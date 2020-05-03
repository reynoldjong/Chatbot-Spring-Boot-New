package team14.chatbot.Repository;

import java.sql.SQLException;

public interface UserDatabase {
  boolean verifyUser(String username, String password) throws SQLException;
  boolean connect();
}
