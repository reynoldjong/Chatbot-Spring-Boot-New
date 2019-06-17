package utoronto.utsc.cs.cscc01.chatbot;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Before;

public class LoginServletTest {
  @Before
  public void setUp() throws LifecycleException {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.start();
    
    UserDatabase db = new MockDatabase();
    tomcat.addContext("/loginPage", "LoginServlet");
  }
}
