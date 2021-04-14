package ch.uzh.ifi.hase.soprafs21.entity;


public class Picture {

    private String Url;
    private int id;

    public Picture(String link, int id){this.Url = link; this.id = id; }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
