package utoronto.utsc.cs.cscc01.chatbot;


import java.io.*;
import java.sql.*;



/**
 * Admin page for handling uploading/removing files from the database
 *
 */
public class FilesDatabaseAdmin {

    // SQL code for insert
    private static final String INSERT_SQL = "INSERT INTO FILES(NAME, CONTENT) VALUES(?, ?)";
    // SQL code for delete
    private static final String DELETE_SQL = "DELETE FROM FILES WHERE name = ?";
    // SQL code for query
    private static final String QUERY_SQL = "SELECT * FROM FILES WHERE name = ?";
    private Connection connection;

    public FilesDatabaseAdmin() {
        this.connection = null;
    }


    /**
     * Make a connection to database
     */
    public boolean connect() {
        // Use JDBC for connection
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find JDBC");
        }

        // Connect to FileDatabase.db at project folder and return true if it is successful.
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:FileDatabase.db");
            return true;
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            return false;
        }
    }

    /**
     * Upload files to the database using the given filepath
     */
    public void uploadFiles(String filepath) {
        File dir = new File(filepath);
        // Get all files in the directory
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                // Get the content of the file into bytes
                byte[] b = readFile(f);
                // Get the name of the file without extension
                String name = f.getName().replaceFirst("[.][^.]+$", "");
                // Insert the items into the database
                insert(name, b);
            }
        }
    }

    /**
     * Insert the given information to the database, filename and the content of files
     */
    public void insert(String name, byte[] b) {
        PreparedStatement stmt;
        try {
            // Create SQL statement for inserting
            stmt = this.connection.prepareStatement(INSERT_SQL);
            stmt.setString(1, name);
            stmt.setBytes(2, b);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Read the content of the file into bytes
     */
    private byte[] readFile(File f) {
        ByteArrayOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(f);
            // Create a byte array
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            // Read each bytes into the outputstream
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    /**
     * Remove the given information of the given filename from the database
     */
    public void remove(String name) {
        PreparedStatement stmt;
        try {
            // Create SQL statement for deleting
            stmt = this.connection.prepareStatement(DELETE_SQL);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Made a query using the given filename
     */

    public String query(String name) {
        PreparedStatement stmt;
        String result = "";
        try {
            // Create SQL statement for query
            stmt = this.connection.prepareStatement(QUERY_SQL);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            // Get the content of the file from the query
            while (rs.next()) {
                result = rs.getString("content");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
