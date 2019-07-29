package utoronto.utsc.cs.cscc01.chatbot;

import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static junit.framework.TestCase.assertEquals;

public class FileParserTest {

    FileParser fp;

    @Before
    public void setUp() {

        fp = new FileParser();
    }

    @Test
    public void testParsingPdf() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("../chatbot/files/test/test.pdf"));
        String content = fp.parsePdf(is);
        assertEquals(content, "test \n");
    }

    @Test
    public void testParsingDoc() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("../chatbot/files/test/test.doc"));
        String content = fp.parseDoc(is);
        assertEquals(content, "test\n\n");
    }

    @Test
    public void testParsingDocx() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("../chatbot/files/test/test.docx"));
        String content = fp.parseDocx(is);
        assertEquals(content, "test\n");
    }
}
