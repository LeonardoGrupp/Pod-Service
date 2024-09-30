package com.example.PodMicroservice_CopyAllToNew.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {

    @Test
    void getIdShouldReturnTwoAsArtistId() {
        Artist artist = new Artist(2, "Artist1");
        long result = artist.getId();
        assertEquals(2, result);
    }

    @Test
    void setIdShouldSetThreeAsArtistId() {
        Artist artist = new Artist(2, "Artist1");
        artist.setId(3);
        assertEquals(3, artist.getId());
    }

    @Test
    void getNameShouldReturnArtist1AsArtistName() {
        Artist artist = new Artist(2, "Artist1");
        String result = artist.getName();
        assertEquals("Artist1", result);
    }

    @Test
    void setNameShouldSetArtist2AsArtistName() {
        Artist artist = new Artist(2, "Artist1");
        artist.setName("Artist2");
        assertEquals("Artist2", artist.getName());
    }

    @Test
    void ConstructorShouldReturnRightValueForId() {
        Artist artist = new Artist();
        assertEquals(0, artist.getId());
    }

    @Test
    void ConstructorShouldReturnRightValueForName() {
        Artist artist = new Artist("Artist1");
        assertEquals("Artist1", artist.getName());
    }

    @Test
    void ConstructorShouldReturnRightValues() {
        Artist artist = new Artist(3, "Artist1");
        assertEquals("Artist1", artist.getName());
        assertEquals(3, artist.getId());
    }
}