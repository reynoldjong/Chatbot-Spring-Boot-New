package utoronto.utsc.cs.cscc01.chatbot;

public class App {
  public static void main(String[] args) {

	System.out.println("Hello World");
	WatsonDiscovery wd = WatsonDiscovery.buildDiscovery();
	SearchEngine queryEngine = new QueryEngine(wd);
  }
}
