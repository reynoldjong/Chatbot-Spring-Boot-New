package utoronto.utsc.cs.cscc01.chatbot;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/answerrating")
public class AnswerRatingServlet extends HttpServlet {

    private AnswerRatingDatabaseAdmin answerRatingDb;

    public void init() {
        this.answerRatingDb = new AnswerRatingDatabaseAdmin();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rating = request.getParameter("answerRating");
        String answer = request.getParameter("message");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            this.answerRatingDb.insert(answer, rating);
            writer.write("{\"reply\": \"Feedback received. Thank you!\"}");
        } catch (SQLException e) {
            writer.write("{\"reply\": \"Error receiving feedback!\"}");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("/WEB-INF/answerrating.jsp").forward(request, response);
    }


}
