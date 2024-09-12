package com.example.PodMicroservice_CopyAllToNew.entities;

import com.example.PodMicroservice_CopyAllToNew.vo.Album;
import com.example.PodMicroservice_CopyAllToNew.vo.Artist;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pods")
public class Pod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;
    @Column(nullable = false, length = 250)
    private String title;
    @Column(nullable = false, length = 250)
    private String url;
    @Column(nullable = false, length = 10)
    private String releaseDate;
    private int playCounter;
    private int likes;
    private int disLikes;

    @ManyToMany
    @JoinTable(
            name = "pods_genres", // Name of the join table
            joinColumns = @JoinColumn(name = "pods_id"), // Foreign key in join table pointing to 'music' table
            inverseJoinColumns = @JoinColumn(name = "genre_id") // Foreign key in join table pointing to 'genres' table
    )
    private List<Genre> genres;

    @ElementCollection
    @CollectionTable(
            name = "pods_albums",
            joinColumns = @JoinColumn(name = "pods_id")
    )
    private List<Album> albums;

    @ElementCollection
    @CollectionTable(
            name = "pods_artists",
            joinColumns = @JoinColumn(name = "pods_id")
    )
    private List<Artist> artists;

    public Pod() {
    }

    public Pod(String title, String url, String releaseDate) {
        this.type = "pod";
        this.title = title;
        this.url = url;
        this.releaseDate = releaseDate;
        this.playCounter = 0;
        this.likes = 0;
        this.disLikes = 0;
        this.genres = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artists = new ArrayList<>();
    }

    public Pod(String title, String url, String releaseDate, List<Genre> genres, List<Album> albums, List<Artist> artists) {
        this.type = "pod";
        this.title = title;
        this.url = url;
        this.releaseDate = releaseDate;
        this.playCounter = 0;
        this.likes = 0;
        this.disLikes = 0;
        this.genres = genres;
        this.albums = albums;
        this.artists = artists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPlayCounter() {
        return playCounter;
    }

    public void setPlayCounter(int playCounter) {
        this.playCounter = playCounter;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(int disLikes) {
        this.disLikes = disLikes;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public void countPlay() {
        this.playCounter += 1;
    }

    public void resetPlayCounter() {
        this.playCounter = 0;
    }

    public void addLike() {
        this.likes += 1;
    }

    public void resetLies() {
        this.likes = 0;
    }

    public void addDisLike() {
        this.disLikes += 1;
    }

    public void resetDisLikes() {
        this.disLikes = 0;
    }

    public void resetPlayCounterLikesAndDisLikes() {
        this.playCounter = 0;
        this.likes = 0;
        this.disLikes = 0;
    }
}