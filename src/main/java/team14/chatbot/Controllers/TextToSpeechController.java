package team14.chatbot.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team14.chatbot.IBMWatsonEngine.TextToSpeechEngine;
import team14.chatbot.IBMWatsonEngine.WatsonTextToSpeech;

import java.io.IOException;

@RestController
public class TextToSpeechController {

    private final TextToSpeechEngine ttsEngine = new TextToSpeechEngine(WatsonTextToSpeech.buildWatsonTTS());

    @PostMapping("/tts")
    public String convertTTS(@RequestParam String ttsText) throws IOException {
        byte[] buffer = ttsEngine.createAudioFromText(ttsText);
        return ttsEngine.toBase64(buffer);
    }
}
