package com.example.PodMicroservice_CopyAllToNew.controllers;

import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.services.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreControllerTest {
    private GenreService genreServiceMock;
    private GenreController genreController;

    @BeforeEach
    void setUp() {

        genreServiceMock = mock(GenreService.class);
        genreController = new GenreController(genreServiceMock);
    }

    @Test
    void allGenres() {
        List<Genre> genreList = Arrays.asList(new Genre("Rock"), new Genre("EDM"), new Genre("Hip-Hop"));

        when(genreServiceMock.findAllGenres()).thenReturn(genreList);

        ResponseEntity<List<Genre>> result = genreController.allGenres();

        assertEquals(genreList, result.getBody(), "ERROR: Lists was not identical");
        assertEquals("Rock", result.getBody().get(0).getGenre());

        verify(genreServiceMock).findAllGenres();
    }

    @Test
    void createGenreShouldReturnGenre() {
        Genre genre = new Genre("Rock");

        when(genreServiceMock.create(genre)).thenReturn(genre);

        ResponseEntity<Genre> result = genreController.createGenre(genre);

        assertEquals("Rock", result.getBody().getGenre(), "ERROR: Gernes was not identical");

        verify(genreServiceMock).create(genre);
    }

    @Test
    void createGenreNoGenreShouldReturnException() {
        Genre genre = new Genre("");

        when(genreServiceMock.create(genre)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Genre not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            genreController.createGenre(genre);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Genre not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(genreServiceMock).create(genre);
    }

    @Test
    void createGenreWhenGenreAlreadyExistShouldReturnException() {
        Genre newGenre = new Genre("Rock");


        when(genreServiceMock.create(newGenre)).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "ERROR: Genre already exists"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            genreController.createGenre(newGenre);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Genre already exists", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(genreServiceMock).create(newGenre);
    }

    @Test
    void updateGenre() {
        long existingGenreId = 1;
        Genre existingGenre = new Genre("Rock");
        existingGenre.setId(existingGenreId);

        Genre newInfo = new Genre("RNB");

        when(genreServiceMock.update(existingGenreId, newInfo)).thenReturn(newInfo);

        ResponseEntity<Genre> response = genreController.updateGenre(existingGenreId, newInfo);

        assertEquals("RNB", response.getBody().getGenre(), "ERROR: Genres was not identical");

        verify(genreServiceMock).update(existingGenreId, newInfo);
    }

    @Test
    void deleteGenreShouldReturnString() {
        long genreId = 1;

        when(genreServiceMock.delete(genreId)).thenReturn("Genre deleted");

        ResponseEntity<String> response = genreController.deleteGenre(genreId);

        assertEquals("Genre deleted", response.getBody().toString(), "ERROR: Strings was not identical");

        verify(genreServiceMock).delete(genreId);
    }
}