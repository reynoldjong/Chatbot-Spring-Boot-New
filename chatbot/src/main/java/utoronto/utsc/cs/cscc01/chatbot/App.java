package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import javax.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Delete this after
 *
 */
public class App {
  
  public class MockDatabase implements UserDatabase {

    @Override
    public boolean verifyUser(String username, String password) {
      String testUser = "JohnDoe";
      String testPassword = "abc123";
      
      boolean validated = username == testUser && testPassword == password;
      return validated;
    }
  }
  
  public static void main(String[] args)
      throws ServletException, LifecycleException {
    
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    
    String contextPath = "/";
    String docBase = new File(".").getAbsolutePath();
    
    //String webappDir = new File("src/main/webapp").getAbsolutePath();
    
    tomcat.addWebapp(contextPath, docBase);
    
    App myApp = new App();
    UserDatabase db = myApp.new MockDatabase();
    
    Context context = tomcat.addContext(contextPath, docBase);
    
    LoginServlet login = new LoginServlet(db);
    tomcat.addServlet(contextPath, "LoginServlet", login);
    context.addServletMapping("/login", "LoginServlet");

    LogoutServlet logout = new LogoutServlet();
    tomcat.addServlet(contextPath, "LogoutServlet", logout);
    context.addServletMapping("/logout", "LogoutServlet");
    
    AdminPage adminpage = new AdminPage();
    tomcat.addServlet(contextPath, "AdminPage", adminpage);
    context.addServletMapping("/adminpage", "AdminPage");
    
    // start the server
    tomcat.start();
    tomcat.getServer().await();
  }
}
