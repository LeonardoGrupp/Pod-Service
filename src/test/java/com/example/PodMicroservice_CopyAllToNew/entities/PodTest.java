package com.example.PodMicroservice_CopyAllToNew.entities;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PodTest {

    @Test
    void getIdShouldReturnZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        long result = pod.getId();
        assertEquals(0, result);
    }

    @Test
    void setIdShouldSetIdToFour() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setId(4);
        long result = pod.getId();
        assertEquals(4, result);
    }

    @Test
    void getTypeShouldReturnPod() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        String result = pod.getType();
        assertEquals("pod", result);
    }

    @Test
    void setTypeShouldSetTypeToPod() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setType("pod");
        String result = pod.getType();
        assertEquals("pod", result);
    }

    @Test
    void getTitleShouldReturnPod1() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        String result = pod.getTitle();
        assertEquals("Pod1", result);
    }

    @Test
    void setTitleShouldSetTitleToPod2() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setTitle("Pod2");
        String result = pod.getTitle();
        assertEquals("Pod2", result);
    }

    @Test
    void getUrlShouldReturnURL1() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        String result = pod.getUrl();
        assertEquals("URL1", result);
    }

    @Test
    void setUrlShouldSetUrlToURL4() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setUrl("URL4");
        String result = pod.getUrl();
        assertEquals("URL4", result);
    }

    @Test
    void getReleaseDateShouldReturnReleaseDate() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        String result = pod.getReleaseDate();
        assertEquals("2024-12-24", result);
    }

    @Test
    void setReleaseDateShouldUpdateReleaseDate() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setReleaseDate("1978-01-01");
        String result = pod.getReleaseDate();
        assertEquals("1978-01-01", result);
    }

    @Test
    void getPlayCounterShouldReturnZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        int result = pod.getPlayCounter();
        assertEquals(0, result);
    }

    @Test
    void setPlayCounterShouldUpdatePlayCounterToFour() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setPlayCounter(4);
        int result = pod.getPlayCounter();
        assertEquals(4, result);
    }

    @Test
    void getLikesShouldReturnZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        int result = pod.getLikes();
        assertEquals(0, result);
    }

    @Test
    void setLikesShouldUpdateLikesToFour() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setLikes(4);
        int result = pod.getLikes();
        assertEquals(4, result);
    }

    @Test
    void getDisLikesShouldReturnZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        int result = pod.getDisLikes();
        assertEquals(0, result);
    }

    @Test
    void setDisLikesShouldUpdatePlayCounterToFour() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.setDisLikes(4);
        int result = pod.getDisLikes();
        assertEquals(4, result);
    }

    @Test
    void getGenresShouldReturnEmptyGenreList() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        assertTrue(pod.getGenres().isEmpty());
    }

    @Test
    void setGenresShouldUpdateGenreList() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Rock"));
        genres.add(new Genre("Pop"));
        pod.setGenres(genres);
        assertEquals("Pop", pod.getGenres().get(1).getGenre());
    }

    @Test
    void getAlbumsShouldReturnEmptyAlbumList() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        assertTrue(pod.getAlbums().isEmpty());
    }

    @Test
    void setAlbumsShouldUpdateAlbumList() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        List<Album> album = new ArrayList<>();
        album.add(new Album("Rock in Rio"));
        album.add(new Album("Alchemy"));
        pod.setAlbums(album);
        assertEquals("Alchemy", pod.getAlbums().get(1).getName());
    }

    @Test
    void getArtistsShouldReturnEmptyArtistList() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        assertTrue(pod.getArtists().isEmpty());
    }

    @Test
    void setArtistsShouldUpdateArtistList() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        List<Artist> artist = new ArrayList<>();
        artist.add(new Artist("Iron Maiden"));
        artist.add(new Artist("Dire Straits"));
        pod.setArtists(artist);
        assertEquals("Dire Straits", pod.getArtists().get(1).getName());
    }

    @Test
    void countPlayShouldIncreasePlayCounterFromZeroToOne() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.countPlay();
        int result = pod.getPlayCounter();
        assertEquals(1, result);
    }

    @Test
    void resetPlayShouldResetPlayCounterToZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.countPlay();
        pod.resetPlayCounter();
        int result = pod.getPlayCounter();
        assertEquals(0, result);
    }

    @Test
    void addLikeShouldIncreaseLikesWithOne() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.addLike();
        int result = pod.getLikes();
        assertEquals(1, result);
    }

    @Test
    void resetLikesShouldResetLikesToZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.addLike();
        pod.resetLikes();
        int result = pod.getLikes();
        assertEquals(0, result);
    }

    @Test
    void addDisLikeShouldIncreaseDisLikesWithOne() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.addDisLike();
        int result = pod.getDisLikes();
        assertEquals(1, result);
    }

    @Test
    void resetDisLikesShouldResetDisLikesToZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.addDisLike();
        pod.resetDisLikes();
        int result = pod.getDisLikes();
        assertEquals(0, result);
    }

    @Test
    void resetPlayCounterLikesAndDisLikesShouldResetPlayCounterLikesAndDisLikesToZero() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        pod.countPlay();
        pod.addLike();
        pod.addDisLike();
        pod.resetPlayCounterLikesAndDisLikes();
        assertEquals(0, pod.getPlayCounter());
        assertEquals(0, pod.getLikes());
        assertEquals(0, pod.getDisLikes());
    }

    @Test
    void ConstructorShouldReturnRightValuesCombinedWithEmptyValues() {
        Pod pod = new Pod("Pod1", "URL1", "2024-12-24");
        assertEquals("Pod1", pod.getTitle());
        assertEquals("URL1", pod.getUrl());
        assertEquals("2024-12-24", pod.getReleaseDate());
        assertTrue(pod.getGenres().isEmpty());
        assertTrue(pod.getAlbums().isEmpty());
        assertTrue(pod.getArtists().isEmpty());
    }

    @Test
    void ConstructorShouldReturnRightValues() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Heavy Metal"));
        genres.add(new Genre("Rock"));
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Rock in Rio"));
        albums.add(new Album("Alchemy"));
        List<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Iron Maiden"));
        artists.add(new Artist("Dire Straits"));

        Pod pod = new Pod("Pod1", "URL1", "2024-12-24", genres, albums, artists);

        assertNotNull(pod);
        assertEquals("pod", pod.getType());  // Kontrollera att typfältet är korrekt satt
        assertEquals("Pod1", pod.getTitle());
        assertEquals("URL1", pod.getUrl());
        assertEquals("2024-12-24", pod.getReleaseDate());
        assertEquals("Rock", pod.getGenres().get(1).getGenre());
        assertEquals("Rock in Rio", pod.getAlbums().get(0).getName());
        assertEquals("Dire Straits", pod.getArtists().get(1).getName());
    }

    @Test
    void ConstructorShouldReturnRightValueForId() {
        Pod pod = new Pod();
        assertEquals(0, pod.getId());
    }
}