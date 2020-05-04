package team14.chatbot.Models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity // This tells Hibernate to make a table out of this class
public class ChatbotQuery {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "id")
    private Long id;
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "message")
    private String message;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "frequency")
    private int frequency;

    public ChatbotQuery() {}

    public ChatbotQuery(String message, int frequency) {
        this.message = message;
        this.frequency = frequency;
    }
}
