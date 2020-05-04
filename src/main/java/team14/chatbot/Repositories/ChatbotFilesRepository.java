package team14.chatbot.Repository;


import org.springframework.data.repository.CrudRepository;
import team14.chatbot.Models.ChatbotFile;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean
// CRUD refers Create, Read, Update, Delete

public interface ChatbotFilesRepository extends CrudRepository<ChatbotFile, String> {
    void deleteByFileName(String fileName);
    Optional<ChatbotFile> findByFileName(String fileName);

}
