package utoronto.utsc.cs.cscc01.chatbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebCrawler {

    private static HashMap<String, HashMap<String, String>> links;
    private int maxDepth;

    public WebCrawler(int maxDepth) {
        this.links = new HashMap<>();
        this.maxDepth = maxDepth;
    }

    public void crawl(String url, int currDepth, String notContains, String contains) {

        // invalid link
        if (url.contains(".pdf") || url.contains("@") || url.contains(":80") || url.contains(".jpg")) {
            return;
        }


        if (!links.containsKey(url) && (currDepth < maxDepth)) {

            int httpIndex = url.indexOf("//") + 2;
            if (url.substring(httpIndex).contains("www")) {
                httpIndex += 4;
            }
            String domain = url.substring(httpIndex, url.indexOf('.', httpIndex));

            try {
                Document document = Jsoup.connect(url).userAgent("Mozilla").ignoreHttpErrors(true).timeout(10000)
                        .ignoreContentType(true).followRedirects(true).get();

                int lastIndex = url.lastIndexOf("/") + 1;

                if (! url.substring(lastIndex).contains(notContains) && url.substring(lastIndex).contains(contains)) {
                    HashMap<String, String> article = crawlArticles(document);
                    this.links.put(url, article);
                }


                document.getElementsByTag("header").remove();
                document.getElementsByTag("footer").remove();



                Elements sublinks = document.select("a[href]");

                currDepth++;

                for (Element sublink : sublinks) {
                    String suburl = sublink.attributes().get("href");
                    if (suburl.startsWith("http") && suburl.contains(domain)) {
                        crawl(suburl, currDepth, notContains, contains);
                    }
                }

            } catch (IOException e) {
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }


    public HashMap<String, String> crawlArticles(Document document) {
        HashMap<String, String> article = new HashMap<>();

        Elements headers = document.select("h1");
        Element header = headers.get(1);

        String wholeArticle = "";
        Element articleTrunk = document.getElementsByTag("article").first();
        Elements paragraphs = articleTrunk.select("p");
        for (Element paragraph : paragraphs) {
            wholeArticle = wholeArticle + "\n" + paragraph.text();
        }
        article.put(header.text(), wholeArticle);


        return article;

    }

    public static void main(String[] args) {
        WebCrawler wc = new WebCrawler(3);
        String url = "https://digitalfinanceinstitute.org/";
        wc.crawl(url, 0, "?page_id", "?p");

        for (Map.Entry<String, HashMap<String, String>> entry : links.entrySet()) {
            System.out.println(entry.getKey());
            for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
                System.out.println(entry2.getKey());
                System.out.println(entry2.getValue());
            }
        }

    }

}
