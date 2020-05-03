package team14.chatbot.Models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import team14.chatbot.Repository.QueryRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ChatbotQueryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QueryRepository queryRepository;

    @Test
    public void whenFindByMessage_thenReturnChatbotQuery() {
        // given
        ChatbotQuery query = new ChatbotQuery("This question appeared 10 times", 10);
        entityManager.persist(query);
        entityManager.flush();

        // when
        List<ChatbotQuery> found = queryRepository.findByMessage(query.getMessage());

        // then
        for (ChatbotQuery eachFoundQuery: found) {
            assertThat(eachFoundQuery.getMessage()).isEqualTo(query.getMessage());
            assertThat(eachFoundQuery.getFrequency()).isEqualTo(query.getFrequency());
        }

    }

    @Test
    public void whenFindAllChatbotQueries_thenReturnChatbotQueriesInDescendingOrderByFrequency() {
        // given
        ChatbotQuery queryOne = new ChatbotQuery("This question appeared 10 times", 10);
        ChatbotQuery queryTwo = new ChatbotQuery("This question appeared 50 times", 50);
        ChatbotQuery queryThree = new ChatbotQuery("This question appeared 30 times", 30);
        entityManager.persist(queryOne);
        entityManager.persist(queryTwo);
        entityManager.persist(queryThree);
        entityManager.flush();

        List<ChatbotQuery> queries = Arrays.asList(queryOne, queryTwo, queryThree);

        List<ChatbotQuery> sortedQueries = queries.stream().sorted(Comparator.comparing(ChatbotQuery::getFrequency)
                .reversed()).collect(Collectors.toList());
        // when
        Iterable<Object[]> found = queryRepository.findAllByFrequencyDesc();
        List<ChatbotQuery> foundList = new ArrayList<>();
        for (Object[] object: found) {
            foundList.add(new ChatbotQuery((String) object[0], (Integer) object[1]));
        }

        // then
        assertThat(foundList.get(0).getMessage()).isEqualTo(sortedQueries.get(0).getMessage());
        assertThat(foundList.get(0).getFrequency()).isEqualTo(sortedQueries.get(0).getFrequency());
        assertThat(foundList.get(1).getMessage()).isEqualTo(sortedQueries.get(1).getMessage());
        assertThat(foundList.get(1).getFrequency()).isEqualTo(sortedQueries.get(1).getFrequency());
        assertThat(foundList.get(2).getMessage()).isEqualTo(sortedQueries.get(2).getMessage());
        assertThat(foundList.get(2).getFrequency()).isEqualTo(sortedQueries.get(2).getFrequency());
    }
}
