package utoronto.utsc.cs.cscc01.chatbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class AbstractDatabaseAdmin {


    public Connection connection;

    public AbstractDatabaseAdmin() {
        this.connection = null;
    }

    /**
     * Make a connection to database
     */
    public boolean connect() {

        // Connect to FileDatabase.db at project folder and return true if it is successful.
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:Chatbot.db");
            return true;

        } catch (SQLException e) {

            System.out.println("Can't connect to database");
            return false;
        }
    }


    /**
     * Close a database connection if it is connected
     */
    public boolean close() {

        if (this.connection != null) {
            try {

                this.connection.close();

                return true;

            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }
        }
        return false;
    }



}
