package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.Collection;
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
    // query result of crawler for now, hard coded
    QueryOptions.Builder queryBuilder = new QueryOptions.Builder(environmentId, crawlerCollectionId);
    queryBuilder.query("enriched_text.concepts.text:" + q);
    QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute().getResult();
    System.out.println(queryResponse.getMatchingResults());
    //System.out.println(queryResponse.getPassages().size());
    return "hello"; //queryResponse.getPassages().get(0).getPassageText();
  }

  public static void main(String[] args) {
    WatsonDiscovery watsonDiscovery = WatsonDiscovery.buildDiscovery();
    QueryEngine qe = new QueryEngine(watsonDiscovery);
    
    System.out.println(qe.simpleQuery("Finance"));
  }
}