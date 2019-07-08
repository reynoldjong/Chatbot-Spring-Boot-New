package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.io.PrintWriter;
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
		PrintWriter writer = resp.getWriter();
		// get user request from http request	
		String userQuery = req.getQueryString();
		
		// first try watson assistant
		reply = queryAssistant.simpleAssistantQuery(userQuery);
		// if assistant cannot pattern match the user input, it will return the string
		// "Need to query"
		if (reply.equals("Need to query")) {
			// query watson discovery
			reply = queryEngine.simpleQuery(userQuery);
			// TODO: query our own index
			//reply += 2ndqueryEngine.simpleQuery(userQuery);
		}
		// if watson assistant is able to answer, we simply return that answer
		
		writer.println("<html><title>Query</title><body>");
        writer.println("<h1>If you see this, this is working</h1>");
        writer.println("</body>" + reply + "</html>");
	}
}
