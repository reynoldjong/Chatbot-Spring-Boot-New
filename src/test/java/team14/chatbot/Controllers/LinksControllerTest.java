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
import team14.chatbot.IBMWatsonEngine.CrawlerEngine;
import team14.chatbot.IBMWatsonEngine.WatsonDiscovery;
import team14.chatbot.Models.Link;
import team14.chatbot.Repository.LinksRepository;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class LinksControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LinksRepository linksRepository;

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
        linksRepository.deleteAll();
        new CrawlerEngine(WatsonDiscovery.buildDiscovery()).removeUrl("https://www.justForTestPurpose.com");
    }

    @Test
    public void whenValidInputAndUnAuthorized_thenStatus401() throws Exception {
        String url = "https://www.justForTestPurpose.com";
        String depth = "2";
        mvc.perform(put("/webcrawler")
                .param("url", url)
                .param("depth", depth))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenValidInputAndAuthorized_thenCreateLink() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");
        String url = "https://www.justForTestPurpose.com";
        String depth = "2";
        mvc.perform(put("/webcrawler")
                .header("Authorization", "Bearer " + accessToken)
                .param("url", url)
                .param("depth", depth))
                .andExpect(status().isOk());

        Iterable<Link> found = linksRepository.findAll();
        assertThat(found).extracting(Link::getSeed).containsOnly(url);
        assertThat(found).extracting(Link::getDepth).containsOnly(Integer.parseInt(depth));
    }

    @Test
    public void whenValidInputAndAuthorized_thenUpdateLink() throws Exception {
        HashMap<String, HashMap<String, String>> nothing = new HashMap<>();
        String accessToken = obtainAccessToken("admin", "admin");
        Link existingLink = new Link("https://www.justForTestPurpose.com", 2, SerializationUtils.serialize(nothing));
        linksRepository.save(existingLink);
        String depth = "3";
        mvc.perform(put("/webcrawler")
                .header("Authorization", "Bearer " + accessToken)
                .param("url", existingLink.getSeed())
                .param("depth", depth))
                .andExpect(status().isOk());

        Iterable<Link> found = linksRepository.findAll();
        assertThat(found).extracting(Link::getSeed).containsOnly(existingLink.getSeed());
        assertThat(found).extracting(Link::getDepth).containsOnly(Integer.parseInt(depth));
    }

    @Test
    public void whenGetFileAndUnauthorized_thenStatus401() throws Exception {
        HashMap<String, HashMap<String, String>> nothing = new HashMap<>();
        Link testLink = new Link("www.test.com", 2, SerializationUtils.serialize(nothing));
        linksRepository.save(testLink);

        mvc.perform(get("/webcrawler")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenGetFileAndAuthorized_thenStatus200() throws Exception {
        HashMap<String, HashMap<String, String>> nothingOne = new HashMap<>();
        HashMap<String, HashMap<String, String>> nothingTwo= new HashMap<>();
        String accessToken = obtainAccessToken("admin", "admin");
        Link testLinkOne = new Link("www.test1.com", 2, SerializationUtils.serialize(nothingOne));
        Link testLinkTwo = new Link("www.test2.com", 3, SerializationUtils.serialize(nothingTwo));
        linksRepository.save(testLinkOne);
        linksRepository.save(testLinkTwo);

        mvc.perform(get("/webcrawler")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].seed", is(testLinkOne.getSeed())))
                .andExpect(jsonPath("$[0].depth", is(testLinkOne.getDepth())))
                .andExpect(jsonPath("$[1].seed", is(testLinkTwo.getSeed())))
                .andExpect(jsonPath("$[1].depth", is(testLinkTwo.getDepth())));
    }

    @Test
    public void whenDeleteLinkAndUnauthorized_thenStatus401() throws Exception {
        HashMap<String, HashMap<String, String>> nothing = new HashMap<>();
        Link testLink = new Link("www.test.com", 2, SerializationUtils.serialize(nothing));
        linksRepository.save(testLink);

        mvc.perform(delete("/webcrawler")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenDeleteLinkAndAuthorized_thenStatus200() throws Exception {
        HashMap<String, HashMap<String, String>> nothing = new HashMap<>();
        String accessToken = obtainAccessToken("admin", "admin");
        Link testLink = new Link("https://www.justForTestPurpose.com", 2, SerializationUtils.serialize(nothing));
        linksRepository.save(testLink);

        mvc.perform(delete("/webcrawler")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .param("url", testLink.getSeed()))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThat(linksRepository.findAll()).isEmpty();
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
