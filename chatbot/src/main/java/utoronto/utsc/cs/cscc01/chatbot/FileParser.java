package utoronto.utsc.cs.cscc01.chatbot;


import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class FileParser {


    public String parsePdf(InputStream file) {
        PDDocument pdDoc;
        String trimmedText = "";
        PDFTextStripper pdfStripper;

        try {
            pdDoc = PDDocument.load(file);
            pdfStripper = new PDFTextStripper();
            String parsedText = pdfStripper.getText(pdDoc).trim();
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
            String parsedText = extract.getText().trim();
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
            String parsedText = extractor.getText().trim();
            trimmedText = parsedText.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trimmedText;
    }

    public String parseHtml(InputStream file) throws IOException {

        String content = IOUtils.toString(file, StandardCharsets.UTF_8);
        String text = "";
        Document document = Jsoup.parse(content);
        String title = document.title();
        text = text + title;
        Elements paragraphs = document.body().select("p");
        for (Element paragraph : paragraphs) {
            text = text + "\n" + paragraph.text();
        }
        return text;

    }

    public ArrayList<ArrayList<String>> parseCorpus(String fileName, InputStream file) {
        XWPFDocument doc;
        ArrayList<ArrayList<String>> outerList = new ArrayList<>();
        try {
            doc = new XWPFDocument(file);
            XWPFWordExtractor extract = new XWPFWordExtractor(doc);
            String parsedText = extract.getText().trim();
            String[] strippedContent = parsedText.split("\\n[0-9]+[.]");
            int index = 0;
            for (String s: strippedContent) {
                String trimmedText = s.replaceAll("[\\n\\t\\v\\f\\r ]", " ");
                ArrayList<String> innerList = new ArrayList<>();
                innerList.add(fileName + "(" + index + ")");
                innerList.add(trimmedText);
                outerList.add(innerList);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outerList;
    }


    public String parse(String fileName, InputStream file) throws IOException {
        String docType = FilenameUtils.getExtension(fileName);
        String text = "";
        switch (docType) {
            case "doc":
                text = parseDoc(file);
                break;
            case "docx":
                if (fileName.equals("Chatbot Corpus.docx")) {
                    Gson gson = new Gson();
                    text = gson.toJson(parseCorpus(fileName, file));
                } else {
                    text = parseDocx(file);
                }
                break;
            case "pdf":
                text = parsePdf(file);
                break;
            case "txt":
                text = IOUtils.toString(file, StandardCharsets.UTF_8);
                break;
            case "html":
                text = parseHtml(file);
                break;
        }
        return text;

    }

    public static void main (String args[]) {
        FileParser fp = new FileParser();
        try {
            File f = new File("../chatbot/files/Chatbot Corpus.docx");
            String content = fp.parse(f.getName(), new FileInputStream(f));
            Gson gson = new Gson();
            ArrayList<ArrayList<String>> obj = gson.fromJson(content, ArrayList.class);
            for (ArrayList<String> list: obj) {
                System.out.println(list.get(0));
                System.out.println(list.get(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

