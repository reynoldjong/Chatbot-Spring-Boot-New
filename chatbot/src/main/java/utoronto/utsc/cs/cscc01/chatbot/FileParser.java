package utoronto.utsc.cs.cscc01.chatbot;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FileParser {


    public String parsePdf(InputStream file) {
        PDDocument pdDoc;
        String parsedText = "";
        PDFTextStripper pdfStripper;

        try {
            pdDoc = PDDocument.load(file);
            pdfStripper = new PDFTextStripper();
            parsedText = pdfStripper.getText(pdDoc);
            pdDoc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedText;

    }


    public String parseDocx(InputStream file) {

        String parsedText = "";

        try {
            XWPFDocument doc = new XWPFDocument(file);
            XWPFWordExtractor extract = new XWPFWordExtractor(doc);
            parsedText = extract.getText();
            file.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return parsedText;
    }

    public String parseDoc(InputStream file) {

        String parsedText = "";

        try {
            HWPFDocument doc = new HWPFDocument(file);
            WordExtractor extractor = new WordExtractor(doc);
            parsedText = extractor.getText();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedText;
    }


    public String parse(String fileName, InputStream file) {
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
        }
        return text;

    }

    public static void main (String args[]) {
        FileParser fp = new FileParser();
        // String content = fp.parse(new File("../chatbot/files/Chatbot Corpus.docx"));
    }
}

