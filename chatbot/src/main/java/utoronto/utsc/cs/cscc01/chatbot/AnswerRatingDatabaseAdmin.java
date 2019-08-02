package utoronto.utsc.cs.cscc01.chatbot;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerRatingDatabaseAdmin extends AbstractDatabaseAdmin {

    /**
     * Insert the given information to the database, filename and the content of files
     *
     * @param answer
     * @param rating
     */
    public void insert(String answer, String rating) throws SQLException {
        PreparedStatement stmt;
        // SQL code for insert
        String insertSQL = "INSERT INTO ANSWERRATING(ANSWER, GOOD, BAD) VALUES(?, ?, ?)";

        int numberOfRatings = getNumberOfRatings(answer, rating);

        if (numberOfRatings == -1) {

            try {
                this.connect();
                // Create SQL statement for inserting
                stmt = this.connection.prepareStatement(insertSQL);
                stmt.setString(1, answer);
                if (rating.equals("Good")) {
                    stmt.setInt(2, 1);
                    stmt.setInt(3, 0);
                } else if (rating.equals("Bad")) {
                    stmt.setInt(2, 0);
                    stmt.setInt(3, 1);
                }
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            update(answer, rating, numberOfRatings);
        }
    }

    public void update(String answer, String rating, int numberOfRatings) {
        PreparedStatement stmt;
        String updateSQL = "UPDATE ANSWERRATING SET " + rating + " = ? WHERE ANSWER = ?";
        int newNumberOfRatings = numberOfRatings + 1;
        try {
            this.connect();
            // Create SQL statement for inserting
            stmt = this.connection.prepareStatement(updateSQL);
            stmt.setInt(1, newNumberOfRatings);
            stmt.setString(2, answer);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Can't update ratings");
        }

        finally{
            this.close();
        }
    }

    public int getNumberOfRatings(String answer, String rating) {
        String selectSQL = "SELECT " + rating + " FROM ANSWERRATING WHERE answer=?";
        ResultSet rs;
        PreparedStatement stmt;
        int numberOfRatings = -1;

        try {
            this.connect();
            stmt = this.connection.prepareStatement(selectSQL);
            stmt.setString(1, answer);
            rs = stmt.executeQuery();

            while (rs.next()) {
                numberOfRatings = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally{
            this.close();
        }

        return numberOfRatings;
    }


    public void extractCSV() throws SQLException, IOException {

        String selectSQL = "SELECT * FROM ANSWERRATING ORDER BY BAD DESCENDING";
        ResultSet rs;
        PreparedStatement stmt;

        this.connect();
        stmt = this.connection.prepareStatement(selectSQL);
        rs = stmt.executeQuery();
        // Open CSV file.
        CSVWriter writer = new CSVWriter(new FileWriter("../chatbot/files/data/answerRatingsData.csv"));

        writer.writeAll(rs, true);

        writer.close();

        this.close();

    }

//    public List<Feedback> list() throws SQLException {
//
//        List<Feedback> listFeedback = new ArrayList<>();
//
//
//        String sql = "SELECT * FROM feedback ORDER BY rating";
//        connect();
//
//        Statement statement = this.connection.createStatement();
//        ResultSet result = statement.executeQuery(sql);
//
//        while (result.next()) {
//            int id = result.getInt("feedbackId");
//            int rating = result.getInt("rating");
//            String comments = result.getString("comments");
//            Feedback feedback = new Feedback(id, rating, comments);
//
//            listFeedback.add(feedback);
//            close();
//        }
//
//        return listFeedback;
//    }
}
