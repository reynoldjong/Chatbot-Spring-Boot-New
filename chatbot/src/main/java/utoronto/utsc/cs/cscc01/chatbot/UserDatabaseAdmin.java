package utoronto.utsc.cs.cscc01.chatbot;

import java.sql.*;



/**
 * Admin page for handling uploading/removing files from the database
 *
 */
public class UserDatabaseAdmin extends AbstractDatabaseAdmin implements UserDatabase {


    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param username
     * @param password
     */
    public void insertUser(String username, String password) {
        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO USERS(USERNAME, PASSWORD) VALUES(?, ?)";

        String pw = getPassword(username);

        if (pw.equals("")) {

            try {
                // Create SQL statement for inserting
                stmt = this.connection.prepareStatement(insertSQL);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("Username is already used!");
        }
    }


    /**
     * Remove the given information of the given filename from the database
     *
     * @param username
     */
    public void removeUser(String username) {
        PreparedStatement stmt;

        // SQL code for delete
        String deleteSQL = "DELETE FROM USERS WHERE username = ?";
        try {
            // Create SQL statement for deleting
            stmt = this.connection.prepareStatement(deleteSQL);
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Read file using the given filename
     *
     * @param username
     * @param password
     */

    public boolean verifyUser(String username, String password) {

        // update sql
        String pwGot = getPassword(username);

        if (pwGot.equals(password)) {
            return true;
        }

        return false;
    }


    public String getPassword(String username) {


        String sql = "SELECT * FROM Users WHERE username = ?";
        String password = "";
        ResultSet rs;
        PreparedStatement stmt;
        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                password = rs.getString("password");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return password;
    }

    public static void main(String[] args) {

        UserDatabaseAdmin db = new UserDatabaseAdmin();
        if (db.connect()) {
            db.insertUser("test", "test");
            db.insertUser("test", "test");
            System.out.println(db.verifyUser("test", "test"));
            System.out.println(db.verifyUser("test", "test2"));
            db.removeUser("test");
        }

    }



}

