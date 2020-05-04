package team14.chatbot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import team14.chatbot.IBMWatsonEngine.FilesEngine;
import team14.chatbot.IBMWatsonEngine.WatsonDiscovery;
import team14.chatbot.Repository.ChatbotFilesRepository;
import team14.chatbot.Models.ChatbotFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Service
public class ChatbotFilesStorageService {

    @Autowired
    private ChatbotFilesRepository chatbotFilesRepository;

    private final FilesEngine filesEngine = new FilesEngine(WatsonDiscovery.buildDiscovery());

    public ChatbotFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file is invalid or it is a txt file
            if (fileName.contains(".txt") || fileName.contains("..")) {
                System.out.println("error");
                return null;
                // throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            byte[] data = file.getBytes();
            InputStream dataInput = file.getInputStream();
            return chatbotFilesRepository.findByFileName(fileName).map(theFile -> {
                filesEngine.updateFiles(dataInput, fileName, theFile.getId());
                theFile.setData(data);
                theFile.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date(System.currentTimeMillis())));
                return chatbotFilesRepository.save(theFile);
            }).orElseGet(() -> {
                String id = filesEngine.uploadFiles(dataInput, fileName);
                ChatbotFile chatbotFile = new ChatbotFile(id, fileName, data);
                return chatbotFilesRepository.save(chatbotFile);
            });
        } catch (IOException ex) {
            return null;
            // throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public ChatbotFile getFile(String fileName) {
        return chatbotFilesRepository.findByFileName(fileName).orElseGet(ChatbotFile::new);
    }

    public Iterable<ChatbotFile> loadAllFiles() {
        // This returns a JSON or XML
        return chatbotFilesRepository.findAll();
    }

    public void deleteFile(String fileName) {
        if (getFile(fileName).getId() != null) {
            filesEngine.removeFiles(getFile(fileName).getId());
            chatbotFilesRepository.deleteByFileName(fileName);
        } else {
            System.out.println("error, no file found");
        }
    }
}