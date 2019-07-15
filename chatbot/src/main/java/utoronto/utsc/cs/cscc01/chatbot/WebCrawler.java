package utoronto.utsc.cs.cscc01.chatbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class WebCrawler {

    private static HashMap<String, String> links;
    private int maxDepth;

    public WebCrawler(int maxDepth) {
        this.links = new HashMap<>();
        this.maxDepth = maxDepth;
    }

    public void crawl(String url, int currDepth) {

        if ((!links.containsKey(url) && (currDepth < maxDepth))) {

            try {
                Document document = Jsoup.connect(url).userAgent("Mozilla").ignoreHttpErrors(true).timeout(10000).
                        ignoreContentType(true).followRedirects(true).get();
                document.getElementsByTag("header").remove();
                document.getElementsByTag("footer").remove();
                Elements elements = document.select("a[href]");
                Elements articles = document.select("article");
                // System.out.println(Jsoup.parse(url));
                for (Element article : articles) {
                    if(links.get(url) == null) {
                        links.put(url, article.text());
                    }
                    else {
                        links.put(url, links.get(url) + " " + article.text());
                    }
                }

                currDepth ++;
                for (Element element : elements) {
                    // Link and Text of the links on the website
                    String sublink = element.attributes().get("href");
                        if (sublink.startsWith("http")) {
                            crawl(sublink, currDepth);
                        }
                }

            } catch(IOException e){
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        WebCrawler wc = new WebCrawler(3);
        wc.crawl("https://www.digitalfinanceinstitute.org/", 0);
        for (HashMap.Entry<String, String> entry : links.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

}
