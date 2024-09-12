package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService implements GenreServiceInterface {
    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public boolean genreExists(String genreName) {
        Genre genre = findGenreByName(genreName);

        if (genre != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findGenreByName(String genre) {
        return genreRepository.findGenreByGenre(genre);
    }

    @Override
    public Genre findGenreById(long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if (optionalGenre.isPresent()) {
            return optionalGenre.get();
        } else {
            return null;
        }
    }

    @Override
    public Genre create(Genre genre) {
        if (genre.getGenre() == null || genre.getGenre().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Genre not provided");
        }
        if (genreExists(genre.getGenre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ERROR: Genre already exists");
        }

        return genreRepository.save(genre);
    }

    @Override
    public Genre update(long id, Genre newInfo) {
        Genre existingGenre = findGenreById(id);

        if (existingGenre == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Genre could not be found");
        }

        if (!existingGenre.getGenre().equals(newInfo.getGenre()) && newInfo.getGenre() != null && !newInfo.getGenre().isEmpty()) {
            existingGenre.setGenre(newInfo.getGenre());
        }

        return genreRepository.save(existingGenre);
    }

    @Override
    public String delete(long id) {
        Genre genreToBeDeleted = findGenreById(id);

        if (genreToBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Genre could not be found with id: " + id);
        }

        genreRepository.delete(genreToBeDeleted);

        return "Genre deleted";
    }

    @Override
    public void countPlay(Genre genre) {
        genre.countPlay();

        genreRepository.save(genre);
    }

    @Override
    public void addLike(Genre genre) {
        genre.addLike();

        genreRepository.save(genre);
    }
}