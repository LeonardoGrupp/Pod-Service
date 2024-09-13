package com.example.PodMicroservice_CopyAllToNew.controllers;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import com.example.PodMicroservice_CopyAllToNew.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pod/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/GetAlbum/{albumId}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long albumId) {
        return new ResponseEntity<>(albumService.getAlbumById(albumId), HttpStatus.OK);
    }

    @PostMapping("/createAlbum")
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        return new ResponseEntity<>(albumService.createAlbum(album), HttpStatus.CREATED);
    }

    @PutMapping("/UpdateAlbum/{albumId}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long albumId, @RequestBody Album album) {
        return new ResponseEntity<>(albumService.updateAlbum(albumId, album), HttpStatus.OK);
    }

    @DeleteMapping("/DeleteAlbum/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long albumId) {
        albumService.deleteAlbum(albumId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAllAlbums")
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> albumExist(@PathVariable("name") String name) {
        return ResponseEntity.ok(albumService.checkIfAlbumExistsByName(name));
    }
}