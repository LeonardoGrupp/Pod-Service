package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.repositories.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class GenreServiceTest {

    private GenreRepository genreRepositoryMock;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreRepositoryMock = mock(GenreRepository.class);
        genreService = new GenreService(genreRepositoryMock);
    }

    @Test
    void genreExistsShouldReturnTrueWhenExists() {
        Genre existingGenre = new Genre("Rock");

        when(genreRepositoryMock.findGenreByGenre("Rock")).thenReturn(existingGenre);

        boolean genreExists = genreService.genreExists("Rock");

        assertTrue(genreExists, "ERROR: Genre does not exist");

        verify(genreRepositoryMock).findGenreByGenre("Rock");
    }

    @Test
    void genreExistsShouldReturnFalseWhenNotExists() {
        when(genreRepositoryMock.findGenreByGenre("Rock")).thenReturn(null);

        boolean genreDoesNotExist = genreService.genreExists("Rock");

        assertFalse(genreDoesNotExist, "ERROR: Genre already exists");

        verify(genreRepositoryMock).findGenreByGenre("Rock");
    }

    @Test
    void findAllGenresShouldReturnAllGenres() {
        List<Genre> genreList = Arrays.asList(new Genre("Rock"), new Genre("RNB"), new Genre("Heavy Metal"));

        when(genreRepositoryMock.findAll()).thenReturn(genreList);

        List<Genre> response = genreService.findAllGenres();

        assertEquals(genreList, response, "ERROR: Lists was not identical");

        verify(genreRepositoryMock).findAll();
    }

    @Test
    void findGenreByNameShouldReturnGenreWhenExists() {
        Genre genre = new Genre("Rock");

        when(genreRepositoryMock.findGenreByGenre("Rock")).thenReturn(genre);

        Genre response = genreService.findGenreByName("Rock");

        assertEquals("Rock", response.getGenre(), "ERROR: Genres was not identical");

        verify(genreRepositoryMock).findGenreByGenre("Rock");
    }

    @Test
    void findGenreByIdShouldReturnGenreWhenExists() {
        long genreId = 1;
        Genre genre = new Genre("Rock");
        genre.setId(genreId);

        when(genreRepositoryMock.findById(genreId)).thenReturn(Optional.of(genre));

        Genre response = genreService.findGenreById(genreId);

        assertEquals(genre, response, "ERROR: Genres was not identical");

        verify(genreRepositoryMock).findById(genreId);
    }

    @Test
    void createGenreShouldReturnGenre() {
        Genre genre = new Genre("Rock");

        when(genreRepositoryMock.save(genre)).thenReturn(genre);

        Genre result = genreService.create(genre);

        assertEquals("Rock", result.getGenre(), "ERROR: Genres was not identical");

        verify(genreRepositoryMock).save(genre);
    }

    @Test
    void createGenreNoGenreShouldReturnException() {
        Genre genre = new Genre("");

        ResponseStatusException result = assertThrows(ResponseStatusException.class, () -> {
            genreService.create(genre);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Genre not provided", result.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, result.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(genreRepositoryMock, never()).save(genre);
    }

    @Test
    void createGenreNullGenreShouldReturnException() {
        Genre genre = new Genre(null);

        ResponseStatusException result = assertThrows(ResponseStatusException.class, () -> {
            genreService.create(genre);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Genre not provided", result.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, result.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(genreRepositoryMock, never()).save(genre);
    }

    @Test
    void createGenreWhenGenreAlreadyExistShouldReturnException() {
        Genre existingGenre = new Genre("Rock");

        when(genreRepositoryMock.findGenreByGenre("Rock")).thenReturn(existingGenre);

        ResponseStatusException result = assertThrows(ResponseStatusException.class, () -> {
            genreService.create(existingGenre);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Genre already exists", result.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(genreRepositoryMock, never()).save(existingGenre);
    }

    @Test
    void updateGenreShouldReturnGenre() {
        long existingId = 1;
        Genre existingGenre = new Genre("Rock");
        existingGenre.setId(existingId);

        Genre newGenreInfo = new Genre("RNB");

        when(genreRepositoryMock.findById(existingId)).thenReturn(Optional.of(existingGenre));
        when(genreRepositoryMock.save(existingGenre)).thenReturn(newGenreInfo);

        Genre updatedGenre = genreService.update(existingId, newGenreInfo);

        assertEquals("RNB", updatedGenre.getGenre(), "ERROR: Genres was not identical");

        verify(genreRepositoryMock).save(existingGenre);
    }

    @Test
    void updateGenreIdNotFoundShouldReturnException() {
        long existingId = 1;

        Genre newGenreInfo = new Genre("RNB");

        when(genreRepositoryMock.findById(existingId)).thenReturn(Optional.empty());

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            genreService.update(existingId, newGenreInfo);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Genre could not be found", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(genreRepositoryMock, never()).save(any(Genre.class));
    }

    @Test
    void deleteShouldReturnString() {
        long genreId = 1;
        Genre genreToDelete = new Genre("Rock");

        when(genreRepositoryMock.findById(genreId)).thenReturn(Optional.of(genreToDelete));

        String response = genreService.delete(genreId);

        assertEquals("Genre deleted", response, "ERROR: Strings was not identical");

        verify(genreRepositoryMock).delete(genreToDelete);
    }

    @Test
    void deleteShouldReturnExceptionWhenIdNotFound() {
        long genreId = 1;

        when(genreRepositoryMock.findById(genreId)).thenReturn(Optional.empty());

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            genreService.delete(genreId);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Genre could not be found with id: " + genreId, response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(genreRepositoryMock).findById(genreId);
        verify(genreRepositoryMock, never()).delete(any(Genre.class));
    }

    @Test
    void countPlayShouldIncreaseTotalPlaysWithOne() {
        Genre genre = new Genre("Rock");
        genre.setTotalPlays(0);

        genreService.countPlay(genre);

        assertEquals(1, genre.getTotalPlays(), "ERROR: Total Plays was not identical");

        verify(genreRepositoryMock).save(genre);
    }

    @Test
    void addLikeShouldIncreaseLikeWithOne() {
        Genre genre = new Genre("Rock");
        genre.setTotalLikes(0);

        genreService.addLike(genre);

        assertEquals(1, genre.getTotalLikes(), "ERROR: Total Likes was not identical");

        verify(genreRepositoryMock).save(genre);
    }
}