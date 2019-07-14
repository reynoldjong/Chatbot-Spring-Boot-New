package utoronto.utsc.cs.cscc01.chatbot;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileParser {


    public void parsePdf(String docName) {
        File f = new File(docName);
        String parsedText;
        PDFParser parser = null;
        try {
            parser = new PDFParser(new RandomAccessFile(f, "r"));
            parser.parse();
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void parseDoc(String docName) {

        FileInputStream fis;

        if(docName.substring(docName.length() -1).equals("x")){
            try {
                fis = new FileInputStream(new File(docName));
                XWPFDocument doc = new XWPFDocument(fis);
                XWPFWordExtractor extract = new XWPFWordExtractor(doc);
                System.out.println(extract.getText());
            } catch (IOException e) {

                e.printStackTrace();
            }

        } else {
            try {
                fis = new FileInputStream(new File(docName));
                HWPFDocument doc = new HWPFDocument(fis);
                WordExtractor extractor = new WordExtractor(doc);
                System.out.println(extractor.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String parse(String docName) {
        switch (docType) {
            case ".doc":
                parseDoc(docName);
                break;
            case ".pdf":
                parsePdf(docName);
                break;
            case ".html":
                ;
                break;
        }

    }
}

