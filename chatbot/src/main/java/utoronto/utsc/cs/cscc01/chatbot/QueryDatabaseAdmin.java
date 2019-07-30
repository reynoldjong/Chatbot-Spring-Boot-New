package utoronto.utsc.cs.cscc01.chatbot;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDatabaseAdmin extends AbstractDatabaseAdmin {



    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param query
     */
    public void insertQuery(String query) {
        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO QUERIES(QUERY, FREQUENCY) VALUES(?, ?)";

        String trimmedQuery = query.trim().replaceFirst("^[^a-zA-Z0-9]+", "").replaceAll("[^a-zA-Z0-9]+$", "");

        int frequency = getFrequency(trimmedQuery);

        if (frequency == 0) {

            try {
                this.connect();
                // Create SQL statement for inserting
                stmt = this.connection.prepareStatement(insertSQL);
                stmt.setString(1, trimmedQuery);
                stmt.setInt(2, 1);
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            update(trimmedQuery, frequency);
        }
    }

    public void update(String query, int frequency) {
        PreparedStatement stmt;
        String updateSQL = "UPDATE QUERIES SET FREQUENCY = ? WHERE QUERY = ?";
        int newFrequency = frequency + 1;
        try {
            this.connect();
            // Create SQL statement for inserting
            stmt = this.connection.prepareStatement(updateSQL);
            stmt.setInt(1, newFrequency);
            stmt.setString(2, query);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Can't update frequency");
        }

        finally{
            this.close();
        }
    }

    public int getFrequency(String query) {
        String selectSQL = "SELECT * FROM QUERIES WHERE query=?";
        ResultSet rs;
        PreparedStatement stmt;
        int frequency = 0;

        try {
            this.connect();
            stmt = this.connection.prepareStatement(selectSQL);
            stmt.setString(1, query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                frequency = rs.getInt("FREQUENCY");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally{
            this.close();
        }

        return frequency;
    }

    public List<Query> list() throws SQLException {

        List<Query> listQuery = new ArrayList<>();

        try{

            String sql = "SELECT * FROM queries ORDER BY query";
            connect();

            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("queryid");
                String content = result.getString("query");
                int frequency = result.getInt("frequency");
                Query query = new Query(id, content, frequency);

                listQuery.add(query);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            close();
        }
        return listQuery;

    }


    public void extractCSV() {

        String selectSQL = "SELECT * FROM QUERIES ORDER BY QUERY";
        ResultSet rs;
        PreparedStatement stmt;

        try {
            this.connect();
            stmt = this.connection.prepareStatement(selectSQL);
            rs = stmt.executeQuery();
            // Open CSV file.
            CSVWriter writer = new CSVWriter(new FileWriter("../chatbot/files/data/queriesData.csv"));

            writer.writeAll(rs, true);

            writer.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();

        } finally{
            this.close();
        }

    }

    public static void main (String args[]) {
        QueryDatabaseAdmin qb = new QueryDatabaseAdmin();
        qb.extractCSV();
    }

}
