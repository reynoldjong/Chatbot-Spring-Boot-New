package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for handling text to speech request by the front end
 * @author Chris
 *
 */
@WebServlet(urlPatterns = "/tts")
public class TextToSpeechServlet extends HttpServlet {
  private TextToSpeechEngine ttsEngine;

  public void init() {
    // watsonTTS
    this.ttsEngine =
        new TextToSpeechEngine(WatsonTextToSpeech.buildWatsonTTS());
  }

  /**
   * this servlet post request will return a 4mb audio byte string encoded as
   * mp3
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String text = req.getParameter("ttsText");
    byte[] buffer = new byte[4194304];
    buffer = ttsEngine.createAudioFromText(text);

    resp.getWriter().write(ttsEngine.toBase64(buffer));
  }


}
