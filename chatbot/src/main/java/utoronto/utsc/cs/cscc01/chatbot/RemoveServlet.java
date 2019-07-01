package utoronto.utsc.cs.cscc01.chatbot;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet (urlPatterns = "/remove")
public class RemoveServlet  extends HttpServlet {

    private FilesDatabaseAdmin db;
    private String filePath = "../chatbot/files/";

    public void init( ){
        // Get the file location where it would be stored.
        db = new FilesDatabaseAdmin();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        listUploadedFile(request, response);
    }

    private void listUploadedFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {

            db.connect();
            List<UploadedFile> listUploadedFile = db.list();
            request.setAttribute("listUploadedFile", listUploadedFile);

            RequestDispatcher dispatcher = request.getRequestDispatcher("remove.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        File file;

        String fileName =request.getParameter("file");
        if (db.connect()) {
            db.remove(fileName);

            if( fileName.lastIndexOf("\\") >= 0 ) {
                file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\")));
            } else {
                file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1));
            }

            file.delete();

            String text = "You successfully removed: " + fileName;
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
        }
    }
}
