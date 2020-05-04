package team14.chatbot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team14.chatbot.Models.ChatbotFile;
import team14.chatbot.Service.ChatbotFilesStorageService;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class ChatbotFilesController {

    @Autowired
    private ChatbotFilesStorageService chatbotFilesStorageService;

    @Transactional
    @PutMapping(value="/handlefiles", consumes = {"multipart/form-data"})
    public void uploadFile (@RequestPart MultipartFile[] file) {
        Arrays.stream(file).filter(part -> "file".equals(part.getName())).collect(Collectors.toList())
                .forEach(eachFile -> chatbotFilesStorageService.storeFile(eachFile));
    }

    @GetMapping("/handlefiles")
    public Iterable<ChatbotFile> listAllFiles() {
        // This returns a JSON or XML
        return chatbotFilesStorageService.loadAllFiles();
    }

    @Transactional
    @DeleteMapping("/handlefiles/{fileName}")
    public void deleteFile(@PathVariable String fileName) {
        chatbotFilesStorageService.deleteFile(fileName);
    }
}
