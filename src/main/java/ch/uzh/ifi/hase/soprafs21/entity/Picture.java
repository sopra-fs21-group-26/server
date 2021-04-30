package ch.uzh.ifi.hase.soprafs21.entity;


import javax.persistence.*;

@Entity
@Table(name = "PICTURE")
public class Picture {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500000000)
    private String Url;

    @Column
    private String coordinate;

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Picture(String link, int id){this.Url = link; }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

}
