package utoronto.utsc.cs.cscc01.chatbot;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDatabaseAdmin extends AbstractDatabaseAdmin {



    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param rating
     * @param comments
     */
    public void insertFeedback(int rating, String comments) throws SQLException {
        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO FEEDBACK(RATING, COMMENTS) VALUES(?, ?)";

        this.connect();
        // Create SQL statement for inserting
        stmt = this.connection.prepareStatement(insertSQL);
        stmt.setInt(1, rating);
        stmt.setString(2, comments);
        stmt.executeUpdate();

    }


    public float getAverage() throws SQLException {
        String averageSQL = "SELECT AVG(RATING) FROM FEEDBACK";
        ResultSet rs;
        PreparedStatement stmt;
        float average = 0;

        this.connect();
        stmt = this.connection.prepareStatement(averageSQL);
        rs = stmt.executeQuery();

        while (rs.next()) {
            average = rs.getFloat(1);
        }
        this.close();

    return average;
    }


    public void extractCSV() throws SQLException, IOException {

        String selectSQL = "SELECT * FROM FEEDBACK ORDER BY COMMENTS";
        ResultSet rs;
        PreparedStatement stmt;

        this.connect();
        stmt = this.connection.prepareStatement(selectSQL);
        rs = stmt.executeQuery();
        // Open CSV file.
        CSVWriter writer = new CSVWriter(new FileWriter("../chatbot/files/data/feedbackData.csv"));

        writer.writeAll(rs, true);

        writer.close();

        this.close();

    }

    public List<Feedback> list() throws SQLException {

        List<Feedback> listFeedback = new ArrayList<>();


        String sql = "SELECT * FROM feedback ORDER BY rating";
        connect();

        Statement statement = this.connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            int id = result.getInt("feedbackId");
            int rating = result.getInt("rating");
            String comments = result.getString("comments");
            Feedback feedback = new Feedback(id, rating, comments);

            listFeedback.add(feedback);
            close();
        }

        return listFeedback;
    }

    public static void main (String args[]) {
        FeedbackDatabaseAdmin fb = new FeedbackDatabaseAdmin();
        try {
            fb.extractCSV();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
