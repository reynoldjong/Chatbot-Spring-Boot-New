package utoronto.utsc.cs.cscc01.chatbot;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class FeedbackDatabaseAdmin extends AbstractDatabaseAdmin {



    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param rating
     * @param comments
     */
    public void insertFeedback(int rating, String comments) {
        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO FEEDBACK(RATING, COMMENTS) VALUES(?, ?)";
        try {
            this.connect();
            // Create SQL statement for inserting
            stmt = this.connection.prepareStatement(insertSQL);
            stmt.setInt(1, rating);
            stmt.setString(2, comments);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public float getAverage() {
        String averageSQL = "SELECT AVG(RATING) FROM FEEDBACK";
        ResultSet rs;
        PreparedStatement stmt;
        float average = 0;

        try {
            this.connect();
            stmt = this.connection.prepareStatement(averageSQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                average = rs.getFloat(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally{
            this.close();
        }

        return average;
    }

    public void extractCSV() {

        String selectSQL = "SELECT * FROM FEEDBACK ORDER BY COMMENTS";
        ResultSet rs;
        PreparedStatement stmt;

        try {
            this.connect();
            stmt = this.connection.prepareStatement(selectSQL);
            rs = stmt.executeQuery();
            // Open CSV file.
            CSVWriter writer = new CSVWriter(new FileWriter("../chatbot/files/feedbackData.csv"));

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
        FeedbackDatabaseAdmin fb = new FeedbackDatabaseAdmin();
        fb.extractCSV();
    }

}
