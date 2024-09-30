package com.example.PodMicroservice_CopyAllToNew.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void getIdShouldReturnTwoAsAlbumId() {
        Album album = new Album(2, "Album1");
        long result = album.getId();
        assertEquals(2, result);
    }

    @Test
    void setIdShouldSetThreeAsAlbumId() {
        Album album = new Album(2, "Album1");
        album.setId(3);
        assertEquals(3, album.getId());
    }

    @Test
    void getNameShouldReturnAlbum1AsAlbumName() {
        Album album = new Album(2, "Album1");
        String result = album.getName();
        assertEquals("Album1", result);
    }

    @Test
    void setNameShouldSetAlbum2AsAlbumName() {
        Album album = new Album(2, "Album1");
        album.setName("Album2");
        assertEquals("Album2", album.getName());
    }

    @Test
    void ConstructorShouldReturnRightValueForName() {
        Album album = new Album("Album1");
        assertEquals("Album1", album.getName());
    }

    @Test
    void ConstructorShouldReturnRightValueForId() {
        Album album = new Album();
        assertEquals(0, album.getId());
    }
}