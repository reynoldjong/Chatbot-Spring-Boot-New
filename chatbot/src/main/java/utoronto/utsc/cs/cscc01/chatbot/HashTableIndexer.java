package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class HashTableIndexer {

  private IndexWriter writer;

  public HashTableIndexer(String indexDirectoryPath) throws IOException {
    Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
    StandardAnalyzer analyzer = new StandardAnalyzer();
    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
    writer = new IndexWriter(indexDirectory, iwc);
  }

  public void close() throws CorruptIndexException, IOException {
    writer.close();
  }

  private Document buildUrlDoc(String titleKey, String headerKey, String body) {
    Document document = new Document();

    StringField titleField = new StringField("title", titleKey, Field.Store.YES);
    StringField headerField = new StringField("header", headerKey, Field.Store.YES);
    StringField bodyField = new StringField("body", body, Field.Store.YES);

    document.add(titleField);
    document.add(headerField);
    document.add(bodyField);

    return document;
  }
  
  private Document buildTextDoc(String title, String body) {
    Document document = new Document();
    
    StringField titleField = new StringField("title", title, Field.Store.YES);
    StringField bodyField = new StringField("body", body, Field.Store.YES);

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
  }
  
  /*
   * For documents, we are only given the title of the document and body of
   * the document
   */
  public void indexDoc(String title, String body) throws IOException {
    Document doc = buildTextDoc(title, body);
    writer.addDocument(doc);
  }
}
