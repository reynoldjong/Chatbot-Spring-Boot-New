package utoronto.utsc.cs.cscc01.chatbot;

public class Query {

    private int id;
    private String content;
    private int frequency;

    public Query(int id, String content, int frequency) {
        this.id = id;
        this.content = content;
        this.frequency = frequency;
    }

    public void setId(int id) { this.id = id; }

    public void setContent(String content) {this.content = content; }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    public int getId() { return this.id; }

    public String getContent() {return this.content; }

    public int getFrequency() {
        return this.frequency;
    }

}
