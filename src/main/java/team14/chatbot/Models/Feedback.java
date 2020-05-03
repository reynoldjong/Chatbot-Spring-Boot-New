package team14.chatbot.Models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Data
@Entity // This tells Hibernate to make a table out of this class
@EntityListeners(AuditingEntityListener.class)
public class Feedback {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "id")
    private Long id;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "rating")
    private int rating;
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "comments")
    private String comments;

    public Feedback() {}

    public Feedback(String comments, int rating) {
        this.comments = comments;
        this.rating = rating;
    }
}
