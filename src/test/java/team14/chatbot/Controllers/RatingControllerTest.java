package team14.chatbot.Controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import team14.chatbot.Models.Rating;
import team14.chatbot.Repository.RatingRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RatingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RatingRepository ratingRepository;

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
        ratingRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateRating() throws Exception {
        String message = "Great Answer";
        String rating = "Good";
        int goodRating = 0;
        int badRating = 0;
        if (rating.equals("Good")) {
            goodRating += 1;
        } else if (rating.equals("Bad")) {
            badRating += 1;
        }
        mvc.perform(put("/rating")
                .param("message", message)
                .param("answerRating", rating))
                .andExpect(status().isOk());

        List<Rating> found = ratingRepository.findAll();
        assertThat(found).extracting(Rating::getMessage).containsOnly(message);
        assertThat(found).extracting(Rating::getGood).containsOnly(goodRating);
        assertThat(found).extracting(Rating::getBad).containsOnly(badRating);
    }

    @Test
    public void whenValidInput_thenUpdateRating() throws Exception {
        Rating existingRating = new Rating("Bad Answer", 0, 1);
        ratingRepository.saveAndFlush(existingRating);
        String rating = "Bad";
        mvc.perform(put("/rating")
                .param("message", existingRating.getMessage())
                .param("answerRating", rating))
                .andExpect(status().isOk());

        List<Rating> found = ratingRepository.findAll();
        assertThat(found).extracting(Rating::getMessage).containsOnly(existingRating.getMessage());
        assertThat(found).extracting(Rating::getGood).containsOnly(existingRating.getGood());
        assertThat(found).extracting(Rating::getBad).containsOnly(existingRating.getBad() + 1);
    }

    @Test
    public void whenGetRatingAndUnauthorized_thenStatus401() throws Exception {
        Rating rating = new Rating("Good Answer", 1, 0);
        ratingRepository.saveAndFlush(rating);

        mvc.perform(get("/rating")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenGetRatingAndAuthorized_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        Rating ratingOne = new Rating("Good Answer", 1, 0);
        Rating ratingTwo = new Rating("Bad Answer", 0, 2);
        ratingRepository.saveAndFlush(ratingOne);
        ratingRepository.saveAndFlush(ratingTwo);

        mvc.perform(get("/rating")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].message", is(ratingOne.getMessage())))
                .andExpect(jsonPath("$[0].good", is(ratingOne.getGood())))
                .andExpect(jsonPath("$[0].bad", is(ratingOne.getBad())))
                .andExpect(jsonPath("$[1].message", is(ratingTwo.getMessage())))
                .andExpect(jsonPath("$[1].good", is(ratingTwo.getGood())))
                .andExpect(jsonPath("$[1].bad", is(ratingTwo.getBad())));
    }

    @Test
    public void  whenUnauthorizedDownloadCSV_thenStatus401() throws Exception {

        mvc.perform(get("/rating/exportCSV")
                .contentType(MEDIA_TYPE_CSV))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAuthorizedDownloadCSV_thenStatus200() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        String filename = "ratingData.csv";

        mvc.perform(get("/rating/exportCSV")
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
