package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/tts")
public class TextToSpeechServlet extends HttpServlet {
  private TextToSpeechEngine ttsEngine;
  
  public void init(ServletConfig config) {
    // watsonTTS
    this.ttsEngine = new TextToSpeechEngine(WatsonTextToSpeech.buildWatsonTTS());
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // front end needs to pass in this parameter
    // String text = req.getQueryString(); -- for testing
    String text = req.getParameter("ttsText");
    byte[] buffer = new byte[4194304];
    buffer = ttsEngine.createAudioFromText(text);
    //System.out.println(buffer);
    ServletOutputStream writer = resp.getOutputStream();
    writer.write(buffer);
  }
  
  
}
