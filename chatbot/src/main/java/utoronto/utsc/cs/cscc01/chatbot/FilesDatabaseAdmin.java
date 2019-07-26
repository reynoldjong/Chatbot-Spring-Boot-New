package utoronto.utsc.cs.cscc01.chatbot;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Admin page for handling uploading/removing files from the database
 *
 */
public class FilesDatabaseAdmin extends AbstractDatabaseAdmin {

    private String filePath = "../chatbot/files/";
    private HandleFilesEngine fileEngine;

    public FilesDatabaseAdmin() {
       
        WatsonDiscovery w =  WatsonDiscovery.buildDiscovery();
        this.fileEngine = new HandleFilesEngine(w);
 
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
        String insertSQL = "INSERT INTO FILES VALUES(?, ?, ?, ?)";

        String existDocumentId = getDocumentId(filename);

        System.out.println(existDocumentId);

        if (existDocumentId.equals("")) {

            String documentId = this.fileEngine.uploadFiles(content, filename);

            try {
                connect();
                // Create SQL statement for inserting
                stmt = this.connection.prepareStatement(insertSQL);
                stmt.setString(1, documentId);
                stmt.setString(2, filename);
                stmt.setBinaryStream(3, contentForDb, (int) size);
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String formattedDate = formatter.format(date);
                stmt.setString(4, formattedDate);
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            finally{

                close();

            }

        } else {
            update(filename, existDocumentId, content, contentForDb, size);
        }
    }


    public void update(String filename, String existDocumentId, InputStream content, InputStream contentForDb,long size) {
        PreparedStatement stmt;
        String updateSQL = "UPDATE FILES SET DOCUMENTID = ?, FILE = ?, DATE = ? WHERE FILENAME = ?";
        String documentId = this.fileEngine.updateFiles(content, filename, existDocumentId);
        try {
            connect();
            // Create SQL statement for inserting
            stmt = this.connection.prepareStatement(updateSQL);
            stmt.setString(1, documentId);
            stmt.setBinaryStream(2, contentForDb, (int) size);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            String formattedDate = formatter.format(date);
            stmt.setString(3, formattedDate);
            stmt.setString(4, filename);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Can't update file");
        }

        finally{

            close();
        }
    }

    /**
     * Remove the given information of the given filename from the database
     *
     * @param filename
     */
    public boolean remove(String filename) {
        PreparedStatement stmt;
        String documentId = getDocumentId(filename);
        // SQL code for delete
        String deleteSQL = "DELETE FROM FILES WHERE filename = ?";

        String result = this.fileEngine.removeFiles(documentId);

        if (result.equals("deleted")) {
            try {
                connect();
                // Create SQL statement for deleting
                stmt = this.connection.prepareStatement(deleteSQL);
                stmt.setString(1, filename);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            finally {

                close();
            }
        }
        return false;
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
            connect();
            stmt = this.connection.prepareStatement(selectSQL);
            stmt.setString(1, filename);
            rs = stmt.executeQuery();

            while (rs.next()) {
                documentId = rs.getString("DOCUMENTID");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally{

            close();
        }

        return documentId;
    }

    public List<UploadedFile> list() throws SQLException {
        List<UploadedFile> listUploadedFile = new ArrayList<>();
        try{


            String sql = "SELECT * FROM files ORDER BY filename";
            connect();

            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("documentId");
                String filename = result.getString("filename");
                String date = result.getString("date");
                UploadedFile file = new UploadedFile(id, filename, date);

                listUploadedFile.add(file);
            }


        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{

            close();
        }
        return listUploadedFile;
       
    }

    public static void main(String[] args) {

        FilesDatabaseAdmin db = new FilesDatabaseAdmin();
            db.remove("MSIN3004 notes.docx");


    }

}
