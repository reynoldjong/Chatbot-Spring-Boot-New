package utoronto.utsc.cs.cscc01.chatbot;

import java.util.ArrayList;
import java.util.Hashtable;

public interface SearchAssistant {
	Hashtable<String, ArrayList<String>> simpleAssistantQuery(String q);
}
