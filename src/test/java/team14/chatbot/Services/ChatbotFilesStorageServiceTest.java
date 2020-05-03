package team14.chatbot.Service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import team14.chatbot.Models.ChatbotFile;
import team14.chatbot.Repository.ChatbotFilesRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class ChatbotFilesStorageServiceTest {

    @TestConfiguration
    static class ChatbotFilesStorageServiceTestContextConfiguration {
        @Bean
        public ChatbotFilesStorageService chatbotFilesStorageService() {
            return new ChatbotFilesStorageService();
        }
    }

    @Autowired
    private ChatbotFilesStorageService chatbotFilesStorageService;

    @MockBean
    private ChatbotFilesRepository chatbotFilesRepository;

    @Before
    public void setUp() {
        ChatbotFile testFile = new ChatbotFile("TestId", "Test.html", "test".getBytes());
        when(chatbotFilesRepository.save(testFile)).thenReturn(testFile);
        when(chatbotFilesRepository.findByFileName(testFile.getFileName())).thenReturn(Optional.of(testFile));
        when(chatbotFilesRepository.findAll()).thenReturn(Collections.singleton(testFile));
    }

    @Test
    public void whenValidFile_thenChatbotFileShouldBeStored() throws IOException {
        MockMultipartFile file = new MockMultipartFile("Test", "Test.html", "text/html", "test".getBytes());
        ChatbotFile fileStored = chatbotFilesStorageService.storeFile(file);
        assertThat(fileStored.getFileName()).isEqualTo(file.getOriginalFilename());
        assertThat(fileStored.getData()).isEqualTo(file.getBytes());
    }

    @Test
    public void whenValidFile_thenChatbotFileShouldBeUpdated() throws IOException {
        MockMultipartFile file = new MockMultipartFile("Test", "Test.html", "text/html", "test content updated".getBytes());
        ChatbotFile fileUpdated = chatbotFilesStorageService.storeFile(file);
        assertThat(fileUpdated.getFileName()).isEqualTo(file.getOriginalFilename());
        assertThat(fileUpdated.getData()).isEqualTo(file.getBytes());
    }

    @Test
    public void whenValidFileName_thenChatbotFileShouldBeFound() {
        String fileId = "TestId";
        String fileName = "Test.html";
        byte[] fileData = "test".getBytes();
        ChatbotFile found = chatbotFilesStorageService.getFile(fileName);
        assertThat(found.getId()).isEqualTo(fileId);
        assertThat(found.getFileName()).isEqualTo(fileName);
        assertThat(found.getData()).isEqualTo(fileData);
    }

    @Test
    public void whenValidFileName_thenDeleteChatbotFile() {
        String fileName = "Test.html";
        // when
        chatbotFilesStorageService.deleteFile(fileName);
        // then
        verify(chatbotFilesRepository, times(1)).deleteByFileName(fileName);
    }

    @Test
    public void whenValid_thenLoadAllChatbotFiles() {
        String fileId = "TestId";
        String fileName = "Test.html";
        byte[] fileData = "test".getBytes();
        Iterable<ChatbotFile> found = chatbotFilesStorageService.loadAllFiles();
        for (ChatbotFile eachFile: found) {
            assertThat(eachFile.getId()).isEqualTo(fileId);
            assertThat(eachFile.getFileName()).isEqualTo(fileName);
            assertThat(eachFile.getData()).isEqualTo(fileData);
        }
    }

}
