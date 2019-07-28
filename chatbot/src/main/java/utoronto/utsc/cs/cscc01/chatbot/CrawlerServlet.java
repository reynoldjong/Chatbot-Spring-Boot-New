package utoronto.utsc.cs.cscc01.chatbot;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = "/webcrawler")
public class CrawlerServlet extends HttpServlet {


    private WebCrawler crawler;
    private LinksDatabaseAdmin linksDb;

    @Override
    public void init() {
        this.linksDb = new LinksDatabaseAdmin();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String url = request.getParameter("url");
        String depth = request.getParameter("depth");

        try {
            this.crawler = new WebCrawler(Integer.parseInt(depth));
            crawler.crawl(url, 0, "?page_id", "?p");
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("/WEB-INF/crawler.jsp").forward(request, response);
    }

}
