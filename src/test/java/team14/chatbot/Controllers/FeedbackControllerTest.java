package team14.chatbot.Controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import team14.chatbot.ChatbotApplication;
import team14.chatbot.Models.Feedback;
import team14.chatbot.Repository.FeedbackRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    ObjectMapper mapper;

    private static final MediaType MEDIA_TYPE_CSV = new MediaType("text", "csv");

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();
    }


    @After
    public void resetDb() {
        feedbackRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateFeedback() throws Exception {
        Feedback feedback = new Feedback("Nice Chatbot", 5);
        mvc.perform(post("/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(feedback)))
                .andExpect(status().isOk());

        List<Feedback> found = feedbackRepository.findAll();
        assertThat(found).extracting(Feedback::getComments).containsOnly("Nice Chatbot");
        assertThat(found).extracting(Feedback::getRating).containsOnly(5);
    }

    @Test
    public void givenFeedback_whenGetFeedbackAndUnauthorized_thenStatus401() throws Exception {
        Feedback feedback = new Feedback("Nice Chatbot", 5);
        feedbackRepository.saveAndFlush(feedback);

        mvc.perform(get("/feedback")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenFeedback_whenGetFeedbackAndAuthorized_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        Feedback feedbackOne = new Feedback("Nice Chatbot", 5);
        Feedback feedbackTwo = new Feedback("Hate this Chatbot", 1);
        feedbackRepository.saveAndFlush(feedbackOne);
        feedbackRepository.saveAndFlush(feedbackTwo);

        List<Feedback> allFeedback = Arrays.asList(feedbackOne, feedbackTwo);
        double total = 0;
        for (Feedback feedback: allFeedback) {
            total += feedback.getRating();
        }
        double average = total / allFeedback.size();

        mvc.perform(get("/feedback")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feedback", hasSize(2)))
                .andExpect(jsonPath("$.feedback[0].comments", is(feedbackOne.getComments())))
                .andExpect(jsonPath("$.feedback[0].rating", is(feedbackOne.getRating())))
                .andExpect(jsonPath("$.feedback[1].comments", is(feedbackTwo.getComments())))
                .andExpect(jsonPath("$.feedback[1].rating", is(feedbackTwo.getRating())))
                .andExpect(jsonPath("$.average", is(average)));
    }

    @Test
    public void  whenUnauthorizedDownloadCSV_thenStatus401() throws Exception {

        mvc.perform(get("/feedback/exportCSV")
                .contentType(MEDIA_TYPE_CSV))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAuthorizedDownloadCSV_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        String filename = "feedbackData.csv";

        mvc.perform(get("/feedback/exportCSV")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MEDIA_TYPE_CSV))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MEDIA_TYPE_CSV))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\""));

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
