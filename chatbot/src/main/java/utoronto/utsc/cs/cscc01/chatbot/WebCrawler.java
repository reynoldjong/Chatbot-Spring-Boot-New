package utoronto.utsc.cs.cscc01.chatbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class WebCrawler {

    private HashMap<String, String> links;

    public WebCrawler() {
        this.links = new HashMap<>();
    }

    public void crawl(String url, int targetDepth) {

        int currDepth = 0;

        if ((!links.containsKey(url) && (currDepth < targetDepth))) {

            try {
                Document document = Jsoup.connect(url).get();
                document.getElementsByTag("header").remove();
                document.getElementsByTag("footer").remove();
                links.put(url, document.text());
                Elements elements = document.select("a[href]");
                currDepth ++;
                for (Element element : elements) {
                    // Link and Text of the links on the website
                    String sublink = element.attributes().get("href");
                    if (sublink.startsWith("http")) {
                        crawl(element.attributes().get("href"), currDepth);
                    }

                }

            } catch(IOException e){
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }

        for (HashMap.Entry<String, String> entry : links.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        WebCrawler wc = new WebCrawler();
        wc.crawl("https://www.digitalfinanceinstitute.org/", 2);
    }

}
