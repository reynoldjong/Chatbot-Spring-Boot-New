package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;

public class WatsonAssistant {
  private static WatsonAssistant watsonAssistant = null;
  private Assistant assistant;
  private static final String assistantId =
      "84452a24-45c5-45db-af2d-3957c16a6f1e";

  private WatsonAssistant() {
    IamOptions options = new IamOptions.Builder()
        .apiKey("5pMstPSablFoCNh96IJhPI9EUCL0akMKqqR3-4l1pFJo").build();
    this.assistant = new Assistant("2019-06-29", options);
    this.assistant
        .setEndPoint("https://gateway-wdc.watsonplatform.net/assistant/api");
  }

  public Assistant getAssistant() {
    return this.assistant;
  }

  public static WatsonAssistant buildAssistant() {
    if (watsonAssistant == null)
      watsonAssistant = new WatsonAssistant();
    return watsonAssistant;
  }

  public String getAssistantId() {
    return this.assistantId;
  }

}
