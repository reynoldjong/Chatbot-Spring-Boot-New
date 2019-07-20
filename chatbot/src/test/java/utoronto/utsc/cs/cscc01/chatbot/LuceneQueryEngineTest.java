package utoronto.utsc.cs.cscc01.chatbot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LuceneQueryEngineTest {
  private IndexSearcher searcher;
  private QueryParser parser;
  private Query q;
  private LuceneQueryEngine qe;
  // a test index is already created at target location
  private static final String luceneQueryTestDir = "../chatbot/index/testLuceneQuery";
  
  @Before
  public void setUp() throws IOException {
     Directory indexDirectory = FSDirectory.open(Paths.get(luceneQueryTestDir));
     IndexReader reader = DirectoryReader.open(indexDirectory);
     searcher = new IndexSearcher(reader);
     parser = new QueryParser("body", new StandardAnalyzer());
     qe = new LuceneQueryEngine(luceneQueryTestDir);
  }
  
  @Test
  public void testUrlQuery() throws IOException {
    Hashtable<String, ArrayList<String>> searchResult = qe.simpleQuery("is it raining today?");
    assertTrue(searchResult.get("file").size() == 0);
    if (searchResult.get("url").size() == 0) {
      Assert.fail();
    }
    String resultString = searchResult.get("url").get(0);
    assertEquals(resultString, "www.myjournals.net");
  }
  
  @Test
  public void testUrlQuery2() throws IOException {
    Hashtable<String, ArrayList<String>> searchResult = qe.simpleQuery("how do I make Apache Lucene indexer?");
    assertTrue(searchResult.get("file").size() == 0);
    /*searchResult = qe.simpleQuery("how do I make Apache Lucene indexer?");
    System.out.println(searchResult);
    searchResult = qe.simpleQuery("tell me about Haskell");
    System.out.println(searchResult);*/
    if (searchResult.get("url").size() == 0) {
      Assert.fail();
    }
    String resultString = searchResult.get("url").get(0);
    assertEquals(resultString, "www.workschedule.com");
  }
  
  @Test
  public void testFileQuery() throws IOException {
    Hashtable<String, ArrayList<String>> searchResult = qe.simpleQuery("tell me about Haskell");
    assertTrue(searchResult.get("url").size() == 0);
    if (searchResult.get("file").size() == 0) {
      Assert.fail();
    }
    String resultString = searchResult.get("file").get(0);
    assertEquals(resultString, "{\"filename\":\"Learning Haskell\",\"passage\":\"Learning Haskell is as easy as 1, 2, lol you're dead\"}");
  }
}