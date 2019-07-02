package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userquery")
public class QueryServlet extends HttpServlet {
	
	private SearchEngine queryEngine;
	
	public void init(SearchEngine engine) throws ServletException {
		this.queryEngine = engine;
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter writer = resp.getWriter();
		// get user request from http request
		String userQuery = req.getQueryString();
		String reply = queryEngine.simpleQuery(userQuery);
		writer.println("<html><title>Query</title><body>");
        writer.println("<h1>If you see this, this is working</h1>");
        writer.println("</body>" + reply + "</html>");
	}
}
