package team14.chatbot;


import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team14.chatbot.Models.ChatbotUser;
import team14.chatbot.Repository.ChatbotUserRepository;
import team14.chatbot.Repository.FeedbackRepository;
import team14.chatbot.Repository.QueryRepository;
import team14.chatbot.Repository.RatingRepository;


@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(FeedbackRepository feedbackRepository, RatingRepository ratingRepository, ChatbotUserRepository userRepository, QueryRepository queryRepository) {
        return args -> {
            log.info("Preloading " + userRepository.save(new ChatbotUser("admin", "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG")));
        };
    }
}