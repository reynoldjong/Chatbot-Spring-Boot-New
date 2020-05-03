package team14.chatbot.Repositories;

import java.sql.SQLException;

public interface UserDatabase {
  boolean verifyUser(String username, String password) throws SQLException;
  boolean connect();
}
