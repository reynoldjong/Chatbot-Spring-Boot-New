package utoronto.utsc.cs.cscc01.chatbot;

import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;


import java.io.*;


public class FilesEngine {

    private WatsonDiscovery wdisc;
    private String environmentId; 
    private String uploadedFilesCollectionId;
  
    public FilesEngine(WatsonDiscovery wdisc) {
        this.wdisc = wdisc;
        this.environmentId = this.wdisc.getEnvironmentId();
        this.uploadedFilesCollectionId = this.wdisc.getUploadedFilesCollectionId();
    }

   // String environmentId = wdisc.getEnvironmentId();
    //String uploadedFilesCollectionId = wdisc.getUploadedFilesCollectionId();


    public String uploadFiles(InputStream documentStream, String filename) {
        Discovery discovery = wdisc.getDiscovery();
        AddDocumentOptions.Builder builder = new AddDocumentOptions.Builder(environmentId, uploadedFilesCollectionId);
        builder.file(documentStream);
        builder.filename(filename);
        DocumentAccepted response = discovery.addDocument(builder.build()).execute().getResult();
        return response.getDocumentId();
    }

    public String updateFiles(InputStream updatedDocumentStream, String filename, String documentId) {
        Discovery discovery = wdisc.getDiscovery();

        UpdateDocumentOptions.Builder updateBuilder = new UpdateDocumentOptions.Builder(environmentId, uploadedFilesCollectionId, documentId);
        updateBuilder.file(updatedDocumentStream);
        updateBuilder.filename(filename);
        DocumentAccepted updateResponse = discovery.updateDocument(updateBuilder.build()).execute().getResult();
        return updateResponse.getDocumentId();
    }

    public String removeFiles(String documentId) {
        Discovery discovery = wdisc.getDiscovery();
        DeleteDocumentOptions deleteRequest = new DeleteDocumentOptions.Builder(environmentId, uploadedFilesCollectionId, documentId).build();
        DeleteDocumentResponse deleteResponse = discovery.deleteDocument(deleteRequest).execute().getResult();
        return deleteResponse.getStatus();
    }

}
