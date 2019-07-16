package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;



public class CrawlerEngine {

    private WatsonDiscovery wdisc;
    private String environmentId;
    private String credentialType;
    private String sourceType;
    private String languageCode;
    private String collectionId;

    public CrawlerEngine(WatsonDiscovery wdisc) {
        this.wdisc = wdisc;
        this.environmentId = this.wdisc.getEnvironmentId();
        this.collectionId = this.wdisc.getCrawlerCollectionId();
        this.credentialType = "noauth";
        this.sourceType = "web_crawl";
        this.languageCode = "en";
    }

    public String createCredential(String url) {
        Discovery discovery = wdisc.getDiscovery();
        CredentialDetails credentialDetails = new CredentialDetails();
        credentialDetails.setCredentialType(this.credentialType);
        credentialDetails.setUrl(url);
        Credentials credentials = new Credentials();
        credentials.setSourceType(this.sourceType);
        credentials.setCredentialDetails(credentialDetails);
        CreateCredentialsOptions createOptions = new CreateCredentialsOptions.Builder()
                .environmentId(this.environmentId)
                .credentials(credentials)
                .build();
        Credentials response = discovery.createCredentials(createOptions).execute().getResult();
        return response.getCredentialId();
    }

    public void list() {
        Discovery discovery = wdisc.getDiscovery();
        ListCredentialsOptions listOptions = new ListCredentialsOptions.Builder()
                .environmentId(environmentId)
                .build();

        CredentialsList response = discovery.listCredentials(listOptions).execute().getResult();
        System.out.println(response.getCredentials());
    }

    public String updateCredential(String url, String credentialId) {
        Discovery discovery = wdisc.getDiscovery();
        CredentialDetails updatedDetails = new CredentialDetails();
        updatedDetails.setCredentialType(credentialType);
        updatedDetails.setUrl(url);

        UpdateCredentialsOptions updateOptions = new UpdateCredentialsOptions.Builder()
                .environmentId(environmentId)
                .credentialId(credentialId)
                .sourceType(sourceType)
                .credentialDetails(updatedDetails)
                .build();

        Credentials response = discovery.updateCredentials(updateOptions).execute().getResult();
        System.out.println(response.getCredentialDetails());
        return response.getStatus();
    }

    public void deleteCredential(String credentialId) {
        Discovery discovery = wdisc.getDiscovery();
        DeleteCredentialsOptions deleteOptions = new DeleteCredentialsOptions.Builder()
                .environmentId(environmentId)
                .credentialId(credentialId)
                .build();

        discovery.deleteCredentials(deleteOptions).execute();
    }

    public String createConfiguration(String configurationName, String credentialId) {
        Discovery discovery = wdisc.getDiscovery();

        CreateConfigurationOptions.Builder createBuilder = new CreateConfigurationOptions.Builder();
        Configuration configuration = new Configuration();
        configuration.setName(configurationName);
        createBuilder.configuration(configuration);
        createBuilder.environmentId(environmentId);
        Source source = new Source();
        source.setCredentialId(credentialId);
        source.setType(sourceType);
        createBuilder.source(source);
        Configuration createResponse = discovery.createConfiguration(createBuilder.build()).execute().getResult();
        return createResponse.getConfigurationId();

    }

    public String updateConfiguration(String configurationId, String credentialId, String name) {
        Discovery discovery = wdisc.getDiscovery();
        Configuration updatedConfiguration = new Configuration();
        UpdateConfigurationOptions.Builder updateBuilder = new UpdateConfigurationOptions.Builder()
                .environmentId(environmentId)
                .configurationId(configurationId)
                .name(name);
        updatedConfiguration.setName(name);
        Source source = new Source();
        source.setCredentialId(credentialId);
        source.setType(sourceType);
        updateBuilder.source(source);
        updateBuilder.configuration(updatedConfiguration);
        Configuration updateResponse = discovery.updateConfiguration(updateBuilder.build()).execute().getResult();
        return updateResponse.getConfigurationId();
    }

    public void listConfiguration() {
        Discovery discovery = wdisc.getDiscovery();
        ListConfigurationsOptions listOptions = new ListConfigurationsOptions.Builder(environmentId).build();
        ListConfigurationsResponse listResponse = discovery.listConfigurations(listOptions).execute().getResult();
        System.out.println(listResponse.getConfigurations());
    }

    public String getCredentialId() {
        Discovery discovery = wdisc.getDiscovery();
        GetConfigurationOptions getOptions = new GetConfigurationOptions.Builder(environmentId, getConfigurationId()).build();
        Configuration getResponse = discovery.getConfiguration(getOptions).execute().getResult();
        return getResponse.getSource().getCredentialId();
    }

    public String createCrawlerCollection(String configurationId, String collectionName) {

        Discovery discovery = wdisc.getDiscovery();

        CreateCollectionOptions.Builder createCollectionBuilder = new CreateCollectionOptions.Builder()
                .environmentId(environmentId)
                .configurationId(configurationId)
                .name(collectionName)
                .language(languageCode);
        Collection createResponse = discovery.createCollection(createCollectionBuilder.build()).execute().getResult();
        return createResponse.getCollectionId();
    }

    public String getConfigurationId() {
        Discovery discovery = wdisc.getDiscovery();
        GetCollectionOptions getOptions = new GetCollectionOptions.Builder(environmentId, collectionId).build();
        Collection getResponse = discovery.getCollection(getOptions).execute().getResult();
        return getResponse.getConfigurationId();
    }

    public String updateCollection(String configurationId) {

        Discovery discovery = wdisc.getDiscovery();

        String updateCollectionName = "Web-crawl";

        UpdateCollectionOptions updateOptions = new UpdateCollectionOptions.Builder(environmentId, collectionId)
                .name(updateCollectionName)
                .configurationId(configurationId)
                .build();

        Collection updatedCollection = discovery.updateCollection(updateOptions).execute().getResult();
        return updatedCollection.getCollectionId();

    }

    public static void main (String args[]) {

        WatsonDiscovery widsc = WatsonDiscovery.buildDiscovery();
        CrawlerEngine ce = new CrawlerEngine(widsc);
//        String credId = ce.createCredential("https://utsctscpa.weebly.com/");
//        ce.listConfiguration();
//        String configId = ce.getConfigurationId();
//        System.out.println(configId);
//        String configId2 = ce.updateConfiguration(configId, credId, "newConfig");
//        System.out.println(configId2);
//        ce.updateCollection(configId2);
        // System.out.println(ce.createCrawlerCollection(configId, "dfiWebsite"));
        System.out.println(ce.getCredentialId());

    }
}
