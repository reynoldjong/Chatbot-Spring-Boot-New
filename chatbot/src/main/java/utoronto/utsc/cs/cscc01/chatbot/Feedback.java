package utoronto.utsc.cs.cscc01.chatbot;


/**
 * A class that stores all the information associated with feedback given by user. It will be put into a list and sent to
 * the frontend for information displaying
 *
 * @author Reynold
 */
public class Feedback {

  private int id;
  private int rating;
  private String comments;

  public Feedback(int id, int rating, String comments) {
    this.id = id;
    this.rating = rating;
    this.comments = comments;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public int getId() {
    return this.id;
  }

  public int getRating() {
    return this.rating;
  }

  public String getComments() {
    return this.comments;
  }
}
