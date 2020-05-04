package team14.chatbot.IBMWatsonEngine;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.discovery.v1.Discovery;

/**
 * Singleton class used to wrap the IBM Watson Discovery object
 * @author Chris
 *
 */
public class WatsonDiscovery {
    private static WatsonDiscovery watsonDiscovery = null;
    private Discovery discovery;
    private static final String environmentId =
        "35ee748a-5486-4809-a8be-1c1b5773b73e";
    private static final String uploadedFilesCollectionId =
            "5907e278-2e52-440a-b6e2-8dd58d0d956b";
    private static final String crawlerCollectionId =
            "bedf65b9-55ff-4260-9d44-56adbd5b5722";
    private static final String crawlerConfigurationId =
            "fc48ac7e-ab97-4d51-86d1-e2613b80d63f";

    // For testing purpose
//    private static final String environmentId =
//      "194211fe-5c5e-4ed6-b998-9c50b2897fbe";
//    private static final String crawlerCollectionId =
//      "8aae7e5d-e0b7-4ec4-a1ba-62d9795d63f6";


  private WatsonDiscovery() {
    IamOptions options = new IamOptions.Builder()
        .apiKey("JU3T588lVW-SPABz-eoxiC-rnvCH07FJ7_LZQEqAXxXh").build();
    this.discovery = new Discovery("2020-04-12", options);
    this.discovery
        .setEndPoint("https://gateway-wdc.watsonplatform.net/discovery/api");

    //For Testing Purpose
//    IamOptions options = new IamOptions.Builder()
//            .apiKey("TSIKFRqN48HFKIDW9xdxZUGF4Ger3Sc0aeMvnRWv941n").build();
//    this.discovery = new Discovery("2020-04-12", options);
//    this.discovery
//            .setEndPoint("https://api.eu-gb.discovery.watson.cloud.ibm.com/instances/0b200024-2b15-422c-a509-1cf6433c6faf");
  }

  /**
   * Method used to unwrap the singleton and retrieve the Discovery class
   * defined by IBM
   * @return Discovery object contained within the singleton
   */
  public Discovery getDiscovery() {
    return this.discovery;
  }

  /**
   * Retrieve the environment ID of the Watson Discovery instance on IBM cloud
   * @return String representing the Watson Discovery environment ID
   */
  public String getEnvironmentId() {
    return this.environmentId;
  }

  /**
   * Retrieve the collection ID of the uploaded files collection for the 
   * Watson Discovery instance on IBM cloud
   * @return String representing the Watson Discovery collection ID of
   * uploaded files
   */
  public String getUploadedFilesCollectionId() {
    return this.uploadedFilesCollectionId;
  }

  /**
   * Retrieve the collection ID of the crawled url collection for the 
   * Watson Discovery instance on IBM cloud
   * @return String representing the Watson Discovery collection ID of
   * crawled url
   */
  public String getCrawlerCollectionId() {
    return this.crawlerCollectionId;
  }

  /**
   * Retrieve the configuration ID of the crawled url collection for the
   * Watson Discovery instance on IBM cloud
   * @return String representing the Watson Discovery configuration ID of
   * crawled url
   */
  public String getCrawlerConfigurationId() {
    return this.crawlerConfigurationId;
  }

  /**
   * Singleton constructor
   * @return Watson Discovery object that contains the IBM Discovery object
   */
  public static WatsonDiscovery buildDiscovery() {
    if (watsonDiscovery == null)
      watsonDiscovery = new WatsonDiscovery();
    return watsonDiscovery;
  }
}
