package team14.chatbot.LuceneEngine;

import java.io.IOException;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A web crawler class used to crawl a given URL as starting seed and crawls the
 * content of that seed and recursively crawls any sub-link that it contains
 * until the given depth limit is reached or there are no more sub-links
 * 
 * @author Reynold
 *
 */
public class WebCrawler {

  private HashMap<String, HashMap<String, String>> links;
  private int maxDepth;

  public WebCrawler(int maxDepth) {
    this.links = new HashMap<>();
    this.maxDepth = maxDepth;
  }

  /**
   * Main method used to crawl a given URL
   * @param url - a web page to be crawled
   * @param currDepth - the current depth of the crawl
   * @param notContains - strings that a URL should not contain
   * @param contains - strings that may be contained within the URL
   */
  public void crawl(String url, int currDepth, String notContains,
      String contains) {

    // invalid link
    if (url.contains(".pdf") || url.contains("@") || url.contains(":80")
        || url.contains(".jpg")) {
      return;
    }


    if (!links.containsKey(url) && (currDepth < maxDepth)) {

      int httpIndex = url.indexOf("//") + 2;
      if (url.substring(httpIndex).contains("www")) {
        httpIndex += 4;
      }
      String domain = url.substring(httpIndex, url.indexOf('.', httpIndex));

      try {
        Document document = Jsoup.connect(url).userAgent("Mozilla")
            .ignoreHttpErrors(true).timeout(10000).ignoreContentType(true)
            .followRedirects(true).get();

        int lastIndex = url.lastIndexOf("/") + 1;

        if (!url.substring(lastIndex).contains(notContains)
            && url.substring(lastIndex).contains(contains)) {
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


  /**
   * Used by the web crawler to create a hashmap containing the title of the 
   * document as key and the content of the document as value
   * @param document - the web document generated by the crawler
   * @return a hashmap containing the contents of the web document
   */
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

  public HashMap<String, HashMap<String, String>> getLinks() {
    return links;
  }

}
