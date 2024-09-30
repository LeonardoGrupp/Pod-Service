package com.example.PodMicroservice_CopyAllToNew.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void getIdShouldReturnTwo() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        long result = genre.getId();
        assertEquals(2, result);
    }

    @Test
    void setIdShouldSetIdToFour() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        genre.setId(4);
        long result = genre.getId();
        assertEquals(4, result);
    }

    @Test
    void getGenreShouldReturnGenre1() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        String result = genre.getGenre();
        assertEquals("Genre1", result);
    }

    @Test
    void setGenreShouldSetGenreToGenre2() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        genre.setGenre("Genre2");
        String result = genre.getGenre();
        assertEquals("Genre2", result);
    }

    @Test
    void getTypeShouldReturnPod() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        String result = genre.getType();
        assertEquals("pod", result);
    }

    @Test
    void setTypeShouldSetTypeToPod() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        genre.setType("pod");
        String result = genre.getType();
        assertEquals("pod", result);
    }

    @Test
    void getTotalLikesShouldReturnZero() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        int result = genre.getTotalLikes();
        assertEquals(0, result);
    }

    @Test
    void setTotalLikesShouldSetTotalLikesToFive() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        genre.setTotalLikes(5);
        int result = genre.getTotalLikes();
        assertEquals(5, result);
    }

    @Test
    void getTotalPlaysShouldReturnZero() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        int result = genre.getTotalPlays();
        assertEquals(0, result);
    }

    @Test
    void setTotalPlaysShouldSetTotalPlaysToThree() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        genre.setTotalPlays(3);
        int result = genre.getTotalPlays();
        assertEquals(3, result);
    }

    @Test
    void countPlayShouldIncreaseTotalPlaysFromZeroToOne() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        genre.countPlay();
        int result = genre.getTotalPlays();
        assertEquals(1, result);
    }

    @Test
    void addLikeShouldIncreaseTotalLikesFromZeroToOne() {
        Genre genre = new Genre(2, "Genre1", "pod", 0,0);
        genre.addLike();
        int result = genre.getTotalLikes();
        assertEquals(1, result);
    }

    @Test
    void ConstructorShouldReturnRightValues() {
        Genre genre = new Genre( "Genre", "pod", 0,0);
        assertEquals("Genre", genre.getGenre());
        assertEquals("pod", genre.getType());
        assertEquals(0, genre.getTotalLikes());
        assertEquals(0, genre.getTotalPlays());
    }

    @Test
    void ConstructorShouldReturnRightValueForGenre() {
        Genre genre = new Genre("Genre");
        assertEquals("Genre", genre.getGenre());
    }

    @Test
    void ConstructorShouldReturnRightValueForId() {
        Genre genre = new Genre();
        assertEquals(0, genre.getId());
    }
}