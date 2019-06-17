package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/login")
/*
 * This class handles the user logins and 
 */
public class LoginServlet extends HttpServlet {
  // dependency injection of database into login servlet so we can test it
  private UserDatabase db;
  
  public LoginServlet(UserDatabase db) {
    this.db = db;
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // grab username and password from the fields
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    try {
      req.login(username, password);
      if (req.getUserPrincipal() != null && db.verifyUser(username, password)) {
        resp.sendRedirect("/adminpage");
      }
      else {
        // if we couldn't verify the user, try again
        forwardToLoginPage(req, resp, "Incorrect user name or password.");
      }
      
    } catch (ServletException e) {
      // System.out.println(e.getMessage());
      forwardToLoginPage(req, resp, e.getMessage());
    }
  }

  public static void forwardToLoginPage(HttpServletRequest req,
      HttpServletResponse resp, String errorMessage)
      throws ServletException, IOException {

    req.setAttribute("error", errorMessage);
    // for now we use this basic login page
    req.getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
