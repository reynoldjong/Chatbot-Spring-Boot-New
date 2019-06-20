package utoronto.utsc.cs.cscc01.chatbot;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class DataBase {

    // TODO: get a url for connection
    private final String url = "";
    private final String user = "";

    public void connect() {
        Connection connect;
        PreparedStatement stmt;
        FileInputStream input;
        try {
            connect = DriverManager.getConnection(url);
            String sql = "";
            stmt = connect.prepareStatement(sql);
            File f = new File("articles.txt");
            input = new FileInputStream(f);
            stmt.setBinaryStream(1, input);
            stmt.executeUpdate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        DataBase db = new DataBase();
        db.connect();
    }

}
