package utoronto.utsc.cs.cscc01.chatbot;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinksDatabaseAdmin extends AbstractDatabaseAdmin {

    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param seed
     * @param content
     */
    public void insert(String seed, HashMap<String, HashMap<String, String>> content) {

        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO LINKS(SEED, HASHMAP, DATE) VALUES(?, ?, ?)";

        String existLink = getLink(seed);

        if (existLink.equals("")) {

            try {
                connect();
                // Create SQL statement for inserting
                stmt = this.connection.prepareStatement(insertSQL);
                stmt.setString(1, seed);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream output = new ObjectOutputStream(b);
                output.writeObject(content);
                byte[] bytes = b.toByteArray();
                ByteArrayInputStream input = new ByteArrayInputStream(bytes);
                stmt.setBinaryStream(2, input, bytes.length);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String formattedDate = formatter.format(date);
                stmt.setString(3, formattedDate);
                stmt.executeUpdate();

            } catch (SQLException | IOException e) {
                System.out.println(e);
            } finally {

                close();

            }
        } else {
            remove(seed);
            insert(seed, content);
        }

    }

    /**
     * Remove the given information of the given filename from the database
     *
     * @param seed
     */
    public boolean remove(String seed) {
        PreparedStatement stmt;
        String documentId = getLink(seed);
        // SQL code for delete
        String deleteSQL = "DELETE FROM LINKS WHERE seed = ?";
        try {
            connect();
            // Create SQL statement for deleting
            stmt = this.connection.prepareStatement(deleteSQL);
            stmt.setString(1, seed);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {

            close();
        }
        return false;
    }

    /**
     * Read file using the given filename
     *
     * @param seed
     */

    public HashMap<String, HashMap<String, String>> extractLinkCollection(String seed) throws SQLException, IOException, ClassNotFoundException {
        // update sql

        HashMap<String, HashMap<String, String>> linksCollection = new HashMap<>();
        // Connection conn = null;

        if (!getLink(seed).equals("")) {
            connect();
            // write binary stream into file
            PreparedStatement stmt = this.connection.prepareStatement("SELECT hashmap FROM LINKS WHERE seed=?");
            stmt.setString(1, seed);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InputStream input = rs.getBinaryStream(1);
                ObjectInputStream ois = new ObjectInputStream(input);
                linksCollection = (HashMap<String, HashMap<String, String>>) ois.readObject();
            }

        } else {

            throw new SQLException();
        }

        return linksCollection;
    }


    public String getLink(String link) {
        String selectSQL = "SELECT * FROM LINKS WHERE seed=?";
        ResultSet rs;
        PreparedStatement stmt;
        String seed = "";

        try {
            connect();
            stmt = this.connection.prepareStatement(selectSQL);
            stmt.setString(1, link);
            rs = stmt.executeQuery();

            while (rs.next()) {
                seed = rs.getString("SEED");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally{

            close();
        }

        return seed;
    }

    public List<CrawledLink> list() throws SQLException {
        List<CrawledLink> listCrawledLink = new ArrayList<>();
        try{

            String sql = "SELECT * FROM links ORDER BY seed";
            connect();

            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String seed = result.getString("seed");
                String date = result.getString("date");
                CrawledLink link = new CrawledLink(seed, date);

                listCrawledLink.add(link);
            }


        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{

            close();
        }
        return listCrawledLink;

    }
}