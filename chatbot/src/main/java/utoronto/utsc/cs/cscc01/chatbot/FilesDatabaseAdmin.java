package utoronto.utsc.cs.cscc01.chatbot;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Admin page for handling uploading/removing files from the database
 *
 */
public class FilesDatabaseAdmin {

    private Connection connection;
    private String filePath = "../chatbot/files/";

    public FilesDatabaseAdmin() {
        this.connection = null;
    }


    /**
     * Make a connection to database
     */
    public boolean connect() {

        // Connect to FileDatabase.db at project folder and return true if it is successful.
        try {

            this.connection = DriverManager.getConnection("jdbc:sqlite:Files.db");
            return true;

        } catch (SQLException e) {

            System.out.println("Can't connect to database");
            return false;
        }
    }

    public void close() {

        try {

            this.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * Upload files to the database using the given filepath
//     *
//     * @param filepath
//     */
//    public void uploadFiles(String filepath) {
//        File dir = new File(filepath);
//        // Get all files in the directory
//        File[] directoryListing = dir.listFiles();
//        if (directoryListing != null) {
//            for (File f : directoryListing) {
//                // Get the content of the file into bytes
//                byte[] b = readFile(f);
//                // Get the name of the file with extension
//                String name = f.getName();
//                // Insert the items into the database
//                insert(name, b);
//            }
//        }
//    }

    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param filename
     * @param content
     */
    public void insertFile(String filename, InputStream content, long size) {
        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO FILES(FILENAME, FILE) VALUES(?, ?)";
        try {
            // Create SQL statement for inserting
            this.connect();
            stmt = this.connection.prepareStatement(insertSQL);
            stmt.setString(1, filename);
            stmt.setBinaryStream(2, content, (int) size);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("UH OH");
            e.printStackTrace();
        }
        finally{
            this.close();
        }
    }

    /**
     * Remove the given information of the given filename from the database
     *
     * @param filename
     */
    public void remove(String filename) {
        PreparedStatement stmt;

        // SQL code for delete
        String deleteSQL = "DELETE FROM FILES WHERE filename = ?";
        try {
           
            // Create SQL statement for deleting
            stmt = this.connection.prepareStatement(deleteSQL);
            stmt.setString(1, filename);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       
    }

    /**
     * Read file using the given filename
     *
     * @param filename
     */

    public void extractFile(String filename) {
        // update sql

        FileOutputStream fos;
        // Connection conn = null;
        ResultSet rs = checkFile(filename);


        if (rs != null) {
            // write binary stream into file
            try {
                
                File file = new File(filePath + filename);
                fos = new FileOutputStream(file);

                while (rs.next()) {
                    InputStream input = rs.getBinaryStream(1);
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File uploaded");
        }
    }


    public ResultSet checkFile(String filename) {
        String selectSQL = "SELECT file FROM FILES WHERE filename=?";
        ResultSet rs = null;
        // Connection conn = null;
        PreparedStatement stmt = null;

        try {
            stmt = this.connection.prepareStatement(selectSQL);
            stmt.setString(1, filename);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    public List<UploadedFile> list() throws SQLException {

        List<UploadedFile> listUploadedFile = new ArrayList<>();

        String sql = "SELECT * FROM files ORDER BY filename";
        Statement statement = this.connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            int id = result.getInt("fileid");
            String filename = result.getString("filename");
            UploadedFile file = new UploadedFile(id, filename);

            listUploadedFile.add(file);
        }

        return listUploadedFile;
    }

    public static void main(String args[]) {
        FilesDatabaseAdmin db = new FilesDatabaseAdmin();
        db.connect();
        db.extractFile("CSC373_Assignment2.pdf");
    }


}
