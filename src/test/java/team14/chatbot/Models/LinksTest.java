package team14.chatbot.Models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.SerializationUtils;
import team14.chatbot.Repository.LinksRepository;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LinksTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LinksRepository linksRepository;

    @Test
    public void whenFindBySeed_thenReturnLink() {
        // given
        HashMap<String, HashMap<String, String>> nothing = new HashMap<>();
        Link link = new Link("www.test.com", 2, SerializationUtils.serialize(nothing));
        entityManager.persist(link);
        entityManager.flush();

        // when
        Optional<Link> found = linksRepository.findBySeed(link.getSeed());

        // then
        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getSeed()).isEqualTo(link.getSeed());
        assertThat(found.get().getDepth()).isEqualTo(link.getDepth());
    }

    @Test
    public void whenDeleteBySeed_thenEmpty() {
        HashMap<String, HashMap<String, String>> nothing = new HashMap<>();
        Link beforeLink = new Link("www.test.com", 2, SerializationUtils.serialize(nothing));
        entityManager.persist(beforeLink);
        entityManager.flush();
        linksRepository.deleteBySeed(beforeLink.getSeed());
        entityManager.flush();
        Link afterLink = entityManager.find(Link.class, beforeLink.getId());
        assertThat(afterLink).isNull();
    }
}
