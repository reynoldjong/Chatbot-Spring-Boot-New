package team14.chatbot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team14.chatbot.Models.Rating;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean
// CRUD refers Create, Read, Update, Delete

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByMessage(String message);
}
