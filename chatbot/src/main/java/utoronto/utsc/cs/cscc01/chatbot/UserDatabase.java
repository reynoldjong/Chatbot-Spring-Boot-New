package utoronto.utsc.cs.cscc01.chatbot;

public interface UserDatabase {
  boolean verifyUser(String username, String password);
  boolean connect();
}
