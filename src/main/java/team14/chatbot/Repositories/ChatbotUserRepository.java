package team14.chatbot.Repository;

import org.springframework.data.repository.CrudRepository;
import team14.chatbot.Models.ChatbotUser;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean
// CRUD refers Create, Read, Update, Delete

public interface ChatbotUserRepository extends CrudRepository<ChatbotUser, Long> {
    Optional<ChatbotUser> findByUsername(String username);
}
