package utoronto.utsc.cs.cscc01.chatbot;

public class TextToSpeechEngine {
  private WatsonTextToSpeech watsonTTS;
  
  public TextToSpeechEngine(WatsonTextToSpeech tts) {
    this.watsonTTS = tts;
  }
}
