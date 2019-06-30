package utoronto.utsc.cs.cscc01.chatbot;

import java.util.HashMap;
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

public class QueryEngine {
  
  private WatsonDiscovery wdisc;
  
  public QueryEngine(WatsonDiscovery wdisc) {
    this.wdisc = wdisc;
  }
  
  public String simpleQuery(String q) {
    Discovery discovery = wdisc.getDiscovery();
    
    String environmentId = wdisc.getEnvironmentId();
    String crawlerCollectionId = wdisc.getCrawlerCollectionId();
    String uploadedFilesCollectionId = wdisc.getUploadedFilesCollectionId();
    // query result of crawler for now, hard coded
    FederatedQueryOptions.Builder queryBuilder = new FederatedQueryOptions.Builder(environmentId);
    queryBuilder.naturalLanguageQuery(q);
    queryBuilder.collectionIds(crawlerCollectionId + "," + uploadedFilesCollectionId);
    queryBuilder.passages(true);
    queryBuilder.returnFields("metadata.source.url,extracted_metadata.filename");
    QueryResponse queryResponse = discovery.federatedQuery(queryBuilder.build()).execute().getResult();

    List<QueryResult> resultList = queryResponse.getResults();
    String result = "";
    // first check to see if we have any results
    if (resultList.size() > 0) {
      QueryResult firstResult = resultList.get(0);
      // if this was from an uploaded file
      if (firstResult.getCollectionId().equals(wdisc.getUploadedFilesCollectionId())) {
        Map<String, Object> map = firstResult.getProperties();
        Map<String, String> fileMap = (Map<String, String>) map.get("extracted_metadata");
        String filename = fileMap.get("filename");
        result = filename;
      }
      // if this was from crawler indexing the webpage
      else if (firstResult.getCollectionId().equals(wdisc.getCrawlerCollectionId())) {
        Map<String, Map<String, String>> map = firstResult.getMetadata();
        String url = map.get("source").get("url");
        result = url;
      }
    }
    
    List<QueryPassages> passageList = queryResponse.getPassages();
    
    return result;
  }

  public static void main(String[] args) {
    WatsonDiscovery watsonDiscovery = WatsonDiscovery.buildDiscovery();
    QueryEngine qe = new QueryEngine(watsonDiscovery);
    
    System.out.println(qe.simpleQuery("What is context switch?"));
    System.out.println(qe.simpleQuery("Who is DFI?"));
    
  }
}