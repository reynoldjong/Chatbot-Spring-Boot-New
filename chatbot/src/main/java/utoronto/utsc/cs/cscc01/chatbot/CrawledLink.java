package utoronto.utsc.cs.cscc01.chatbot;

public class CrawledLink {

    private String seed;
    private String date;

    public CrawledLink(String seed, String date) {
        this.seed = seed;
        this.date = date;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public void setDate(String date) {this.date = date; }

    public String getSeed() {
        return this.seed;
    }

    public String getDate() {return this.date; }


}
