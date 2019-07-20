package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.lucene.queryparser.classic.ParseException;

public interface SearchEngine {
	Hashtable<String, ArrayList<String>> simpleQuery(String s) throws IOException;
}
