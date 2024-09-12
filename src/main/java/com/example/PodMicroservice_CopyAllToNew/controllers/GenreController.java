package com.example.PodMicroservice_CopyAllToNew.controllers;

import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Genre>> allGenres() {
        return ResponseEntity.ok(genreService.findAllGenres());
    }

    @PostMapping("/create")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.create(genre));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable("id") long id, @RequestBody Genre newInfo) {
        return ResponseEntity.ok(genreService.update(id, newInfo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable("id") long id) {
        return ResponseEntity.ok(genreService.delete(id));
    }
}
