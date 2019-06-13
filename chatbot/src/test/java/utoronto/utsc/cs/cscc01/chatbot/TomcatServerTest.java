package utoronto.utsc.cs.cscc01.chatbot;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Before;
import org.junit.Test;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TomcatServerTest {
  
  /*
  @Before
  public void setUp() throws LifecycleException {
    
    @WebServlet("/test")
    final class ServerTest extends HttpServlet {
        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
          PrintWriter writer = resp.getWriter();
          
          writer.append("Hello World");
          writer.flush();
        }
    }
    
    Tomcat tomcat = TomcatServer.createTomcatServer();
    
    ServerTest test = new ServerTest();
    
    String contextPath = "/";
    String docBase = new File(".").getAbsolutePath();
    
    Context context = tomcat.addContext(contextPath, docBase);
    
    String servletName = "MyTest";
    String urlPattern = "/test";
    
    tomcat.addServlet(contextPath, servletName, test);
    context.addServletMapping(urlPattern, servletName);
    
    tomcat.start();
    tomcat.getServer().await();
  }
  */
  
  @Test
  public void testDoGet() throws IOException {
    /*
    ServerTest test = new ServerTest();
    
    Tomcat tomcat = TomcatServer.createTomcatServer();
    
    String contextPath = "/";
    String docBase = new File(".").getAbsolutePath();
    
    Context context = tomcat.addContext(contextPath, docBase);
    
    String servletName = "MyTest";
    String urlPattern = "/test";
    
    tomcat.addServlet(contextPath, servletName, test);
    context.addServletMapping(urlPattern, servletName);
    */
    
    URL url = new URL("http://localhost:8080/test");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    url.openConnection();
    con.setRequestMethod("GET");
    
    
    InputStream is = con.getInputStream();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    
    String response = rd.readLine();
    System.out.println(response);
    assertEquals(response, "Hello World");
  }
}
