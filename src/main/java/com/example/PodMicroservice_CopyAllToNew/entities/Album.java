package com.example.PodMicroservice_CopyAllToNew.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pod_albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "album_name")
    private String name;

    @Column(name = "release_date")
    private String releaseDate;

    public Album() {
    }

    public Album(String name, String releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}