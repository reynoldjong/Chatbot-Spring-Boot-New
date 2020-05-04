package team14.chatbot.Models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import team14.chatbot.Repository.RatingRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RatingTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void whenFindByMessage_thenReturnRating() {
        // given
        Rating rating = new Rating("Good Answer", 1, 0);
        entityManager.persist(rating);
        entityManager.flush();

        // when
        Optional<Rating> found = ratingRepository.findByMessage(rating.getMessage());

        // then
        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getMessage()).isEqualTo(rating.getMessage());
        assertThat(found.get().getGood()).isEqualTo(rating.getGood());
        assertThat(found.get().getBad()).isEqualTo(rating.getBad());

    }
}
