package team14.chatbot.Models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import team14.chatbot.Repository.FeedbackRepository;

import java.util.Optional;

    import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedbackTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    public void whenFindById_thenReturnFeedback() {
        // given
        Feedback feedback = new Feedback("Nice Chatbot", 5);
        entityManager.persist(feedback);
        entityManager.flush();

        // when
        Optional<Feedback> found = feedbackRepository.findById(feedback.getId());

        // then
        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getComments()).isEqualTo(feedback.getComments());
        assertThat(found.get().getRating()).isEqualTo(feedback.getRating());
    }

    @Test
    public void whenGetAverage_thenReturnAverageRating() {
        // given
        String[] comments = {"Test1", "Test2"};
        int[] ratings = {5, 1};
        Feedback feedbackOne = new Feedback(comments[0], ratings[0]);
        Feedback feedbackTwo = new Feedback(comments[1], ratings[1]);

        // Calculate the average
        double total = 0;
        for(int i=0; i < ratings.length; i++){
            total = total + ratings[i];
        }
        double average = total / ratings.length;

        entityManager.persist(feedbackOne);
        entityManager.flush();
        entityManager.persist(feedbackTwo);
        entityManager.flush();

        // when
       double averageFound = feedbackRepository.getAverageRating();

        // then
        assertThat(averageFound).isEqualTo(average);
    }
}
