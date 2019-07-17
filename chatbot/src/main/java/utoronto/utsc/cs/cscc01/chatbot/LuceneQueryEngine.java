package utoronto.utsc.cs.cscc01.chatbot;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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

public class LuceneQueryEngine implements SearchEngine {
  private IndexSearcher searcher;
  private QueryParser parser;
  private Query q;
  
  // path for testing is "../chatbot/testindex"
  public LuceneQueryEngine(String indexDirPath) throws IOException {
     Directory indexDirectory = FSDirectory.open(Paths.get(indexDirPath));
     IndexReader reader = DirectoryReader.open(indexDirectory);
     searcher = new IndexSearcher(reader);
     parser = new QueryParser("body", new StandardAnalyzer());
  }
  
  @Override
  public Hashtable<String, ArrayList<String>> simpleQuery(String s) throws IOException{
    Hashtable<String, ArrayList<String>> dict = new Hashtable<>();
    
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> file = new ArrayList<>();
    
    dict.put("url", url);
    dict.put("file", file);
    
    try {
      q = parser.parse(s);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    // we only care about the top hit or else we are printing too much on chatbot
    TopDocs hits = searcher.search(q, 1);
    if (hits.scoreDocs.length > 0) {
      ScoreDoc scoreDoc = hits.scoreDocs[0];
      Document doc = searcher.doc(scoreDoc.doc);
      // get type back from doc, can be url or file
      String filetype = doc.get("type");
      
      // if we have a url, add to the url list
      if (filetype.equals("url")) {
        String link = doc.get("title");
        url.add(link);
      }
      
      // if we have a file, add to the file list
      else if (filetype.equals("file")) {
        String fileString = "{\"filename\":\"" + doc.get("title") + "\",\"passage\":\"" + doc.get("body") + "\"}";
        file.add(fileString);
      }
      
    }
    return dict;
  }
  
  public static void main(String[] args) throws IOException {
    String filePath = "../chatbot/testindex";
    LuceneQueryEngine qe = new LuceneQueryEngine(filePath);
    Hashtable<String, ArrayList<String>> searchResult = qe.simpleQuery("what is the weather?");
    System.out.println(searchResult);
    searchResult = qe.simpleQuery("indexer");
    System.out.println(searchResult);
    searchResult = qe.simpleQuery("Haskell");
    System.out.println(searchResult);
  }

}
