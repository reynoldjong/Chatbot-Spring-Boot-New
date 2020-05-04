package team14.chatbot.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import team14.chatbot.Models.ChatbotQuery;
import team14.chatbot.Repository.QueryRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ChatbotQueryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private QueryRepository queryRepository;

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
        queryRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateQueryAndReturnAnswerToQuery() throws Exception {
        String userQuery = "What is DFI?";
        // answer to the query
        String answer = "DFI is a think tank created for the next generation of financial services. " +
                "They address issues in respect of the nexus between financial innovation, digital finance policy " +
                "and regulation, financial inclusion and women in financial technology.";
        // image given along with answer
        String image = "http://www.digitalfinanceinstitute.org/wp-content/uploads/2015/09/Horizontal-Logo" +
                "-In-White-Color-copy-290.png";
        // First time asking
        int frequency = 1;
        mvc.perform(get("/userquery")
                .param("userQuery", userQuery)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.watson.url", hasSize(0)))
                .andExpect(jsonPath("$.watson.queryFlag", hasSize(0)))
                .andExpect(jsonPath("$.watson.image", hasSize(1)))
                .andExpect(jsonPath("$.watson.image[0]", is(image)))
                .andExpect(jsonPath("$.watson.text", hasSize(1)))
                .andExpect(jsonPath("$.watson.text[0]", is(answer)))
                .andExpect(jsonPath("$.lucene.url", hasSize(0)))
                .andExpect(jsonPath("$.lucene.file", hasSize(0)));

        List<ChatbotQuery> found = queryRepository.findAll();
        assertThat(found).extracting(ChatbotQuery::getMessage).containsOnly(userQuery);
        assertThat(found).extracting(ChatbotQuery::getFrequency).containsOnly(frequency);
    }

    @Test
    public void whenValidInput_thenUpdateQueryAndReturnAnswerToQuery() throws Exception {
        ChatbotQuery existingQuery = new ChatbotQuery("What is DFI?", 10);
        queryRepository.saveAndFlush(existingQuery);
        // answer to the query
        String answer = "DFI is a think tank created for the next generation of financial services. " +
                "They address issues in respect of the nexus between financial innovation, digital finance policy " +
                "and regulation, financial inclusion and women in financial technology.";
        // image given along with answer
        String image = "http://www.digitalfinanceinstitute.org/wp-content/uploads/2015/09/Horizontal-Logo" +
                "-In-White-Color-copy-290.png";
        mvc.perform(get("/userquery")
                .param("userQuery", existingQuery.getMessage())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.watson.url", hasSize(0)))
                .andExpect(jsonPath("$.watson.queryFlag", hasSize(0)))
                .andExpect(jsonPath("$.watson.image", hasSize(1)))
                .andExpect(jsonPath("$.watson.image[0]", is(image)))
                .andExpect(jsonPath("$.watson.text", hasSize(1)))
                .andExpect(jsonPath("$.watson.text[0]", is(answer)))
                .andExpect(jsonPath("$.lucene.url", hasSize(0)))
                .andExpect(jsonPath("$.lucene.file", hasSize(0)));

        List<ChatbotQuery> found = queryRepository.findAll();
        assertThat(found).extracting(ChatbotQuery::getMessage).containsOnly(existingQuery.getMessage());
        assertThat(found).extracting(ChatbotQuery::getFrequency).containsOnly(existingQuery.getFrequency() + 1);
    }

    @Test
    public void givenQuery_whenGetQueryAndUnauthorized_thenStatus401() throws Exception {
        ChatbotQuery query = new ChatbotQuery("This question appeared 10 times", 10);
        queryRepository.saveAndFlush(query);

        mvc.perform(get("/userquery/getData")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenQuery_whenGetQueryAndAuthorized_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        ChatbotQuery queryOne = new ChatbotQuery("This question appeared 10 times", 10);
        ChatbotQuery queryTwo = new ChatbotQuery("This question appeared 50 times", 50);
        ChatbotQuery queryThree = new ChatbotQuery("This question appeared 30 times", 30);
        queryRepository.saveAndFlush(queryOne);
        queryRepository.saveAndFlush(queryTwo);
        queryRepository.saveAndFlush(queryThree);


        List<ChatbotQuery> queries = Arrays.asList(queryOne, queryTwo, queryThree);

        List<ChatbotQuery> sortedQueries = queries.stream().sorted(Comparator.comparing(ChatbotQuery::getFrequency)
                .reversed()).collect(Collectors.toList());

        mvc.perform(get("/userquery/getData")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0][0]", is(sortedQueries.get(0).getMessage())))
                .andExpect(jsonPath("$[0][1]", is(sortedQueries.get(0).getFrequency())))
                .andExpect(jsonPath("$[1][0]", is(sortedQueries.get(1).getMessage())))
                .andExpect(jsonPath("$[1][1]", is(sortedQueries.get(1).getFrequency())))
                .andExpect(jsonPath("$[2][0]", is(sortedQueries.get(2).getMessage())))
                .andExpect(jsonPath("$[2][1]", is(sortedQueries.get(2).getFrequency())));

    }

    @Test
    public void  whenUnauthorizedDownloadCSV_thenStatus401() throws Exception {

        mvc.perform(get("/userquery/exportCSV")
                .contentType(MEDIA_TYPE_CSV))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAuthorizedDownloadCSV_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        String filename = "queriesData.csv";

        mvc.perform(get("/userquery/exportCSV")
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
