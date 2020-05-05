package team14.chatbot.IBMWatsonEngine;

import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;

/**
 * Contain methods used to change the URL to which Watson Discovery crawls.
 * @author Reynold
 *
 */

public class CrawlerEngine {

  private WatsonDiscovery wdisc;
  private String environmentId;
  private String configurationId;
  private String configurationName;

  public CrawlerEngine(WatsonDiscovery wdisc) {
    this.wdisc = wdisc;
    this.environmentId = this.wdisc.getEnvironmentId();
    this.configurationId = this.wdisc.getCrawlerConfigurationId();
    this.configurationName = "Chatbot";
  }


  public String addUrl(String url) {
    Discovery discovery = wdisc.getDiscovery();
    UpdateConfigurationOptions.Builder updateBuilder =
        new UpdateConfigurationOptions.Builder().environmentId(environmentId)
            .configurationId(getConfigurationId()).name(this.configurationName);
    Configuration config = getConfiguration();
    Source source = config.getSource();
    SourceOptionsWebCrawl newSourceOptionsWebCrawl = new SourceOptionsWebCrawl();
    newSourceOptionsWebCrawl.setUrl(url);
    if (source.getOptions().getUrls().contains(newSourceOptionsWebCrawl)) return null;
    source.getOptions().getUrls().add(newSourceOptionsWebCrawl);
    config.setSource(source);
    updateBuilder.configuration(config);
    Configuration updateResponse = discovery.updateConfiguration(updateBuilder.build()).execute().getResult();
    return updateResponse.getConfigurationId();
  }

  public String removeUrl(String url) {
    Discovery discovery = wdisc.getDiscovery();
    UpdateConfigurationOptions.Builder updateBuilder =
            new UpdateConfigurationOptions.Builder().environmentId(environmentId)
                    .configurationId(getConfigurationId()).name(this.configurationName);
    Configuration config = getConfiguration();
    Source source = config.getSource();
    SourceOptionsWebCrawl newSourceOptionsWebCrawl = new SourceOptionsWebCrawl();
    newSourceOptionsWebCrawl.setUrl(url);
    if (! source.getOptions().getUrls().contains(newSourceOptionsWebCrawl)) return null;
    source.getOptions().getUrls().remove(newSourceOptionsWebCrawl);
    config.setSource(source);
    updateBuilder.configuration(config);
    Configuration updateResponse = discovery.updateConfiguration(updateBuilder.build()).execute().getResult();
    return updateResponse.getConfigurationId();
  }

  public Configuration getConfiguration() {
    Discovery discovery = wdisc.getDiscovery();
    GetConfigurationOptions getOptions = new GetConfigurationOptions.Builder(environmentId, getConfigurationId()).build();
    return discovery.getConfiguration(getOptions).execute().getResult();
  }

  private String getConfigurationId() {
    return this.configurationId;
  }

/*
  private String getCredentialId() {
    Discovery discovery = wdisc.getDiscovery();
    GetConfigurationOptions getOptions =
            new GetConfigurationOptions.Builder(environmentId, getConfigurationId())
                    .build();
    Configuration getResponse =
            discovery.getConfiguration(getOptions).execute().getResult();
    System.out.println(getResponse);
    return getResponse.getSource().getCredentialId();
  }


  public static void main (String[] args) {
    CrawlerEngine ce = new CrawlerEngine(WatsonDiscovery.buildDiscovery());
    ce.getCredentialId();
  }
*/

}
