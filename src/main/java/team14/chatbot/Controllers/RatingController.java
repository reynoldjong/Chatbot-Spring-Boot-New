package team14.chatbot.Controllers;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import team14.chatbot.Repository.RatingRepository;
import team14.chatbot.Models.Rating;
import team14.chatbot.utils.CustomMappingStrategy;

import javax.servlet.http.HttpServletResponse;


@RestController // This means that this class is a Controller
public class RatingController {

    @Autowired // This means to get the bean called
    // Which is auto-gene           rated by Spring, we will use it to handle the data
    private RatingRepository ratingRepository;

    @PutMapping("/rating")
    Rating updateRating(@RequestParam String message, @RequestParam String answerRating) {

        return ratingRepository.findByMessage(message).map(rating -> {
            if (answerRating.equals("Good")) {
                rating.setGood(rating.getGood() + 1);
            } else if (answerRating.equals("Bad")) {
                rating.setBad(rating.getBad() + 1);
            }
            return ratingRepository.save(rating);
        }).orElseGet(() -> {
            Rating rating = new Rating();
            rating.setMessage(message);
            if (answerRating.equals("Good")) {
                rating.setGood(1);
            } else if (answerRating.equals("Bad")) {
                rating.setBad(1);
            }
            return ratingRepository.save(rating);
        });
    }

    @GetMapping("/rating")
    public Iterable<Rating> getAllRatings() {
        // This returns a JSON or XML
        return ratingRepository.findAll();
    }

    @GetMapping("/rating/exportCSV")
    public void exportRatingCSV(HttpServletResponse response) throws Exception {
        CustomMappingStrategy<Rating> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(Rating.class);

        //set file name and content type
        String filename = "ratingData.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<Rating> writer = new StatefulBeanToCsvBuilder<Rating>(response.getWriter())
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withMappingStrategy(mappingStrategy)
                .build();

        //write all users to csv file
        writer.write(ratingRepository.findAll(Sort.by(Sort.Direction.DESC, "good")));
    }

}