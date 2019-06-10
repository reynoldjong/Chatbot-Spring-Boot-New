package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

/**
 * Main class of the chatbot
 *
 */
public class App {
	public static void main(String[] args) throws ServletException, LifecycleException {
		// start the tomcat server
		String startDir = "src/main/java/utoronto/utsc/cs/cscc01/chatbot/";
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080); // use port 8080 for now

		StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(startDir).getAbsolutePath());

		WebResourceRoot resources = new StandardRoot(ctx);
		ctx.setResources(resources);
		
		tomcat.start();
		tomcat.getServer().await();
	}
}
