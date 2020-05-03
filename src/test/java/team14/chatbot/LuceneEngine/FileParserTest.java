package team14.chatbot.LuceneEngine;

import org.junit.Before;
import org.junit.Test;
import team14.chatbot.LuceneEngine.FileParser;


import java.io.*;

import static junit.framework.TestCase.assertEquals;

public class FileParserTest {

    FileParser fp;

    @Before
    public void setUp() {

        fp = new FileParser();
    }

    @Test
    public void testParsingPdf() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("src/main/resources/files/test/test.pdf"));
        String content = fp.extractContent("test.pdf", is);
        assertEquals(content, "test");
    }

    @Test
    public void testParsingDoc() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("src/main/resources/files/test/test.doc"));
        String content = fp.extractContent("test.doc", is);
        assertEquals(content, "test");
    }

    @Test
    public void testParsingDocx() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("src/main/resources/files/test/test.docx"));
        String content = fp.extractContent("test.docx", is);
        assertEquals(content, "test");
    }

    @Test
    public void testParsingHtml() throws IOException {
        InputStream is = new FileInputStream(new File("src/main/resources/files/test/test.html"));
        String content = fp.extractContent("test.html", is);
        assertEquals(content, "test");
    }
}
