package utoronto.utsc.cs.cscc01.chatbot;


import java.io.*;
import java.sql.*;

public class Database {


    private static final String INSERT_SQL = "INSERT INTO FILES(NAME, CONTENT) VALUES(?, ?)";
    private static final String DELETE_SQL = "DELETE FROM FILES WHERE name = ?";
    private static final String QUERY_SQL = "SELECT * FROM FILES WHERE name = ?";
    private Connection connection;

    public Database() {
        this.connection = null;
    }

    public boolean connect() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find JDBC");
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:Database.db");
            return true;
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            return false;
        }
    }

    public void uploadFiles(String filepath) {
        File dir = new File(filepath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                byte[] b = readFile(f);
                String name = f.getName().replaceFirst("[.][^.]+$", "");
                insert(name, b);
            }
        }
    }

    public void insert(String name, byte[] b) {
        PreparedStatement stmt;
        try {
            stmt = this.connection.prepareStatement(INSERT_SQL);
            stmt.setString(1, name);
            stmt.setBytes(2, b);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private byte[] readFile(File f) {
        ByteArrayOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    public void remove(String name) {
        PreparedStatement stmt;
        try {
            stmt = this.connection.prepareStatement(DELETE_SQL);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String query(String name) {
        PreparedStatement stmt;
        String result = "";
        try {
            stmt = this.connection.prepareStatement(QUERY_SQL);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString("content");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
