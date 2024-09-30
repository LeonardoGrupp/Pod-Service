package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import java.util.List;

public interface GenreServiceInterface {

    boolean genreExists(String genreName);
    List<Genre> findAllGenres();
    Genre findGenreByName(String genre);
    Genre findGenreById(long id);
    Genre create(Genre genre);
    Genre update(long id, Genre newInfo);
    String delete(long id);
    void countPlay(Genre genre);
    void addLike(Genre genre);
}
