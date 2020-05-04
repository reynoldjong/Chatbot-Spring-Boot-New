package team14.chatbot.Models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import team14.chatbot.Repository.ChatbotFilesRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ChatbotFilesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChatbotFilesRepository chatbotFilesRepository;

    @Test
    public void whenFindByFileName_thenReturnChatbotFile() {
        // given
        ChatbotFile testFile = new ChatbotFile("TestId", "Test.docx", "test".getBytes());
        entityManager.persist(testFile);
        entityManager.flush();

        // when
        Optional<ChatbotFile> found = chatbotFilesRepository.findByFileName(testFile.getFileName());

        // then
        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getId()).isEqualTo(testFile.getId());
        assertThat(found.get().getFileName()).isEqualTo(testFile.getFileName());
        assertThat(found.get().getData()).isEqualTo(testFile.getData());
    }

    @Test
    public void whenDeleteByFileName_thenEmpty() {
        ChatbotFile beforeFile = new ChatbotFile("TestId", "Test.docx", "test".getBytes());
        entityManager.persist(beforeFile);
        entityManager.flush();
        chatbotFilesRepository.deleteByFileName(beforeFile.getFileName());
        entityManager.flush();
        ChatbotFile afterFile = entityManager.find(ChatbotFile.class, beforeFile.getId());
        assertThat(afterFile).isNull();
    }
}
