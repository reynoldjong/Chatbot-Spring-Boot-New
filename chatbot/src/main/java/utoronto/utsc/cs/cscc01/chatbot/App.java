package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import javax.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Main class of the chatbot
 *
 */
public class App {
  public static void main(String[] args)
      throws ServletException, LifecycleException {
    
    Tomcat tomcat = TomcatServer.createTomcatServer();
    
    UserQuery query = new QueryServlet();
    
    String contextPath = "/";
    String docBase = new File(".").getAbsolutePath();
    
    Context context = tomcat.addContext(contextPath, docBase);
    
    String servletName = "MyQuery";
    String urlPattern = "/QueryServlet";
    
    tomcat.addServlet(contextPath, servletName, (QueryServlet) query);
    context.addServletMapping(urlPattern, servletName);
    
    // start the server
    tomcat.start();
    tomcat.getServer().await();
  }
}
