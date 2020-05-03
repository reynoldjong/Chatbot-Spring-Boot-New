package team14.chatbot.Models;

import lombok.Data;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;

@Data
@Entity // This tells Hibernate to make a table out of this class
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "id")
    private Long id;
    @Column(unique=true)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "answer")
    private String message;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "good")
    private int good;
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "bad")
    private int bad;

    public Rating() {}

    public Rating(String message, int good, int bad) {
        this.message = message;
        this.good = good;
        this.bad = bad;
    }
}