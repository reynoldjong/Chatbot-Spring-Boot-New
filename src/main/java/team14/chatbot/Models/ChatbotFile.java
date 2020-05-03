package team14.chatbot.Models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
@Entity // This tells Hibernate to make a table out of this class
public class ChatbotFile {

    @Id
    private String id;
    @Column(unique=true)
    private String fileName;
    @Lob
    private byte[] data;

    private String date;

    public ChatbotFile() {}

    public ChatbotFile(String id, String fileName, byte[] data) {
        this.id = id;
        this.fileName = fileName;
        this.data = data;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date(System.currentTimeMillis()));
    }

}
