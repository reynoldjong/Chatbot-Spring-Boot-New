package team14.chatbot.LuceneEngine;


import java.io.*;
import java.util.ArrayList;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import com.google.gson.Gson;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * File parser used to standardize the format of a file to be uploaded.
 * 
 * @author Reynold
 */
public class FileParser {
  public String extractContent(String fileName, InputStream stream) {
    String content = "";
    try {
      Parser parser = new AutoDetectParser();
      ContentHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
      ParseContext context = new ParseContext();
      parser.parse(stream, handler, metadata, context);
      content =  handler.toString().trim();
      if (fileName.equals("Chatbot Corpus.docx")) {
        content = new Gson().toJson(parseCorpus(fileName, content));
      }
    } catch (IOException | TikaException | SAXException e) {
      e.printStackTrace();
    }
    return content.replaceAll("[\\n\\t\\v\\f\\r ]", " ").trim();
  }

  /**
   * Special case parser used to handle parsing of corpus document
   * 
   * @param fileName - name of file
   * @param content - input stream of file
   */
  public ArrayList<ArrayList<String>> parseCorpus(String fileName, String content) {
    ArrayList<ArrayList<String>> outerList = new ArrayList<>();
    String[] strippedContent = content.split("\\n[0-9]+[.]");
    int index = 0;
    for (String s : strippedContent) {
      String trimmedText = s.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
      ArrayList<String> innerList = new ArrayList<>();
      innerList.add(fileName + "(" + index + ")");
      innerList.add(trimmedText);
      outerList.add(innerList);
      index++;
    }
    return outerList;
  }

//
//  public String parsePdf(InputStream file) {
//    PDDocument pdDoc;
//    String trimmedText = "";
//    PDFTextStripper pdfStripper;
//
//    try {
//      pdDoc = PDDocument.load(file);
//      pdfStripper = new PDFTextStripper();
//      String parsedText = pdfStripper.getText(pdDoc).trim();
//      trimmedText = parsedText.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
//      pdDoc.close();
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    return trimmedText;
//
//  }
//
//  /**
//   * Used to handle parsing of docx file
//   *
//   * @param file - input stream of file
//   * @return string of standardized format with illegal characters removed
//   */
//  public String parseDocx(InputStream file) {
//
//    String trimmedText = "";
//
//    try {
//      XWPFDocument doc = new XWPFDocument(file);
//      XWPFWordExtractor extract = new XWPFWordExtractor(doc);
//      String parsedText = extract.getText().trim();
//      trimmedText = parsedText.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
//      file.close();
//    } catch (IOException e) {
//
//      e.printStackTrace();
//    }
//
//    return trimmedText;
//  }
//
//  /**
//   * Used to handle parsing of doc files
//   *
//   * @param file - input stream of file
//   * @return string of standardized format with illegal characters removed
//   */
//  public String parseDoc(InputStream file) {
//
//    String trimmedText = "";
//
//    try {
//      HWPFDocument doc = new HWPFDocument(file);
//      WordExtractor extractor = new WordExtractor(doc);
//      String parsedText = extractor.getText().trim();
//      trimmedText = parsedText.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
//      file.close();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    return trimmedText;
//  }
//
//  /**
//   * Used to handle parsing of html
//   *
//   * @param file - input stream of file
//   * @return string of standardized format with illegal characters removed
//   */
//  public String parseHtml(InputStream file) throws IOException {
//
//    String content = IOUtils.toString(file, StandardCharsets.UTF_8);
//    String text = "";
//    Document document = Jsoup.parse(content);
//    String title = document.title();
//    text = text + title;
//    Elements paragraphs = document.body().select("p");
//    for (Element paragraph : paragraphs) {
//      text = text + "\n" + paragraph.text();
//    }
//    return text;
//
//  }
//
//
//  /**
//   * A method for overall file parsing, which will call other parsing methods
//   *
//   * @param fileName - name of file
//   * @param file - input stream of file
//   */
//  public String parse(String fileName, InputStream file) throws IOException {
//    String docType = FilenameUtils.getExtension(fileName);
//    String text = "";
//    switch (docType) {
//      case "doc":
//        text = parseDoc(file);
//        break;
//      case "docx":
//        if (fileName.equals("Chatbot Corpus.docx")) {
//          Gson gson = new Gson();
//          text = parseDocx(file);
//          text = gson.toJson(parseCorpus(fileName, text));
//        } else {
//          text = parseDocx(file);
//        }
//        break;
//      case "pdf":
//        text = parsePdf(file);
//        break;
//      case "txt":
//        text = IOUtils.toString(file, StandardCharsets.UTF_8);
//        break;
//      case "html":
//        text = parseHtml(file);
//        break;
//    }
//    return text;
//
//  }
}

