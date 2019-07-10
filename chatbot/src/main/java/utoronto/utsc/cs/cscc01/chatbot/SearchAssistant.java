package utoronto.utsc.cs.cscc01.chatbot;

import java.util.ArrayList;
import java.util.Hashtable;

public interface SearchAssistant {
	public Hashtable<String, ArrayList<String>> simpleAssistantQuery(String q);
}
