package team14.chatbot.Models;

import lombok.Data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
@Entity // This tells Hibernate to make a table out of this class
@EntityListeners(AuditingEntityListener.class)
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String seed;
    private int depth;
    @Lob
    private byte[] collection;
    private String date;

    public Link() {}

    public Link(String seed, int depth, byte[] collection) {
        this.seed = seed;
        this.depth = depth;
        this.collection = collection;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date(System.currentTimeMillis()));
    }


}
