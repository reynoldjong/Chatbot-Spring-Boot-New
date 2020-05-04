package team14.chatbot.Repository;


import org.springframework.data.repository.CrudRepository;
import team14.chatbot.Models.Link;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean
// CRUD refers Create, Read, Update, Delete

public interface LinksRepository extends CrudRepository<Link, Long> {
    void deleteBySeed(String seed);
    Optional<Link> findBySeed(String seed);
}
