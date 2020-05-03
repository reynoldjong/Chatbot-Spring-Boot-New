package team14.chatbot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team14.chatbot.Models.ChatbotQuery;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean
// CRUD refers Create, Read, Update, Delete

public interface QueryRepository extends JpaRepository<ChatbotQuery, Long> {
    List<ChatbotQuery> findByMessage(String message);
    @Query("SELECT message, frequency FROM ChatbotQuery ORDER BY frequency DESC")
    Iterable<Object []> findAllByFrequencyDesc();

}
