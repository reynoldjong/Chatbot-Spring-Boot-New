package team14.chatbot.IBMWatsonEngine;

import org.junit.Before;
import org.junit.Test;
import team14.chatbot.IBMWatsonEngine.FilesEngine;
import team14.chatbot.IBMWatsonEngine.WatsonDiscovery;

import java.io.*;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class FileEngineTest {

    private FilesEngine fe;

    @Before
    public void setUp() {
        WatsonDiscovery wd = WatsonDiscovery.buildDiscovery();
        fe = new FilesEngine(wd);
    }

    @Test
    public void testUploadAndUpdateAndRemove() throws IOException {
        File file = new File("src/main/resources/files/test/test.pdf");
        InputStream ip = new FileInputStream(file);
        String id = fe.uploadFiles(ip, file.getName());
        assertNotNull(id);
        assertThat(id, instanceOf(String.class));
        ip.close();
        // Same filename for update
        File file2 = new File("src/main/resources/files/test/test.pdf");
        InputStream ip2 = new FileInputStream(file2);
        String newId = fe.updateFiles(ip2, file2.getName(), id);
        assertNotNull(newId);
        assertEquals(id, newId);
        ip2.close();
        String response = fe.removeFiles(newId);
        assertNotNull(response);
        assertEquals(response, "deleted");
    }

}
