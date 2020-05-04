package team14.chatbot.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team14.chatbot.Models.Feedback;

// This will be AUTO IMPLEMENTED by Spring into a Bean
// CRUD refers Create, Read, Update, Delete

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.rating IS NOT NULL")
    Double getAverageRating();

}
