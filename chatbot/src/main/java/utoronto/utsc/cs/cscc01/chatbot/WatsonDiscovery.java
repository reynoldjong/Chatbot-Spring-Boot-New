package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.discovery.v1.Discovery;

public class WatsonDiscovery {
  private static WatsonDiscovery watsonDiscovery = null;
  private Discovery discovery;
  private static final String environmentId = "e4164f4e-d658-4fb5-b2a5-92608aa5a2b7";
  private static final String uploadedFilesCollectionId = "5907e278-2e52-440a-b6e2-8dd58d0d956b";
  private static final String crawlerCollectionId = "56312aeb-7765-42aa-baaf-889b681960d4";
  
  private WatsonDiscovery() {
    IamOptions options = new IamOptions.Builder()
        .apiKey("2--uALhovPVgaO1511XMlDnD-oSKiCBs4RvjRZtxgga7")
        .build();
    this.discovery = new Discovery("2019-07-15", options);
    this.discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");
  }
  
  public Discovery getDiscovery() {
    return this.discovery;
  }
  
  public String getEnvironmentId() {
    return this.environmentId;
  }
  
  public String getUploadedFilesCollectionId() {
    return this.uploadedFilesCollectionId;
  }
  
  public String getCrawlerCollectionId() {
    return this.crawlerCollectionId;
  }
  
  public static WatsonDiscovery buildDiscovery() {
    if (watsonDiscovery == null) 
      return new WatsonDiscovery();
    else
      return watsonDiscovery;
  }
}
