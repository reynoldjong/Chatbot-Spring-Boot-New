package team14.chatbot.Controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SerializationUtils;
import org.springframework.web.context.WebApplicationContext;
import team14.chatbot.ChatbotApplication;
import team14.chatbot.LuceneEngine.Indexer;
import team14.chatbot.Models.ChatbotFile;
import team14.chatbot.Models.Link;
import team14.chatbot.Repository.ChatbotFilesRepository;
import team14.chatbot.Repository.LinksRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class IndexControllerTest {

    private static final String testIndexPath = "src/main/resources/index/documents";
    private Indexer indexer;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ChatbotFilesRepository chatbotFilesRepository;

    @Autowired
    private LinksRepository linksRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setup() throws IOException {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();
        indexer = new Indexer(testIndexPath);
        chatbotFilesRepository.save(new ChatbotFile("testId", "test.pdf", "test".getBytes()));
        HashMap<String, HashMap<String, String>> nothing = new HashMap<>();
        linksRepository.save(new Link("www.justForTestPurpose.com", 2, SerializationUtils.serialize(nothing)));
    }

    @After
    public void resetDbAndIndex() throws IOException {
        chatbotFilesRepository.deleteAll();
        linksRepository.deleteAll();
        indexer.removeIndex();
    }

    @Test
    public void whenIndexRequestAndUnauthorized_Status401() throws Exception {
        mvc.perform(post("/index"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenResetRequestAndUnauthorized_Status401() throws Exception {
        mvc.perform(delete("/index"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenIndexRequestAndAuthorized_CreateIndexInIndexFolder() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        mvc.perform(post("/index")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        Stream<Path> walk = Files.walk(Paths.get(testIndexPath));
        // Only get the files that must exist
        List<String> mustExist = walk.map(Path::toString)
                .filter(f -> f.matches(".*(segments_[0-9]*)") || f.contains("write.lock"))
                .collect(Collectors.toList());

        Stream<Path> walkTwo = Files.walk(Paths.get(testIndexPath));

        // If index is successfully created, it will be equal or more than two files
        List<String> allFiles = walkTwo.filter(Files::isRegularFile)
                .map(Path::toString).collect(Collectors.toList());
        assertThat(mustExist.size()).isEqualTo(2);
        assertThat(allFiles.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void whenResetRequestAndAuthorized_RemoveIndexInIndexFolder() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        mvc.perform(delete("/index")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        Stream<Path> walk = Files.walk(Paths.get(testIndexPath));
        List<String> result = walk.filter(Files::isRegularFile)
                .map(Path::toString).collect(Collectors.toList());
        assertThat(result.size()).isEqualTo(0);
    }


    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("chatbot-admin","secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
