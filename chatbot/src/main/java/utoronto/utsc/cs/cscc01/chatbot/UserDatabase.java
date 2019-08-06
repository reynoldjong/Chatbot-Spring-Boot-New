package utoronto.utsc.cs.cscc01.chatbot;

import java.sql.SQLException;

public interface UserDatabase {
  boolean verifyUser(String username, String password) throws SQLException;
  boolean connect();
}
