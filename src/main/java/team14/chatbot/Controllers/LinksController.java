package team14.chatbot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;
import team14.chatbot.IBMWatsonEngine.CrawlerEngine;
import team14.chatbot.IBMWatsonEngine.WatsonDiscovery;
import team14.chatbot.LuceneEngine.WebCrawler;
import team14.chatbot.Repository.LinksRepository;
import team14.chatbot.Models.Link;

import java.sql.Date;
import java.text.SimpleDateFormat;

@RestController
public class LinksController {

    @Autowired // This means to get the bean called
    // Which is auto-generated by Spring, we will use it to handle the data
    private LinksRepository linksRepository;
    private WebCrawler customCrawler;
    private final CrawlerEngine crawlerEngine = new CrawlerEngine(WatsonDiscovery.buildDiscovery());

    @Transactional
    @PutMapping("/webcrawler")
    public void addLink (@RequestParam String url, @RequestParam String depth) {
        customCrawler = new WebCrawler(Integer.parseInt(depth));
        if (url.equals("https://www.digitalfinanceinstitute.org/")) {
            customCrawler.crawl(url, 0, "?page_id", "?p");
        } else {
            customCrawler.crawl(url, 0, "", "");
        }
        byte[] collection = SerializationUtils.serialize(customCrawler.getLinks());
        linksRepository.findBySeed(url).map(theLink -> {
            crawlerEngine.removeUrl(url);
            crawlerEngine.addUrl(url);
            if (theLink.getDepth() != Integer.parseInt(depth)) {
                theLink.setDepth(Integer.parseInt(depth));
            }
            theLink.setCollection(collection);
            theLink.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date(System.currentTimeMillis())));
            return linksRepository.save(theLink);
        }).orElseGet(() -> {
            crawlerEngine.addUrl(url);
            Link link = new Link(url, Integer.parseInt(depth), collection);
            return linksRepository.save(link);
        });
    }

    @GetMapping("/webcrawler")
    public Iterable<Link> getAllLinks() {
        // This returns a JSON or XML
        return linksRepository.findAll();
    }

    @Transactional
    @DeleteMapping("/webcrawler")
    public void deleteLink(@RequestParam String url) {
        crawlerEngine.removeUrl(url);
        linksRepository.deleteBySeed(url);
    }
}
