package utoronto.utsc.cs.cscc01.chatbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;
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

  private QueryParser parser;
  private Query q;
  private String indexDirPath;
  
  // path for index is "../chatbot/index/documents"
  public LuceneQueryEngine(String indexDirPath) throws IOException {

    this.indexDirPath = indexDirPath;
     parser = new QueryParser("body", new StandardAnalyzer());
  }
  
  @Override
  public Hashtable<String, ArrayList<String>> simpleQuery(String s) throws IOException{

    Directory indexDirectory = FSDirectory.open(Paths.get(indexDirPath));
    IndexReader reader = DirectoryReader.open(indexDirectory);
    IndexSearcher searcher = new IndexSearcher(reader);

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
        String docBody = doc.get("body");
        Matcher m = Pattern.compile("(?<=\\w)\\b").matcher(docBody);
        for (int i = 0; i < 50 && m.find(); i++);
        if (! m.hitEnd())
          docBody = docBody.substring(0, m.end());
        String fileString = "{\"filename\":\"" + doc.get("title") + "\",\"passage\":\"" + docBody + "\"}";
        file.add(fileString);
      }
    }

    reader.close();
    return dict;
  }
  
  public static void main(String[] args) throws IOException, java.text.ParseException {
    String uploadPath = "../chatbot/files/Chatbot Corpus.docx";
    String fileName = "Chatbot Corpus.docx";
//    FileParser fp = new FileParser();
//    String content = fp.parse(fileName, new FileInputStream(new File(uploadPath)));
//    String url = "https://www.digitalfinanceinstitute.org/";
//    System.out.println(content);
    String filePath = "../chatbot/index/documents";
//    WebCrawler wc = new WebCrawler(2);
//    wc.crawl(url, 0, "?page_id", "?p");
//    Indexer indexer = new Indexer(filePath);
//    indexer.indexDoc(fileName, content);
//    indexer.indexUrl(wc.getLinks());
    LuceneQueryEngine qe = new LuceneQueryEngine(filePath);
    Hashtable<String, ArrayList<String>> searchResult = qe.simpleQuery("What funding opportunities?");
    System.out.println(searchResult);
//    File dirFile = new File(filePath);
//    FileUtils.cleanDirectory(dirFile);
  }

}
