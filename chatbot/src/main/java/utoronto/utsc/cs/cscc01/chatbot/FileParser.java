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


public class FileParser {


    public String parsePdf(String docName) {
        PDDocument pdDoc;
        String parsedText = "";
        PDFTextStripper pdfStripper;

        try {
            pdDoc = PDDocument.load(new File(docName));
            pdfStripper = new PDFTextStripper();
            parsedText = pdfStripper.getText(pdDoc);
//            PrintWriter pw = new PrintWriter("src/output/pdf.txt");
//            pw.print(parsedText);
//            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedText;

    }


    public String parseDocx(String docName) {

        FileInputStream fis;
        String parsedText = "";

        try {
            fis = new FileInputStream(new File(docName));
            XWPFDocument doc = new XWPFDocument(fis);
            XWPFWordExtractor extract = new XWPFWordExtractor(doc);
            parsedText = extract.getText();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return parsedText;
    }

    public String parseDoc(String docName) {

        FileInputStream fis;
        String parsedText = "";

        try {
            fis = new FileInputStream(new File(docName));
            HWPFDocument doc = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(doc);
            parsedText = extractor.getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedText;
    }


    public String parse(String docName) {
        String docType = FilenameUtils.getExtension(docName);
        System.out.println(docType);
        String text = "";
        switch (docType) {
            case "doc":
                text = parseDoc(docName);
                break;
            case "docx":
                text = parseDocx(docName);
                break;
            case "pdf":
                text = parsePdf(docName);
                break;
            case "html":
                text = "";
                break;
        }
        return text;

    }

    public static void main (String args[]) {
        FileParser fp = new FileParser();
        System.out.println(fp.parse("../chatbot/files/Design.pdf"));


    }
}

