package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Web servlet used to handle file upload/removal on the admin page of the
 * chatbot application
 * 
 * @author Reynold
 */
@WebServlet(urlPatterns = "/handlefiles")
@MultipartConfig
public class FilesServlet extends HttpServlet {


  private FilesDatabaseAdmin db;
  private WatsonDiscovery wdisc;
  // TODO: see if these fields are needed

  // private int maxFileSize = 1024 * 1024;
  // private int maxMemSize = 4 * 1024;


  public void init() {

    this.db = new FilesDatabaseAdmin();
    this.wdisc = WatsonDiscovery.buildDiscovery();
  }

  /**
   * Post method used to handle upload/removal of files on the admin page
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {

    String action = request.getParameter("action");

    if ("upload".equals(action)) {
      try {
        upload(request, response);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    } else if ("remove".equals(action)) {
      try {
        remove(request, response);

      } catch (IOException e) {

        System.out.println(e.getMessage());
      }
    }

  }

  /**
   * Get method used to obtain a list of uploaded files
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {

    listUploadedFile(request, response);
  }

  private void listUploadedFile(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {


    try {


      List<UploadedFile> listUploadedFile = db.list();

      request.setAttribute("listUploadedFile", listUploadedFile);



      ArrayList<String> list = new ArrayList<>();
      for (UploadedFile f : listUploadedFile) {
        list.add(f.getFilename());
      }
      Gson gsonBuilder = new GsonBuilder().create();
      String jsonFromJavaArrayList = gsonBuilder.toJson(listUploadedFile);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      // response.getWriter().write(String.format("{\"files\": %s
      // }",jsonFromJavaArrayList));
      response.getWriter().write(jsonFromJavaArrayList);

    } catch (SQLException e) {

      e.printStackTrace();
      throw new ServletException(e);

    }
  }

  protected void remove(HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    String fileName = request.getParameter("file");

    if (!db.remove(fileName)) {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      PrintWriter writer = response.getWriter();
      writer.write("{\"reply\": \"Can't remove file!\"}");
    }
  }

  public void upload(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Check that we have a file upload request
    java.io.PrintWriter out = response.getWriter();

    List<Part> fileParts; // Retrieves <input type="file" name="file"
                          // multiple="true">
    try {

      fileParts = request.getParts().stream()
          .filter(part -> "file".equals(part.getName()))
          .collect(Collectors.toList());

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
        return token.substring(token.indexOf("=") + 2, token.length() - 1);
      }
    }
    return "";
  }

}
