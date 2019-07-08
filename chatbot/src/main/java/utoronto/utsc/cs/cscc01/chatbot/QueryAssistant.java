package utoronto.utsc.cs.cscc01.chatbot;

import java.util.List;

import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DialogRuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageOutput;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

public class QueryAssistant implements SearchAssistant {
	private WatsonAssistant wa;
	
	public QueryAssistant(WatsonAssistant assistant) {
		this.wa = assistant;
	}
	
	public String simpleAssistantQuery(String q) {
	    Assistant service = wa.getAssistant();
	    
	    String result = "";
	    // TODO: we may have to move this session to query servlet itself
	    // so we aren't creating new session everytime
	    CreateSessionOptions options = new CreateSessionOptions.Builder(wa.getAssistantId()).build();
	    SessionResponse response = service.createSession(options).execute().getResult();
	    String sessionId = response.getSessionId();
	    
	    // build the message for ibm watson assisntant using user input q
	    MessageInput input = new MessageInput.Builder()
	        .messageType("text")
	        .text(q)
	        .build();

	    MessageOptions msgOptions = new MessageOptions.Builder(wa.getAssistantId(), sessionId)
	      .input(input)
	      .build();
	    
	    // grab response from watson assistant
	    MessageResponse msgResponse = service.message(msgOptions).execute().getResult();

	    //System.out.println(msgResponse);
	    
	    // unpack the output from assistant's response
	    MessageOutput output = msgResponse.getOutput();
	    List<DialogRuntimeResponseGeneric> outputList = output.getGeneric();
	    
	    // we only care about text and image response right now
	    // and we will append that to our result
	    // if assistant pattern match fails, it will return "Need to query"
	    for (DialogRuntimeResponseGeneric generic : outputList) {
	      if (generic.getResponseType().equals("text")){
	        result += generic.getText();
	      }
	      else if (generic.getResponseType().equals("image")) {
	        result += generic.getSource();
	      }
	    }
	    
	    return result;
	}
}
