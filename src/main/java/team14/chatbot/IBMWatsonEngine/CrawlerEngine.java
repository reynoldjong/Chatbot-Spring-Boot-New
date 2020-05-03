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

//  public SourceSchedule getSourceSchedule() {
//    SourceSchedule ss = new SourceSchedule();
//    ss.setEnabled(true);
//    ss.setTimeZone("America/New_York");
//    ss.setFrequency("weekly");
//    return ss;
//  }
//
//
//
//  public void listConfiguration() {
//    Discovery discovery = wdisc.getDiscovery();
//    ListConfigurationsOptions listOptions =
//            new ListConfigurationsOptions.Builder(environmentId).build();
//    ListConfigurationsResponse listResponse =
//            discovery.listConfigurations(listOptions).execute().getResult();
//    System.out.println(listResponse.getConfigurations());
//  }
//
//
//  public void deleteConfiguration(String configurationId) {
//    Discovery discovery = wdisc.getDiscovery();
//    DeleteConfigurationOptions deleteRequest =
//            new DeleteConfigurationOptions.Builder(environmentId, configurationId)
//                    .build();
//    DeleteConfigurationResponse deleteResponse =
//            discovery.deleteConfiguration(deleteRequest).execute().getResult();
//  }
//    public void queryConfiguration() {
//    Discovery discovery = wdisc.getDiscovery();
//    ListExpansionsOptions listOptions = new ListExpansionsOptions.Builder()
//            .environmentId(environmentId)
//            .collectionId(collectionId)
//            .build();
//
//    Expansions listResults = discovery.listExpansions(listOptions).execute().getResult();
//    System.out.println(listResults);
//  }
//
//  public String createCredential(String url) {
//    Discovery discovery = wdisc.getDiscovery();
//    CredentialDetails credentialDetails = new CredentialDetails();
//    credentialDetails.setCredentialType(this.credentialType);
//    credentialDetails.setUrl(url);
//    Credentials credentials = new Credentials();
//    credentials.setSourceType(this.sourceType);
//    credentials.setCredentialDetails(credentialDetails);
//    CreateCredentialsOptions createOptions =
//        new CreateCredentialsOptions.Builder().environmentId(this.environmentId)
//            .credentials(credentials).build();
//    Credentials response =
//        discovery.createCredentials(createOptions).execute().getResult();
//    return response.getCredentialId();
//  }
//
//  public void list() {
//    Discovery discovery = wdisc.getDiscovery();
//    ListCredentialsOptions listOptions = new ListCredentialsOptions.Builder()
//        .environmentId(environmentId).build();
//
//    CredentialsList response =
//        discovery.listCredentials(listOptions).execute().getResult();
//    System.out.println(response.getCredentials());
//  }
//  public String updateCredential(String url, String credentialId) {
//    Discovery discovery = wdisc.getDiscovery();
//    CredentialDetails updatedDetails = new CredentialDetails();
//    updatedDetails.setCredentialType(credentialType);
//    updatedDetails.setUrl(url);
//
//    UpdateCredentialsOptions updateOptions =
//        new UpdateCredentialsOptions.Builder().environmentId(environmentId)
//            .credentialId(credentialId).sourceType(sourceType)
//            .credentialDetails(updatedDetails).build();
//
//    Credentials response =
//        discovery.updateCredentials(updateOptions).execute().getResult();
//    System.out.println(response.getCredentialDetails());
//    return response.getStatus();
//  }
//
//  public void deleteCredential(String credentialId) {
//    Discovery discovery = wdisc.getDiscovery();
//    DeleteCredentialsOptions deleteOptions =
//        new DeleteCredentialsOptions.Builder().environmentId(environmentId)
//            .credentialId(credentialId).build();
//
//    discovery.deleteCredentials(deleteOptions).execute();
//  }
//
//  public String createConfiguration(String configurationName,
//      String credentialId) {
//    Discovery discovery = wdisc.getDiscovery();
//
//    CreateConfigurationOptions.Builder createBuilder =
//        new CreateConfigurationOptions.Builder();
//    Configuration configuration = new Configuration();
//    configuration.setName(configurationName);
//    createBuilder.configuration(configuration);
//    createBuilder.environmentId(environmentId);
//    Source source = new Source();
//    source.setCredentialId(credentialId);
//    source.setType(sourceType);
//    source.setSchedule(getSourceSchedule());
//    createBuilder.source(source);
//    Configuration createResponse = discovery
//        .createConfiguration(createBuilder.build()).execute().getResult();
//    return createResponse.getConfigurationId();
//
//  }
//
//
//  public String createCrawlerCollection(String configurationId,
//      String collectionName) {
//
//    Discovery discovery = wdisc.getDiscovery();
//
//    CreateCollectionOptions.Builder createCollectionBuilder =
//        new CreateCollectionOptions.Builder().environmentId(environmentId)
//            .configurationId(configurationId).name(collectionName)
//            .language(languageCode);
//    Collection createResponse =
//        discovery.createCollection(createCollectionBuilder.build()).execute()
//            .getResult();
//    return createResponse.getCollectionId();
//  }
//
//
//  public String updateCollection(String configurationId) {
//
//    Discovery discovery = wdisc.getDiscovery();
//
//    String updateCollectionName = "newCrawler";
//
//    UpdateCollectionOptions updateOptions =
//        new UpdateCollectionOptions.Builder(environmentId, collectionId)
//            .name(updateCollectionName).configurationId(configurationId)
//            .build();
//
//    Collection updatedCollection =
//        discovery.updateCollection(updateOptions).execute().getResult();
//    return updatedCollection.getCollectionId();
//
//  }
//
//  public static void main (String[] args) {
//    CrawlerEngine ce = new CrawlerEngine(WatsonDiscovery.buildDiscovery());
//    ce.getCredentialId();
//  }

}
