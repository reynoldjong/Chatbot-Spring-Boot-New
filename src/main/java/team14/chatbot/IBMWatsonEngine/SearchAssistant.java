package team14.chatbot.IBMWatsonEngine;

import java.util.ArrayList;
import java.util.Hashtable;

public interface SearchAssistant {
	Hashtable<String, ArrayList<String>> simpleAssistantQuery(String q);
}
