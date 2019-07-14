package utoronto.utsc.cs.cscc01.chatbot;

import java.util.ArrayList;
import java.util.Hashtable;

public interface SearchEngine {
	Hashtable<String, ArrayList<String>> simpleQuery(String s);
}
