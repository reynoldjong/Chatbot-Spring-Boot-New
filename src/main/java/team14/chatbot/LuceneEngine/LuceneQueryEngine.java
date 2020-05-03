package team14.chatbot.LuceneEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

/**
 * Contain methods uses Apache Lucene to query its index
 * @author Chris
 *
 */
public class LuceneQueryEngine {

  private QueryParser parser;
  private Query q;
  private String indexDirPath;

  // path for index is ".src/main/resources/index/documents"
  public LuceneQueryEngine(String indexDirPath) {

    this.indexDirPath = indexDirPath;
    parser = new QueryParser("body", new StandardAnalyzer());
  }

  /**
   * Main method used to query the Lucene index and returns a hashtable with the
   * key strings representing the type of file being returned and the list of 
   * strings representing the content of the file
   * @param s - string containing user query
   * @return hashtable of specified format required by queryEngine
   */
  public Hashtable<String, ArrayList<Object>> simpleQuery(String s) {

    Hashtable<String, ArrayList<Object>> dict = new Hashtable<>();

    ArrayList<Object> url = new ArrayList<>();
    ArrayList<Object> file = new ArrayList<>();

    dict.put("url", url);
    dict.put("file", file);
    InputStream inputStream = this.getClass().getResourceAsStream("indexDirPath");

//    File fileDirectory = new File(indexDirPath);
    try {
    if (Files.list(Paths.get(indexDirPath)).findAny().isPresent()) {
      Directory indexDirectory = FSDirectory.open(Paths.get(this.indexDirPath));
      IndexReader reader = DirectoryReader.open(indexDirectory);
      IndexSearcher searcher = new IndexSearcher(reader);

      try {
        q = parser.parse(s);
      } catch (ParseException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
      // we only care about the top hit or else we are printing too much on
      // chatbot
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
          String docBody = doc.get("body");
          Matcher m = Pattern.compile("(?<=\\w)\\b").matcher(docBody);
          for (int i = 0; i < 50 && m.find(); i++) ;
          if (!m.hitEnd()) docBody = docBody.substring(0, m.end());
          Hashtable<String, String> fileDetails = new Hashtable<>();
          fileDetails.put("filename", doc.get("title"));
          fileDetails.put("passage", docBody);
          file.add(fileDetails);
        }

      }

      reader.close();
    }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dict;
  }

}
