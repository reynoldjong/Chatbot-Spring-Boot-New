package utoronto.utsc.cs.cscc01.chatbot;


import java.io.*;
import java.sql.*;

public class DataBase {


    private static final String INSERT_SQL = "INSERT INTO FILES(NAME, CONTENT) VALUES(?, ?)";
    private Connection connection;

    public DataBase () {
        this.connection = null;
    }

    public boolean connect() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find JDBC");
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:FileDatabase.db");
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
                System.out.println(name);
                insert(name, b);
            }
        }
    }

    public void insert(String name, byte[] b) {
        System.out.println(this.connection);
        PreparedStatement stmt;
        try {
            stmt = this.connection.prepareStatement(INSERT_SQL);
            stmt.setString(1, name);
            System.out.println(stmt.toString());
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


    public static void main(String[] args) {
        DataBase db = new DataBase();
        if (db.connect()) {
            db.uploadFiles("../chatbot/files");
        }
    }

}
