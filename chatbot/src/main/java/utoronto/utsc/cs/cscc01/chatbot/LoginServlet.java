package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletConfig;
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
  
  public void init(ServletConfig config) {
    this.db = new TempDatabase();
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // grab username and password from the fields
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    try {
      req.login(username, password);
      if (req.getUserPrincipal() != null && req.isUserInRole("admin")) {
      
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"authenticated\":\"true\"}");

        //resp.sendRedirect("localhost:8080/");
      }
      else {
        // if we couldn't verify the user, try again
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"authenticated\":\"false\"}");
      }
      
    } catch (ServletException e) {
      // System.out.println(e.getMessage());

      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write("{\"authenticated\":\"false\"}");
    }
  }


  public static void forwardToLoginPage(HttpServletRequest req,
      HttpServletResponse resp, String errorMessage)
      throws ServletException, IOException {

    req.setAttribute("error", errorMessage);
    // TODO: this will have to change based on front end implementation
    req.getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
