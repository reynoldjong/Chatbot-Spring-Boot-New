package team14.chatbot.Controllers;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import team14.chatbot.ChatbotApplication;
import team14.chatbot.Models.ChatbotFile;
import team14.chatbot.Repository.ChatbotFilesRepository;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ChatbotFilesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ChatbotFilesRepository chatbotFilesRepository;

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


    @After
    public void resetDb() {
        chatbotFilesRepository.deleteAll();
    }

    @Test
    public void whenUploadFileValidInputAndUnauthorized_thenStatus401() throws Exception {
        MockMultipartFile file = new MockMultipartFile("Test", "Test.html", "text/html", "test".getBytes());
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/handlefiles");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mvc.perform(builder
                .file(file))
                .andExpect(status().isUnauthorized());

    }


    @Test
    public void whenUploadFileValidInputAndAuthorized_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        MockMultipartFile fileOne = new MockMultipartFile("Test", "Test.html", "text/html", "test".getBytes());
        MockMultipartFile fileTwo = new MockMultipartFile("test.json", "", "application/json", "{\"key1\": \"value1\"}".getBytes());
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/handlefiles");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mvc.perform(builder
                .file(fileOne)
                .file(fileTwo)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }


    @Test
    public void whenGetFileAndUnauthorized_thenStatus401() throws Exception {
        ChatbotFile testFile = new ChatbotFile("TestId", "Test.html", "test".getBytes());
        chatbotFilesRepository.save(testFile);

        mvc.perform(get("/handlefiles")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenGetFileAndAuthorized_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        ChatbotFile testFileOne = new ChatbotFile("TestId1", "Test.html", "test".getBytes());
        ChatbotFile testFileTwo = new ChatbotFile("TestId2", "Test.docx", "test".getBytes());
        chatbotFilesRepository.save(testFileOne);
        chatbotFilesRepository.save(testFileTwo);

        mvc.perform(get("/handlefiles")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(testFileOne.getId())))
                .andExpect(jsonPath("$[0].fileName", is(testFileOne.getFileName())))
                .andExpect(jsonPath("$[0].data", is(Base64.getEncoder().encodeToString(testFileOne.getData()))))
                .andExpect(jsonPath("$[1].id", is(testFileTwo.getId())))
                .andExpect(jsonPath("$[1].fileName", is(testFileTwo.getFileName())))
                .andExpect(jsonPath("$[1].data", is(Base64.getEncoder().encodeToString(testFileTwo.getData()))));
    }

    @Test
    public void whenDeleteFileAndUnauthorized_thenStatus401() throws Exception {
        ChatbotFile testFile = new ChatbotFile("TestId", "Test.html", "test".getBytes());
        chatbotFilesRepository.save(testFile);

        mvc.perform(delete("/handlefiles/" + testFile.getFileName())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenDeleteFileAndAuthorized_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        ChatbotFile testFile = new ChatbotFile("TestId1", "Test.html", "test".getBytes());
        chatbotFilesRepository.save(testFile);

        mvc.perform(delete("/handlefiles/" + testFile.getFileName())
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertThat(chatbotFilesRepository.findAll()).isEmpty();
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