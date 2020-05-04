package team14.chatbot.Service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import team14.chatbot.ChatbotApplication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is testing the retrieval of OAuth tokens that will be used in the tests of all the protected HTTP methods.
 * In other words, this confirms the functionality of authentication
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChatbotApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class OAuthTokenTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void whenInvalidUserCredentials_thenStatus400() throws Exception {
        String username = "wrong";
        String password = "wrong";
        String clientId = "chatbot-admin";
        String clientSecret = "secret";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

         mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId,clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenInvalidClientCredentials_thenStatus400() throws Exception {
        String username = "admin";
        String password = "admin";
        String clientId = "wrongId";
        String clientSecret = "wrongSecret";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId,clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenValidCredentials_thenStatus200() throws Exception {
        String username = "admin";
        String password = "admin";
        String clientId = "chatbot-admin";
        String clientSecret = "secret";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId,clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}
