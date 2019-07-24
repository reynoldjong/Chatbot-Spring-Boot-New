package utoronto.utsc.cs.cscc01.chatbot;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/getdata")

public class GetDataServlet extends HttpServlet {

    private QueryDatabaseAdmin qd;

    public void init () {
        QueryDatabaseAdmin qd = new QueryDatabaseAdmin();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {

        if (request.getParameter("getdata") != null) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=queries_data.csv");
            try {
                qd.extractCSV();
                InputStream inStream = new FileInputStream(new File("../chatbot/files/query_data.csv"));
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }


}
