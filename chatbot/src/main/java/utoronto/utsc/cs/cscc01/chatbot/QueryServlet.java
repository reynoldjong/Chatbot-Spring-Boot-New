package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userquery")
public class QueryServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter writer = resp.getWriter();
		
		writer.println("<html><title>Query</title><body>");
        writer.println("<h1>If you see this, this is working</h1>");
        writer.println("</body>Look at your IDE's shell and it should print Hello World</html>");
        
        System.out.println("Hello World!");
	}
}
