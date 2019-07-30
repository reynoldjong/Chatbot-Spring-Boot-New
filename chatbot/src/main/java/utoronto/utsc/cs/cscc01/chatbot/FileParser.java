package utoronto.utsc.cs.cscc01.chatbot;


import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class FileParser {


    public String parsePdf(InputStream file) {
        PDDocument pdDoc;
        String trimmedText = "";
        PDFTextStripper pdfStripper;

        try {
            pdDoc = PDDocument.load(file);
            pdfStripper = new PDFTextStripper();
            String parsedText = pdfStripper.getText(pdDoc);
            trimmedText = parsedText.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
            pdDoc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return trimmedText;

    }


    public String parseDocx(InputStream file) {

        String trimmedText = "";

        try {
            XWPFDocument doc = new XWPFDocument(file);
            XWPFWordExtractor extract = new XWPFWordExtractor(doc);
            String parsedText = extract.getText();
            trimmedText = parsedText.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
            file.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return trimmedText;
    }

    public String parseDoc(InputStream file) {

        String trimmedText = "";

        try {
            HWPFDocument doc = new HWPFDocument(file);
            WordExtractor extractor = new WordExtractor(doc);
            String parsedText = extractor.getText();
            trimmedText = parsedText.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trimmedText;
    }


    public String parse(String fileName, InputStream file) throws IOException {
        String docType = FilenameUtils.getExtension(fileName);
        String text = "";
        switch (docType) {
            case "doc":
                text = parseDoc(file);
                break;
            case "docx":
                text = parseDocx(file);
                break;
            case "pdf":
                text = parsePdf(file);
                break;
            case "txt":
                text = IOUtils.toString(file, StandardCharsets.UTF_8);
                break;
            case "html":
                break;
        }
        return text;

    }

    public static void main (String args[]) {
        FileParser fp = new FileParser();
        // String content = fp.parse(new File("../chatbot/files/Chatbot Corpus.docx"));
    }
}

