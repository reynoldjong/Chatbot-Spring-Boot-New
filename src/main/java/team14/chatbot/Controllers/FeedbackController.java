package team14.chatbot.Controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import team14.chatbot.Repository.FeedbackRepository;
import team14.chatbot.Models.Feedback;
import team14.chatbot.utils.CustomMappingStrategy;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController // This means that this class is a Controller
public class FeedbackController {

    @Autowired // This means to get the bean called
    // Which is auto-generated by Spring, we will use it to handle the data
    private FeedbackRepository feedbackRepository;

    @PostMapping("/feedback")
    public Feedback addFeedback (@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @GetMapping("/feedback")
    public Map<String, Object> getAllFeedback() {
        HashMap<String, Object> map = new HashMap<>();
        // This returns a JSON or XML
        map.put("feedback", feedbackRepository.findAll());
        map.put("average", feedbackRepository.getAverageRating());
        return map;
    }

    @GetMapping("/feedback/exportCSV")
    public void exportFeedbackCSV(HttpServletResponse response) throws Exception {

        CustomMappingStrategy<Feedback> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(Feedback.class);

        //set file name and content type
        String filename = "feedbackData.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<Feedback> writer = new StatefulBeanToCsvBuilder<Feedback>(response.getWriter())
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withMappingStrategy(mappingStrategy)
                .build();

        //write all users to csv file
        writer.write(feedbackRepository.findAll(Sort.by(Sort.Direction.DESC, "rating")));
    }

}