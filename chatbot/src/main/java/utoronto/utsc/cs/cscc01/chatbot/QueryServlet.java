package utoronto.utsc.cs.cscc01.chatbot;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userquery")
public class QueryServlet extends HttpServlet implements UserQuery {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		
	}
}
