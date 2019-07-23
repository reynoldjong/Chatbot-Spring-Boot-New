package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;

// we are making the Watson TTS instance a singleton
public class WatsonTextToSpeech {
  private static WatsonTextToSpeech ttsi = null;
  
  private WatsonTextToSpeech() {
    IamOptions options = new IamOptions.Builder()
        .apiKey("o0xxQxZ21NgAvJLSBfQosUV8oodK6NVo0u0140l7juT-")
        .build();
    TextToSpeech textToSpeech = new TextToSpeech(options);
    textToSpeech.setEndPoint("https://gateway-wdc.watsonplatform.net/text-to-speech/api");
  }
  
  public static WatsonTextToSpeech buildWatsonTTS() {
    if (ttsi == null)
      ttsi = new WatsonTextToSpeech();
    return ttsi;
  }
}
