package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to handle traffic for getting user feedback from the
 * database to the admin page of the chatbot application
 * 
 * @author Reynold
 *
 */
@WebServlet(urlPatterns = "/getdata")
public class GetDataServlet extends HttpServlet {

  private QueryDatabaseAdmin queryDb;
  private FeedbackDatabaseAdmin feedbackDb;
  private AnswerRatingDatabaseAdmin answerRatingDb;

  public void init() {
    this.queryDb = new QueryDatabaseAdmin();
    this.feedbackDb = new FeedbackDatabaseAdmin();
    this.answerRatingDb = new AnswerRatingDatabaseAdmin();
  }

  /**
   * Post method used to get the information from the database and sends it to
   * the admin page
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    if (request.getParameter("getQueries") != null) {
      response.setContentType("text/csv");
      response.setHeader("Content-Disposition",
          "attachment; filename=queriesData.csv");
      this.queryDb.extractCSV();

      InputStream inStream = new FileInputStream(
          new File("../chatbot/files/data/queriesData.csv"));
      OutputStream outputStream = response.getOutputStream();
      byte[] buffer = new byte[4096];
      int bytesRead;

      while ((bytesRead = inStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      outputStream.flush();
      outputStream.close();


    } else if (request.getParameter("getFeedback") != null) {
      response.setContentType("text/csv");
      response.setHeader("Content-Disposition",
          "attachment; filename=feedbackData.csv");
      try {
        this.feedbackDb.extractCSV();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      InputStream inStream = new FileInputStream(
          new File("../chatbot/files/data/feedbackData.csv"));
      OutputStream outputStream = response.getOutputStream();
      byte[] buffer = new byte[4096];
      int bytesRead;

      while ((bytesRead = inStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      outputStream.flush();
      outputStream.close();

    } else if (request.getParameter("getAnswerRating") != null) {
      response.setContentType("text/csv");
      response.setHeader("Content-Disposition",
          "attachment; filename=answerRatingData.csv");
      try {
        this.answerRatingDb.extractCSV();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      InputStream inStream = new FileInputStream(
          new File("../chatbot/files/data/answerRatingData.csv"));
      OutputStream outputStream = response.getOutputStream();
      byte[] buffer = new byte[4096];
      int bytesRead;

      while ((bytesRead = inStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      outputStream.flush();
      outputStream.close();

    }
  }

  //
  // private void listQueries(HttpServletRequest request, HttpServletResponse
  // response)
  // throws ServletException, IOException {
  //
  // try {
  //
  //
  // List<Query> listQuery = queryDb.list();
  //
  // request.setAttribute("listQuery", listQuery);
  //
  // ArrayList<String> list = new ArrayList<>();
  // for(Query q: listQuery){
  // list.add(q.getContent());
  // }
  // Gson gsonBuilder = new GsonBuilder().create();
  // String jsonFromJavaArrayList = gsonBuilder.toJson(list);
  // response.setContentType("application/json");
  // response.setCharacterEncoding("UTF-8");
  // response.getWriter().write(String.format("{\"query\": %s
  // }",jsonFromJavaArrayList));
  //
  //
  // } catch (SQLException e) {
  //
  // e.printStackTrace();
  // throw new ServletException(e);
  //
  // }
  // }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    String result = this.queryDb.list().toString();
    response.getWriter().write(String.format("{\"queries\": %s }", result));
    // listQueries(request, response);
    // request.getRequestDispatcher("/WEB-INF/getdata.jsp").forward(request,
    // response);
  }


}
