package utoronto.utsc.cs.cscc01.chatbot;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@WebServlet(urlPatterns = "/index")
public class IndexerServlet extends HttpServlet {


    private Indexer indexer;
    private FilesDatabaseAdmin filesDb;
    private LinksDatabaseAdmin linksDb;
    private String indexPath = "../chatbot/index/documents";

    public void init() {

        this.filesDb = new FilesDatabaseAdmin();
        this.linksDb = new LinksDatabaseAdmin();
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getParameter("index") != null) {


            List<UploadedFile> fileList;
            List<CrawledLink> linkList;

            try {
                File dirFile = new File(indexPath);
                FileUtils.cleanDirectory(dirFile);
                this.indexer = new Indexer(indexPath);
                fileList = filesDb.list();
                for (UploadedFile file: fileList) {
                    String fileName = file.getFilename();
                    String content = filesDb.extractFile(fileName);
                    this.indexer.indexDoc(fileName, content);
                }
                linkList = linksDb.list();
                for (CrawledLink link: linkList) {
                    String seed = link.getSeed();
                    HashMap<String, HashMap<String, String>> linkCollection = linksDb.extractLinkCollection(seed);
                    this.indexer.indexUrl(linkCollection);
                }


                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("{\"reply\": \"Indexed Files\"}");

            } catch (SQLException | ClassNotFoundException | IOException e) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("{\"reply\": \"Error getting information from database\"}");
            }
        }

    }

    private void listCrawledLink(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {


            List<CrawledLink> listCrawledLink = linksDb.list();

            request.setAttribute("listCrawledLink", listCrawledLink);



            ArrayList<String> list = new ArrayList<>();
            for(CrawledLink c:listCrawledLink){
                list.add(c.getSeed());
            }
            Gson gsonBuilder = new GsonBuilder().create();
            String jsonFromJavaArrayList = gsonBuilder.toJson(list);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{\"links\": %s }",jsonFromJavaArrayList));


        } catch (SQLException e) {

            e.printStackTrace();
            throw new ServletException(e);

        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listCrawledLink(request, response);
        response.setContentType("text/html");
        request.getRequestDispatcher("/WEB-INF/indexer.jsp").forward(request, response);
    }

}
