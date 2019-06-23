package utoronto.utsc.cs.cscc01.chatbot;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;


public class FilesDatabaseTest {

    FilesDatabase db;

    @Before
    public void setUp() {
        db = new FilesDatabase();
        db.connect();
    }


    /**
     * Test if the file is successfully uploaded by checking the content of file. Content of test.txt is "testing"
     */
    @Test
    public void testUpload() {
        db.uploadFiles("../chatbot/files");
        assertEquals("testing", db.query("test"));
    }

    /**
     * Test if the file is successfully removed by checking the content of file. Query should return an empty string.
     */
    @Test
    public void testRemove() {
        db.remove("test");
        assertEquals("", db.query("test"));
    }
}
