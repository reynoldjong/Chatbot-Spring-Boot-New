package utoronto.utsc.cs.cscc01.chatbot;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;

@WebServlet(urlPatterns = "/feedback")

public class FeedbackServlet extends HttpServlet {

    private FeedbackDatabaseAdmin feedbackDb;

    public void init () {
        this.feedbackDb = new FeedbackDatabaseAdmin();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"reply\": \"Feedback received. Thank you!\"}");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/feedback.jsp").forward(request, response);
    }


}
