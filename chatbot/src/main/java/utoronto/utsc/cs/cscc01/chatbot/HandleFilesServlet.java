package utoronto.utsc.cs.cscc01.chatbot;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;


@WebServlet (urlPatterns = "/handlefiles")
@MultipartConfig
public class HandleFilesServlet extends HttpServlet {


    private FilesDatabaseAdmin db;
    private WatsonDiscovery wdisc;
    //TODO: see if these fields are needed

    // private int maxFileSize = 1024 * 1024;
    // private int maxMemSize = 4 * 1024;


    public void init () {
      
        this.db = new FilesDatabaseAdmin();
        this.wdisc = WatsonDiscovery.buildDiscovery();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String action = request.getParameter("action");

        if ("upload".equals(action)) {
            try {
                upload(request, response);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if ("remove".equals(action)) {
            try {
                remove(request, response);

            } catch (IOException e) {

                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        listUploadedFile(request, response);

    }

    private void listUploadedFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {

           
            List<UploadedFile> listUploadedFile = db.list();
        
            request.setAttribute("listUploadedFile", listUploadedFile);



            ArrayList<String> list = new ArrayList<>();
            for(UploadedFile f:listUploadedFile){
                list.add(f.getFilename());
            }
            Gson gsonBuilder = new GsonBuilder().create();
            String jsonFromJavaArrayList = gsonBuilder.toJson(list);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{\"files\": %s }",jsonFromJavaArrayList));
          

        } catch (SQLException e) {

            e.printStackTrace();
            throw new ServletException(e);

        }
    }

    protected void remove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String fileName =request.getParameter("file");

       if (! db.remove(fileName)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("{\"reply\": \"Can't remove file!\"}");
        }
    }

    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Check that we have a file upload request
        java.io.PrintWriter out = response.getWriter();
        
        List<Part> fileParts; // Retrieves <input type="file" name="file" multiple="true">
        try {
            
            fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());
           
            for (Part filePart : fileParts) {
                String fileName = getFileName(filePart);
                InputStream fileContent = filePart.getInputStream();
                InputStream fileContentForDb = filePart.getInputStream();
                db.insert(fileName, fileContent, fileContentForDb, filePart.getSize());
                out.println("Uploaded Filename: " + fileName);

            }
            
        } catch (ServletException e) {
            out.println("Can't upload any files");
        }

    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        // System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }

}