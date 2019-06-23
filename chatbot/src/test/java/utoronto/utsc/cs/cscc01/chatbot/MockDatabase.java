package utoronto.utsc.cs.cscc01.chatbot;

public class MockDatabase implements UserDatabase {

  @Override
  public boolean verifyUser(String username, String password) {
    String testUser = "JohnDoe";
    String testPassword = "abc123";
    
    boolean validated = username == testUser && testPassword == password;
    return validated;
  }
}