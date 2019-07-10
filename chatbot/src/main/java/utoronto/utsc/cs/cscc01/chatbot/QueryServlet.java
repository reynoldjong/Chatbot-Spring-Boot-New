package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userquery")
public class QueryServlet extends HttpServlet {
	
  private SearchEngine queryEngine;
  private SearchAssistant queryAssistant;

  public void init(ServletConfig config) {
    this.queryEngine = new QueryEngine(WatsonDiscovery.buildDiscovery());
    this.queryAssistant = new QueryAssistant(WatsonAssistant.buildAssistant());
  }

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    String reply;
	    // we are returning json to the front end
	    resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		// get user request from http request, and decode it so we have it standardized between browsers
		String userQuery = req.getQueryString();
		userQuery = URLDecoder.decode(userQuery, "UTF-8");
		// first try watson assistant
		Hashtable<String, ArrayList<String>> assistantHashTable = queryAssistant.simpleAssistantQuery(userQuery);
		// if assistant cannot pattern match the user input, query flag
		// will be set to "Need to query"
		if (assistantHashTable.get("queryFlag").size() > 0 && assistantHashTable.get("queryFlag").get(0).equals("Need to query")) {
			// query watson discovery
			Hashtable<String, ArrayList<String>> watsonHashTable = queryEngine.simpleQuery(userQuery);
			// TODO: query our own index
			//reply += 2ndqueryEngine.simpleQuery(userQuery);
			reply = hashToJson(watsonHashTable);
		}
		// if watson assistant is able to answer, we simply return that answer
		else {
		  reply = hashToJson(assistantHashTable);
		}
		
		writer.write(reply);
  }
  
  // we will have to modify this to include our query's result
  // after it has been implemented
  private String hashToJson(Hashtable<String, ArrayList<String>> h) {
    String result = "{";
    for (String key : h.keySet()) {
      String tempResult = "";
      ArrayList<String> value = h.get(key);
      // file and url may have passage element that we have to handle separately
      if (key.equals("file") && value.size() > 0)
        result += "\"file\":" + value.get(0) + ",";
      //else if (key.equals("url") && value.size() > 0)
      //  result += "\"url\":" + value.get(0) + ",";
      else {
        int i = 0;
        // write the key for json
        if (value.size() > 0)
          result += "\"" + key + "\":";
        // we need a list if size > 1
        if (value.size() > 1)
          result += "[";
        // we need to know if tempResult is "" later on to know whether to add ","
        while (i < value.size()) {
          tempResult += "\"" + value.get(i) + "\",";
          i++;
        }
        result += tempResult;
        // remove trailing ","
        if (result.endsWith(","))
          result = result.substring(0, result.length() - 1);
        // close bracket if we opened one
        if (value.size() > 1)
          result += "]";
        // add "," for next key
        if (!tempResult.equals(""))
          result += ",";
      }
    }
    // remove last trailing ","
    if (result.endsWith(","))
      result = result.substring(0, result.length() - 1);
    result += "}";
    return result;
  }
}
