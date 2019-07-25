package utoronto.utsc.cs.cscc01.chatbot;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/webcrawler")
public class CrawlerServlet extends HttpServlet {


    private WebCrawler crawler;


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");

        String url = request.getParameter("url");
        String depth = request.getParameter("depth");

        this.crawler = new WebCrawler(Integer.parseInt(depth));
        crawler.crawl(url, 0, "?page_id", "?p");

        String text = "Website is crawled";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(text);

        } catch (IOException e) {

            e.printStackTrace();
        }


    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("/WEB-INF/crawler.jsp").forward(request, response);
    }

}
