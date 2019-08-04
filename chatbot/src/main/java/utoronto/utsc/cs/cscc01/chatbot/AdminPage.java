package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
// UNUSED
@WebServlet(urlPatterns = "/adminpage")
public class AdminPage extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Principal principal = req.getUserPrincipal();
    if (principal == null || !req.isUserInRole("admin")) {
      LoginServlet.forwardToLoginPage(req, resp,
          "User authentication is required.");
      return;
    }
    resp.setContentType("text/html");
    PrintWriter writer = resp.getWriter();
    writer.println("This is the secured portion of the web app");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // in practice we will call doPost from the webApp but doGet is executed
    doGet(req, resp);
  }
}
*/