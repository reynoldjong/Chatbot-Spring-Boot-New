package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet that handles user login for the admin page of the chatbot
 * application
 * 
 * @author Chris
 *
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
  // dependency injection of database into login servlet so we can test it
  private UserDatabase db;

  public void init() {
    this.db = new UserDatabaseAdmin();
  }

  /**
   * Post method used to verify user name and password by looking it up in the
   * admin user database
   * 
   * @param req - http request containing user name and password
   * @param resp - http response containing a string of json format to verify if
   *        access should be granted or denied
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // grab username and password from the fields
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    try {
      req.login(username, password);
      if (req.getUserPrincipal() != null && db.verifyUser(username, password)) {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"authenticated\":true}");

        // resp.sendRedirect("localhost:8080/");
      } else {
        // if we couldn't verify the user, try again
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"authenticated\":false}");
      }

    } catch (ServletException e) {
      // System.out.println(e.getMessage());

      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write("{\"authenticated\":false}");
    }
  }
}
