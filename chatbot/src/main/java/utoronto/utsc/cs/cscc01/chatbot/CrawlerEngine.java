package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;



public class CrawlerEngine {

    private WatsonDiscovery wdisc;
    private String environmentId;
    private String credentialType;
    private String sourceType;
    private String languageCode;

    public CrawlerEngine(WatsonDiscovery wdisc) {
        this.wdisc = wdisc;
        this.environmentId = this.wdisc.getEnvironmentId();
        this.credentialType = "noauth";
        this.sourceType = "web_crawl";
        this.languageCode = "en";
    }

    public void configureWebCrawling(String url) {

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

    public static void main (String args[]) {

        WatsonDiscovery widsc = WatsonDiscovery.buildDiscovery();
        CrawlerEngine ce = new CrawlerEngine(widsc);
        String credId = ce.createCredential("https://www.digitalfinanceinstitute.org");
        String configId = ce.createConfiguration("dfiConfig", credId);
        System.out.println(ce.createCrawlerCollection(configId, "dfiWebsite"));

    }
}
