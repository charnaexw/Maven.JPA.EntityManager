package entities;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int year;
    private String artist;
    private double price;
    @OneToMany
    private Set<Artist> artists = new HashSet<>();

    public Album(long id, String artist, String title, int i, int price){}

    public Album(Long id, String title, int year, String artist, double price) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.artist = artist;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
