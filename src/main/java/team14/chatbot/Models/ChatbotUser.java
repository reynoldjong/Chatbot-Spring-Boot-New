package team14.chatbot.Models;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Data
public class ChatbotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String username;
    private String password;

    public ChatbotUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ChatbotUser() {
    }
}