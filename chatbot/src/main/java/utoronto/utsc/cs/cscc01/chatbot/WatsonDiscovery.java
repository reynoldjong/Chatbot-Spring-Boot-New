package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.discovery.v1.Discovery;

public class WatsonDiscovery {
  private static WatsonDiscovery watsonDiscovery = null;
  private Discovery discovery;
  private static final String environmentId =
      "35ee748a-5486-4809-a8be-1c1b5773b73e";
  private static final String uploadedFilesCollectionId =
      "5907e278-2e52-440a-b6e2-8dd58d0d956b";
  private static final String crawlerCollectionId =
      "bedf65b9-55ff-4260-9d44-56adbd5b5722";

  private WatsonDiscovery() {
    IamOptions options = new IamOptions.Builder()
        .apiKey("JU3T588lVW-SPABz-eoxiC-rnvCH07FJ7_LZQEqAXxXh").build();
    this.discovery = new Discovery("2019-06-29", options);
    this.discovery
        .setEndPoint("https://gateway-wdc.watsonplatform.net/discovery/api");
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
    if (WatsonDiscovery.watsonDiscovery == null) {
      WatsonDiscovery.watsonDiscovery = new WatsonDiscovery();
      return WatsonDiscovery.watsonDiscovery;
    } else {
      return WatsonDiscovery.watsonDiscovery;
    }

  }
}
