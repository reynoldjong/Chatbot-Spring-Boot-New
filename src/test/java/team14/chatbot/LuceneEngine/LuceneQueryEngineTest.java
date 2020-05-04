package team14.chatbot.LuceneEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team14.chatbot.LuceneEngine.LuceneQueryEngine;

public class LuceneQueryEngineTest {
  private LuceneQueryEngine qe;
  // a test index is already created at target location
  private static final String luceneQueryTestDir =
      "src/main/resources/index/testLuceneQuery";

  @Before
  public void setUp() throws IOException {
    qe = new LuceneQueryEngine(luceneQueryTestDir);
  }

  @Test
  public void testUrlQuery() throws IOException {
    Hashtable<String, ArrayList<Object>> searchResult =
        qe.simpleQuery("is it raining today?");
    assertEquals(searchResult.get("file").size(), 0);
    if (searchResult.get("url").size() == 0) {
      Assert.fail();
    }
    String resultString = (String) searchResult.get("url").get(0);
    assertEquals(resultString, "www.myjournals.net");
  }

  @Test
  public void testUrlQuery2() throws IOException {
    Hashtable<String, ArrayList<Object>> searchResult =
        qe.simpleQuery("how do I make Apache Lucene indexer?");
    assertEquals(searchResult.get("file").size(), 0);
    /*
     * searchResult = qe.simpleQuery("how do I make Apache Lucene indexer?");
     * System.out.println(searchResult); searchResult =
     * qe.simpleQuery("tell me about Haskell");
     * System.out.println(searchResult);
     */
    if (searchResult.get("url").size() == 0) {
      Assert.fail();
    }
    String resultString = (String) searchResult.get("url").get(0);
    assertEquals(resultString, "www.workschedule.com");
  }

  @Test
  public void testFileQuery() throws IOException {
    Hashtable<String, ArrayList<Object>> searchResult =
        qe.simpleQuery("tell me about Haskell");
    assertEquals(searchResult.get("url").size(), 0);
    if (searchResult.get("file").size() == 0) {
      Assert.fail();
    }
    Hashtable<String, String> resultString = (Hashtable<String, String>) searchResult.get("file").get(0);
    assertEquals(resultString.get("filename"), "Learning Haskell");
    assertEquals(resultString.get("passage"), "Learning Haskell is as easy as 1, 2, lol you're dead");
  }
}
