package utoronto.utsc.cs.cscc01.chatbot;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns = "/webcrawler")
public class CrawlerServlet extends HttpServlet {


    private WebCrawler crawler;
    private LinksDatabaseAdmin linksDb;
    private Indexer indexer;
    private String indexPath = "../chatbot/index/documents";

    @Override
    public void init() {
        this.linksDb = new LinksDatabaseAdmin();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String url = request.getParameter("url");
        String depth = request.getParameter("depth");

        try {
            this.indexer = new Indexer(indexPath);
            this.crawler = new WebCrawler(Integer.parseInt(depth));
            crawler.crawl(url, 0, "?page_id", "?p");
            indexer.indexUrl(crawler.getLinks());
            linksDb.insert(url, crawler.getLinks());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"reply\": \"Website is crawled\"}");

        } catch (IOException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"reply\": \"Error spotted\"}");
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
       
       // request.getRequestDispatcher("/WEB-INF/crawler.jsp").forward(request, response);
    }

}
