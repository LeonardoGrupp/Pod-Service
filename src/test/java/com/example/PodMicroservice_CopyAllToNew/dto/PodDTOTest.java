package com.example.PodMicroservice_CopyAllToNew.dto;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PodDTOTest {

    @Test
    void getTypeShouldReturnType() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        assertEquals("pod", poddto.getType());
    }

    @Test
    void setTypeShouldChangeTypeToPod() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        poddto.setType("pod");
        assertEquals("pod", poddto.getType());
    }

    @Test
    void getTitleShouldReturnTitle() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        assertEquals("DTO1", poddto.getTitle());
    }

    @Test
    void setTitleShouldChangeTitleToDTO2() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        poddto.setTitle("DTO2");
        assertEquals("DTO2", poddto.getTitle());
    }

    @Test
    void getUrlShouldReturnUrl() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        assertEquals("Url1", poddto.getUrl());
    }

    @Test
    void setUrlShouldChangeUrlToUrl2() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        poddto.setUrl("Url2");
        assertEquals("Url2", poddto.getUrl());
    }

    @Test
    void getReleaseDateShouldReturnReleaseDate() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        assertEquals("2000-01-01", poddto.getReleaseDate());
    }

    @Test
    void setReleaseDateShouldChangeReleaseDate() {
        PodDTO poddto = new PodDTO("DTO1", "Url1", "2000-01-01");
        poddto.setReleaseDate("2222-01-01");
        assertEquals("2222-01-01", poddto.getReleaseDate());
    }

    @Test
    void getGenresInputsShouldReturnEmptyGenreList() {
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24");
        assertTrue(poddto.getGenreInputs().isEmpty());
    }

    @Test
    void setGenresInputsShouldUpdateGenreList() {
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24");
        List<String> genres = new ArrayList<>();
        genres.add("Rock");
        genres.add("Pop");
        poddto.setGenreInputs(genres);
        assertEquals("Pop", poddto.getGenreInputs().get(1));
    }

    @Test
    void getAlbumInputsShouldReturnEmptyAlbumList() {
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24");
        assertTrue(poddto.getAlbumInputs().isEmpty());
    }

    @Test
    void setAlbumInputsShouldUpdateAlbumList() {
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24");
        List<String> albums = new ArrayList<>();
        albums.add("Rock in Rio");
        albums.add("Alchemy");
        poddto.setAlbumInputs(albums);
        assertEquals("Alchemy", poddto.getAlbumInputs().get(1));
    }

    @Test
    void getArtistInputsShouldReturnEmptyArtistList() {
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24");
        assertTrue(poddto.getArtistInputs().isEmpty());
    }

    @Test
    void setArtistInputsShouldUpdateArtistList() {
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24");
        List<String> artists = new ArrayList<>();
        artists.add("Iron Maiden");
        artists.add("Dire Straits");
        poddto.setArtistInputs(artists);
        assertEquals("Dire Straits", poddto.getArtistInputs().get(1));
    }

    @Test
    void ConstructorShouldReturnEmptyAndNullResults() {
        PodDTO poddto = new PodDTO();
        assertNull(poddto.getType());
        assertNull(poddto.getTitle());
        assertNull(poddto.getUrl());
        assertNull(poddto.getReleaseDate());
        assertTrue(poddto.getGenreInputs().isEmpty());
        assertTrue(poddto.getAlbumInputs().isEmpty());
        assertTrue(poddto.getArtistInputs().isEmpty());
    }

    @Test
    void ConstructorShouldReturnRightValuesCombinedWithEmptyValues() {
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24");
        assertEquals("Pod1", poddto.getTitle());
        assertEquals("URL1", poddto.getUrl());
        assertEquals("2024-12-24", poddto.getReleaseDate());
        assertEquals("pod", poddto.getType());
        assertTrue(poddto.getGenreInputs().isEmpty());
        assertTrue(poddto.getAlbumInputs().isEmpty());
        assertTrue(poddto.getArtistInputs().isEmpty());
    }

    @Test
    void ConstructorShouldReturnRightValues() {
        List<String> genres = Arrays.asList("Heavy Metal", "Rock");
        List<String> albums = Arrays.asList("Rock in Rio", "Alchemy");
        List<String> artists = Arrays.asList("Iron Maiden", "Dire Straits");
        PodDTO poddto = new PodDTO("Pod1", "URL1", "2024-12-24", genres, albums, artists);

        assertNotNull(poddto);
        assertEquals("pod", poddto.getType());  // Kontrollera att typfältet är korrekt satt
        assertEquals("Pod1", poddto.getTitle());
        assertEquals("URL1", poddto.getUrl());
        assertEquals("2024-12-24", poddto.getReleaseDate());
        assertEquals(Arrays.asList("Heavy Metal", "Rock"), poddto.getGenreInputs());
        assertEquals(Arrays.asList("Rock in Rio", "Alchemy"), poddto.getAlbumInputs());
        assertEquals(Arrays.asList("Iron Maiden", "Dire Straits"), poddto.getArtistInputs());
    }
}