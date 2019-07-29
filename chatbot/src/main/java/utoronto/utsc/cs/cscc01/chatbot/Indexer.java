package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

    private String indexDirPath;
    private Directory indexDirectory;
  
  // need to specify where we are writing to
  // filePath = "../chatbot/index/" for testing
  public Indexer(String indexDirPath) throws IOException {
      this.indexDirPath = indexDirPath;
      this.indexDirectory = FSDirectory.open(Paths.get(indexDirPath));
  }


  private Document buildUrlDoc(String titleKey, String headerKey, String body) {
    Document document = new Document();

    StringField typeField = new StringField("type", "url", Field.Store.YES);
    StringField titleField = new StringField("title", titleKey, Field.Store.YES);
    StringField headerField = new StringField("header", headerKey, Field.Store.YES);
    TextField bodyField = new TextField("body", body, Field.Store.YES);

    document.add(typeField);
    document.add(titleField);
    document.add(headerField);
    document.add(bodyField);

    return document;
  }
  
  private Document buildTextDoc(String title, String body) {
    Document document = new Document();
    
    StringField typeField = new StringField("type", "file", Field.Store.YES);
    StringField titleField = new StringField("title", title, Field.Store.YES);
    TextField bodyField = new TextField("body", body, Field.Store.YES);

    document.add(typeField);
    document.add(titleField);
    document.add(bodyField);
    
    return document;
  }

  /*
   * We are given the following format for any URL from crawler:
   * HashMap<String documentTitle, HashMap<String documentHeader, String body>>
   * So we will index for each title -> for each header -> body
   */
  public void indexUrl(HashMap<String, HashMap<String, String>> titleHash) throws IOException {
    StandardAnalyzer analyzer = new StandardAnalyzer();
    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
    IndexWriter writer = new IndexWriter(indexDirectory, iwc);
    // for title in hashmap
    for (String titleKey : titleHash.keySet()) {
      HashMap<String, String> headerHash = titleHash.get(titleKey);
      // for header in hashmap
      for (String headerKey : headerHash.keySet()) {
        String body = headerHash.get(headerKey);
        // build the document we are hashing
        Document doc = buildUrlDoc(titleKey, headerKey, body);
        // write to index
        writer.addDocument(doc);
      }
    }
    writer.close();
  }
  
  /*
   * For documents, we are only given the title of the document and body of
   * the document
   */
  public void indexDoc(String title, String body) throws IOException {
    StandardAnalyzer analyzer = new StandardAnalyzer();
    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
    IndexWriter writer = new IndexWriter(indexDirectory, iwc);
    Document doc = buildTextDoc(title, body);
    writer.addDocument(doc);
    writer.close();
  }

  public void removeIndex() throws IOException {
      File dirFile = new File(this.indexDirPath);
      FileUtils.cleanDirectory(dirFile);
  }
  
  // this was run once before indexer ran so we get an empty index
  public static void main(String[] args) {
    HashMap<String, HashMap<String, String>> testUrlHash = new HashMap<>();
    HashMap<String, String> testJournalHash = new HashMap<>();
    //HashMap<String, String> testHeaderHash = new HashMap<>();
    
/*    testJournalHash.put("Journal entry 1", "This is a great morning to exercise!");
    testJournalHash.put("Journal entry 2", "The weather today is kind of poop.");
    testJournalHash.put("Journal entry 3", "Wow, it is still raining today.");
    testJournalHash.put("Journal entry 4", "The rain finally stopped, but it is still cloudy");
    testJournalHash.put("Journal entry 5", "The sun is finally back! All praise the sun!");*/
    testJournalHash.put("empty url", "empty url");
    testUrlHash.put("empty url", testJournalHash);
    
/*    testHeaderHash.put("Work schedule day 1", "Write unit test for my indexer");
    testHeaderHash.put("Work schedule day 2", "Research on Apache Lucene");
    testHeaderHash.put("Work schedule day 3", "Start writing my indexer");
    testHeaderHash.put("Work schedule day 4", "Continuing to write my indexer");
    testHeaderHash.put("Work schedule day 5", "Finally done with indexer!");
    
    testUrlHash.put("www.workschedule.com", testHeaderHash);
*/    
    String filePath = "../chatbot/index/";
    
    try {
      Indexer myIndexer = new Indexer(filePath);
      myIndexer.indexUrl(testUrlHash);
      myIndexer.indexDoc("empty doc", "empty doc");
      //myIndexer.indexDoc("The Life of Pie", "Something something tiger, boat, stranded.");
      //myIndexer.indexDoc("Operating Systems", "Context switch is the process where the CPU is passed between processes...");
      //myIndexer.indexDoc("Learning Haskell", "Learning Haskell is as easy as 1, 2, lol you're dead");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("error pathing to index");
      e.printStackTrace();
    }
  }
  
}
