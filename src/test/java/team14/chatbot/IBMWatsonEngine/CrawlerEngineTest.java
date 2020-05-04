package team14.chatbot.IBMWatsonEngine;

import org.junit.Before;
import org.junit.Test;
import team14.chatbot.IBMWatsonEngine.CrawlerEngine;
import team14.chatbot.IBMWatsonEngine.WatsonDiscovery;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CrawlerEngineTest {

    private CrawlerEngine ce;
    private String configId;

    @Before
    public void setUp() {
        WatsonDiscovery wd = WatsonDiscovery.buildDiscovery();
        ce = new CrawlerEngine(wd);
        configId = wd.getCrawlerConfigurationId();
    }

    @Test
    public void testUploadAndUpdateAndRemove() {
        String url = "www.justForTestPurpose.com";
        String id = ce.addUrl(url);
        assertNotNull(id);
        assertEquals(id, configId);
        String id2 = ce.removeUrl(url);
        assertNotNull(id2);
        assertEquals(id2, configId);

    }
}
