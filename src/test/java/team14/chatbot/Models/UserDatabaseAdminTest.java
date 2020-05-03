package team14.chatbot.Models;


import static org.junit.Assert.*;

import java.sql.*;

import org.junit.Before;
import org.junit.Test;
import team14.chatbot.Repository.UserDatabaseAdmin;

public class UserDatabaseAdminTest {

    private UserDatabaseAdmin db;

    @Before
    public void setUp() {

        db = new UserDatabaseAdmin();
    }

    @Test
    public void testInsert() throws SQLException {
        db.insertUser("test", "test");
        // Assume that query works
        assertEquals(db.getPassword("test"), "test");
        db.removeUser("test");
    }

    @Test
    public void testRemove() throws SQLException {
        // Assume insert works
        db.insertUser("test", "test");
        db.removeUser("test");
        assertEquals(db.getPassword("test"), "");

    }
}
