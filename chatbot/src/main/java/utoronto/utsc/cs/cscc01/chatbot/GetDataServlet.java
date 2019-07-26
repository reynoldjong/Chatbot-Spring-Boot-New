package utoronto.utsc.cs.cscc01.chatbot;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = "/getdata")

public class GetDataServlet extends HttpServlet {

    private QueryDatabaseAdmin queryDb;
    private FeedbackDatabaseAdmin feedbackDb;

    public void init () {
        this.queryDb = new QueryDatabaseAdmin();
        this.feedbackDb = new FeedbackDatabaseAdmin();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("getQueries") != null) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=queriesData.csv");
                this.queryDb.extractCSV();

                InputStream inStream = new FileInputStream(new File("../chatbot/files/queriesData.csv"));
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
            response.setHeader("Content-Disposition", "attachment; filename=feedbackData.csv");
                this.feedbackDb.extractCSV();

                InputStream inStream = new FileInputStream(new File("../chatbot/files/feedbackData.csv"));
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("average", feedbackDb.getAverage());
        request.getRequestDispatcher("/WEB-INF/getdata.jsp").forward(request, response);
    }


}
