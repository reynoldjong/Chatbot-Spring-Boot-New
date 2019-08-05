package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.codec.binary.Base64;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.util.WaveUtils;

public class TextToSpeechEngine {
  private WatsonTextToSpeech watsonTTS;

  public TextToSpeechEngine(WatsonTextToSpeech tts) {
    this.watsonTTS = tts;
  }

  public byte[] createAudioFromText(String text) throws IOException {
    SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
        .text(text).accept("audio/mp3").voice("en-US_MichaelVoice").build();

    InputStream inputStream =
        watsonTTS.getTTS().synthesize(synthesizeOptions).execute().getResult();
    InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

    // OutputStream out = new FileOutputStream("../chatbot/hello_world.mp3");
    // 4MB of ram for an audio, turns out audio file are pretty big
    byte[] buffer = new byte[4194304];
    in.read(buffer);
    /*
     * int length; length = in.read(buffer); out.write(buffer, 0, length);
     * 
     * out.close();
     */
    in.close();
    inputStream.close();
    return buffer;
  }

  public String toBase64(byte[] audioArray) {
    String base64String = Base64.encodeBase64String(audioArray);
    return base64String;
  }

  public static void main(String[] args) throws IOException {
    WatsonTextToSpeech watsonTTS = WatsonTextToSpeech.buildWatsonTTS();
    TextToSpeechEngine ttsEngine = new TextToSpeechEngine(watsonTTS);
    // ttsEngine.createAudioFromText("Hello World, How are you doing today? What
    // is DFI? What is context switch? How long can this go on for?");
  }
}
