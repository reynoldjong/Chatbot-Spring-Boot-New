package utoronto.utsc.cs.cscc01.chatbot;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Web servlet used to handle user feedback
 * 
 * @author Reynold
 *
 */
@WebServlet(urlPatterns = "/feedback")
public class FeedbackServlet extends HttpServlet {

  private FeedbackDatabaseAdmin feedbackDb;

  public void init() {
    this.feedbackDb = new FeedbackDatabaseAdmin();
  }

  /**
   * Post request used to get the user ratings and comments from the user
   * feedback on the chatbot window
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = response.getWriter();

    try {
      Enumeration e = request.getParameterNames();
      int rating = 0;
      String comments = "";

      while (e.hasMoreElements()) {
        String fieldName = (String) e.nextElement();
        if (fieldName.equals("rating")) {
          rating = Integer.parseInt(request.getParameter(fieldName));

        } else if (fieldName.equals("comments")) {
          comments = request.getParameter(fieldName);

        }
      }

      if (rating != 0) {
        feedbackDb.insertFeedback(rating, comments);
      }

      writer.write("{\"reply\": \"Feedback received. Thank you!\"}");

    } catch (SQLException e) {

      writer
          .write("{\"reply\": \"Error getting feedback. Please try again.\"}");
    }
  }

  private void listFeedback(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    try {


      List<Feedback> listFeedback = feedbackDb.list();

      request.setAttribute("listFeedback", listFeedback);

      ArrayList<String> list = new ArrayList<>();
      for (Feedback f : listFeedback) {
        list.add(f.getComments());
      }
      Gson gsonBuilder = new GsonBuilder().create();
      String jsonFromJavaArrayList = gsonBuilder.toJson(list);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter()
          .write(String.format("{\"feedback\": %s }", jsonFromJavaArrayList));


    } catch (SQLException e) {

      e.printStackTrace();
      throw new ServletException(e);

    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    listFeedback(request, response);
    response.setContentType("text/html");
    request.getRequestDispatcher("/WEB-INF/feedback.jsp").forward(request,
        response);
  }



}
