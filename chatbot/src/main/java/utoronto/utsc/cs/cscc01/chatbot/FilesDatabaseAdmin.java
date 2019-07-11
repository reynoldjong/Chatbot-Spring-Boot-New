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
    private HandleFilesEngine fileEngine;

    public FilesDatabaseAdmin() {
        this.connection = null;
        this.fileEngine = new HandleFilesEngine(WatsonDiscovery.buildDiscovery());
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


    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param filename
     * @param content
     */
    public void insert(String filename, InputStream content, InputStream contentForDb, long size) {

        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO FILES VALUES(?, ?, ?)";

        String existDocumentId = getDocumentId(filename);

        System.out.println(existDocumentId);

        if (existDocumentId.equals("")) {

            String documentId = this.fileEngine.uploadFiles(content, filename);

            try {
                // Create SQL statement for inserting
                stmt = this.connection.prepareStatement(insertSQL);
                stmt.setString(1, documentId);
                stmt.setString(2, filename);
                stmt.setBinaryStream(3, contentForDb, (int) size);
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            update(filename, existDocumentId, content, contentForDb, size);
        }
    }


    public void update(String filename, String existDocumentId, InputStream content, InputStream contentForDb,long size) {
        PreparedStatement stmt;
        String updateSQL = "UPDATE FILES SET DOCUMENTID = ?, FILE = ? WHERE FILENAME = ?";
        String documentId = this.fileEngine.updateFiles(content, filename, existDocumentId);
        try {
            // Create SQL statement for inserting
            stmt = this.connection.prepareStatement(updateSQL);
            stmt.setString(1, documentId);
            stmt.setBinaryStream(2, contentForDb, (int) size);
            stmt.setString(3, filename);
            stmt.executeUpdate();
         

        } catch (SQLException e) {
            System.err.println("Can't update file");
        }
    }

    /**
     * Remove the given information of the given filename from the database
     *
     * @param filename
     */
    public void remove(String filename) {
        PreparedStatement stmt;
        String documentId = getDocumentId(filename);
        // SQL code for delete
        String deleteSQL = "DELETE FROM FILES WHERE filename = ?";

        String result = this.fileEngine.removeFiles(documentId);

        if (result.equals("deleted")) {
            try {
                // Create SQL statement for deleting
                stmt = this.connection.prepareStatement(deleteSQL);
                stmt.setString(1, filename);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

//    /**
//     * Read file using the given filename
//     *
//     * @param filename
//     */
//
//    public void extractFile(String filename) {
//        // update sql
//
//        FileOutputStream fos;
//        // Connection conn = null;
//        ResultSet rs = getDocumentId(filename);
//
//
//        if (rs != null) {
//            // write binary stream into file
//            try {
//
//                File file = new File(filePath + filename);
//                fos = new FileOutputStream(file);
//
//                while (rs.next()) {
//                    InputStream input = rs.getBinaryStream("file");
//                    byte[] buffer = new byte[1024];
//                    while (input.read(buffer) > 0) {
//                        fos.write(buffer);
//                    }
//                }
//            } catch (SQLException | IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("File uploaded");
//        }
//    }


    public String getDocumentId(String filename) {
        String selectSQL = "SELECT * FROM FILES WHERE filename=?";
        ResultSet rs;
        PreparedStatement stmt;
        String documentId = "";

        try {
            stmt = this.connection.prepareStatement(selectSQL);
            stmt.setString(1, filename);
            rs = stmt.executeQuery();

            while (rs.next()) {
                documentId = rs.getString("DOCUMENTID");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return documentId;
    }

    public List<UploadedFile> list() throws SQLException {

        List<UploadedFile> listUploadedFile = new ArrayList<>();

        String sql = "SELECT * FROM files ORDER BY filename";
        Statement statement = this.connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            int id = result.getInt("documentId");
            String filename = result.getString("filename");
            UploadedFile file = new UploadedFile(id, filename);

            listUploadedFile.add(file);
        }

        return listUploadedFile;
    }

}
