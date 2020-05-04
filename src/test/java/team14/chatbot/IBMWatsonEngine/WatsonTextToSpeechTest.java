package team14.chatbot.IBMWatsonEngine;

import static org.junit.Assert.assertSame;
import org.junit.Test;
import team14.chatbot.IBMWatsonEngine.WatsonTextToSpeech;

public class WatsonTextToSpeechTest {

  @Test
  public void testWatsonTextToSpeechSingleton() {
    WatsonTextToSpeech tts1 = WatsonTextToSpeech.buildWatsonTTS();
    WatsonTextToSpeech tts2 = WatsonTextToSpeech.buildWatsonTTS();
    assertSame(tts1, tts2);
  }
}
