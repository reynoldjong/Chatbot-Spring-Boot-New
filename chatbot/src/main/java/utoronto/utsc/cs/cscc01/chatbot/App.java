package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class App {
  public static void main(String[] args) throws LifecycleException {

    WatsonDiscovery wd = WatsonDiscovery.buildDiscovery();
    SearchEngine queryEngine = new QueryEngine(wd);

    Tomcat tomcat = new Tomcat();
    tomcat.setPort(9000);

    String contextPath = "/";
    String docBase = new File(".").getAbsolutePath();

    Context context = tomcat.addContext(contextPath, docBase);
    
    UserDatabase db = new TempDatabase();
    // programmatically add in servlets
    
    LoginServlet login = new LoginServlet(db);
    tomcat.addServlet(contextPath, "LoginServlet", login);
    context.addServletMapping("/login", "LoginServlet");
    
    LogoutServlet logout = new LogoutServlet();
    tomcat.addServlet(contextPath, "LogoutServlet", logout);
    context.addServletMapping("/logout", "LogoutServlet");

    AdminPage adminPage = new AdminPage();
    tomcat.addServlet(contextPath, "AdminPage", adminPage);
    context.addServletMapping("/adminpage", "AdminPage");

    QueryServlet queryServlet = new QueryServlet(queryEngine);
    tomcat.addServlet(contextPath, "QueryServlet", queryServlet);
    context.addServletMapping("/userquery", "QueryServlet");

    // start the server
    tomcat.start();
    tomcat.getServer().await();
  }
}
