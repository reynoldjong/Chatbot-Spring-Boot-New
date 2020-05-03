package team14.chatbot.Controller;

import com.google.gson.Gson;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team14.chatbot.LuceneEngine.FileParser;
import team14.chatbot.Repository.ChatbotFilesRepository;
import team14.chatbot.Repository.LinksRepository;
import team14.chatbot.LuceneEngine.Indexer;
import team14.chatbot.Models.ChatbotFile;
import team14.chatbot.Models.Link;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@RestController // This means that this class is a Controller
public class IndexController {

    @Autowired
    private ChatbotFilesRepository chatbotFilesRepository;
    @Autowired
    private LinksRepository linksRepository;
    private FileParser fileParser = new FileParser();
    private Indexer indexer;
    private String indexPath = "src/main/resources/index/documents";

    @PostMapping(value="/index")
    public void indexDocuments () {
        try {
            indexer = new Indexer(indexPath);
            indexer.removeIndex();
            Iterable<ChatbotFile> allChatbotFiles = chatbotFilesRepository.findAll();
            Iterable<Link> allLinks = linksRepository.findAll();
            for (ChatbotFile eachFile: allChatbotFiles) {
                String fileName = eachFile.getFileName();
                InputStream inputStream = new ByteArrayInputStream(eachFile.getData());
                String content = fileParser.extractContent(fileName, inputStream);
                if (fileName.equals("Chatbot Corpus.docx")) {
                    Gson gson = new Gson();
                    ArrayList<ArrayList<String>> obj = gson.fromJson(content, ArrayList.class);
                    for (ArrayList<String> list : obj) {
                        indexer.indexDoc(list.get(0), list.get(1));
                    }
                } else {
                    indexer.indexDoc(fileName, content);
                }
            }
            for (Link eachLink: allLinks) {
                indexer.indexUrl(SerializationUtils.deserialize(eachLink.getCollection()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/index")
    @Transactional
    public void deleteAllIndex() {
        try {
            indexer = new Indexer(indexPath);
            indexer.removeIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
