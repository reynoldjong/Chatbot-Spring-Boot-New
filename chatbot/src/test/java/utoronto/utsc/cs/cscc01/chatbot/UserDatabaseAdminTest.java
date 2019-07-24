package utoronto.utsc.cs.cscc01.chatbot;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDatabaseAdminTest {

    @InjectMocks
    UserDatabaseAdmin userDb;

    @Mock
    Connection conn;

    @Mock
    PreparedStatement stmt;

    @Mock
    ResultSet rs;


//    @Before
//    public void setUp() throws Exception {
//        when(conn.prepareStatement("SELECT * FROM Users WHERE username = ?")).thenReturn(stmt);
//        when(stmt.executeQuery()).thenReturn(rs);
//        when(rs.next()).thenReturn(true);
//        when(rs.getString("password")).thenReturn("password");
//
//    }
//
//    @Test
//    public void testQuery() throws SQLException {
//        when(conn.prepareStatement("SELECT * FROM Users WHERE username = ?")).thenReturn(stmt);
//        when(stmt.executeQuery()).thenReturn(rs);
//        when(rs.next()).thenReturn(true);
//        when(rs.getString("password")).thenReturn("password");
//        assertEquals(userDb.getPassword("user"), "password");
//    }
//
//    @Test
//    public void testIfConnectedToDb() {
//        assertTrue(userDb.connect());
//    }
//
//
//    @Test
//    public void testIfUsersInserted() throws Exception {
//
//        userDb.insertUser("user", "password");
//        assertEquals(userDb.getPassword("user"), "password");
//    }
//
//    @Test
//    public void testVerifyingUserRightPassword() {
//        assertTrue(userDb.verifyUser("user", "password"));
//
//    }
//
//    @Test
//    public void testVerifyingUserWrongPassword() {
//        assertFalse(userDb.verifyUser("user", "password"));
//    }
//
//    @Test
//    public void testIfUsersRemoved() {
//        userDb.removeUser("user");
//        assertEquals(userDb.getPassword("user"), "");
//    }
//
//    @Test
//    public void testIfConnectionClosed() {
//        assertTrue(userDb.close());
//    }

}
