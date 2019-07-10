package utoronto.utsc.cs.cscc01.chatbot;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.Collection;
import com.ibm.watson.discovery.v1.model.FederatedQueryOptions;
import com.ibm.watson.discovery.v1.model.GetCollectionOptions;
import com.ibm.watson.discovery.v1.model.ListCollectionFieldsResponse;
import com.ibm.watson.discovery.v1.model.ListFieldsOptions;
import com.ibm.watson.discovery.v1.model.QueryNoticesResponse;
import com.ibm.watson.discovery.v1.model.QueryOptions;
import com.ibm.watson.discovery.v1.model.QueryPassages;
import com.ibm.watson.discovery.v1.model.QueryResponse;
import com.ibm.watson.discovery.v1.model.QueryResult;

public class QueryEngine implements SearchEngine {
  
  private WatsonDiscovery wdisc;
  
  public QueryEngine(WatsonDiscovery wdisc) {
    this.wdisc = wdisc;
  }
  
  public Hashtable<String, ArrayList<String>> simpleQuery(String q) {
    Discovery discovery = wdisc.getDiscovery();
    
    String environmentId = wdisc.getEnvironmentId();
    String crawlerCollectionId = wdisc.getCrawlerCollectionId();
    String uploadedFilesCollectionId = wdisc.getUploadedFilesCollectionId();
    // query on IBM watson discovery
    FederatedQueryOptions.Builder queryBuilder = new FederatedQueryOptions.Builder(environmentId);
    queryBuilder.naturalLanguageQuery(q);
    queryBuilder.collectionIds(crawlerCollectionId + "," + uploadedFilesCollectionId);
    queryBuilder.passages(true);
    queryBuilder.returnFields("metadata.source.url,extracted_metadata.filename");
    QueryResponse queryResponse = discovery.federatedQuery(queryBuilder.build()).execute().getResult();

    List<QueryResult> resultList = queryResponse.getResults();
    String jsonString = "{";
    // we have 4 kinds of expected return from the query
    // text, url, image, files
    Hashtable<String, ArrayList<String>> dict = new Hashtable<>();
    
    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> file = new ArrayList<>();
    
    dict.put("text", text);
    dict.put("image", image);
    dict.put("url", url);
    dict.put("file", file);
    // now we have {"text":[], "image":[], "url":[], "file":[]} this will help us build our json string
    
    String passageText = "";
    List<QueryPassages> passageList = queryResponse.getPassages();
    // grab most relevant passage from the text
    if (passageList.size() > 0)
      passageText = passageList.get(0).getPassageText();
    
    // first check to see if we have any results
    if (resultList.size() > 0) {
      QueryResult firstResult = resultList.get(0);
      // if this was from an uploaded file
      if (firstResult.getCollectionId().equals(wdisc.getUploadedFilesCollectionId())) {
        Map<String, Object> map = firstResult.getProperties();
        Map<String, String> fileMap = (Map<String, String>) map.get("extracted_metadata");
        String filename = fileMap.get("filename");
        String fileString = "{\"filename\":\"" + filename + "\",\"passage\":\"" + passageText + "\"}";
        file.add(fileString);
      }
      // if this was from crawler indexing the webpage
      else if (firstResult.getCollectionId().equals(wdisc.getCrawlerCollectionId())) {
        Map<String, Map<String, String>> map = firstResult.getMetadata();
        String link = map.get("source").get("url");
        // the "s from html is crashing my json, not sure how discovery does it
        //String urlString = "{\"link\":\"" + link + "\",\"passage\":\"" + passageText + "\"}";
        url.add(link);
      }
    }
    
    return dict;
  }

  public static void main(String[] args) {
    WatsonDiscovery watsonDiscovery = WatsonDiscovery.buildDiscovery();
    QueryEngine qe = new QueryEngine(watsonDiscovery);
    
    System.out.println(qe.simpleQuery("What is context switch?"));
    //System.out.println(qe.simpleQuery("Who is DFI?"));
    
    Hashtable<String, ArrayList> dict = new Hashtable<>();
    
    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> file = new ArrayList<>();
    
    dict.put("text", text);
    dict.put("image", image);
    dict.put("url", url);
    dict.put("file", file);
    
  }
}