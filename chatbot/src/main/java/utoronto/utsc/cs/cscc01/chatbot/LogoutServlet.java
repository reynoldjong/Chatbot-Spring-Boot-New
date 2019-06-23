package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/logout")
/*
 * This class handles all logout request by any logged in users
 */
public class LogoutServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
      req.logout();
      // we can change this to some place else
      resp.sendRedirect("/adminpage");
  }
}
