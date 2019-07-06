package utoronto.utsc.cs.cscc01.chatbot;

public class UploadedFile {

    private int id;
    private String filename;

    public UploadedFile(int id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
