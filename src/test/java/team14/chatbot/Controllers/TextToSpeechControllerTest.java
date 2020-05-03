package team14.chatbot.Controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team14.chatbot.ChatbotApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TextToSpeechControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    // write test cases here
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void whenValidInput_thenReturnEncodedAudio() throws Exception {
        String ttsText = "Test";
        mvc.perform(post("/tts")
                .param("ttsText", ttsText)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"));
    }
}
