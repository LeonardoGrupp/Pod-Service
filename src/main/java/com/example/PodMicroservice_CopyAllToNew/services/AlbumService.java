package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import com.example.PodMicroservice_CopyAllToNew.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService implements AlbumServiceInterface {
    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public Album getAlbumById(Long albumId) {
        return albumRepository.findById(albumId).orElseThrow(() -> new RuntimeException("Album not found"));
    }

    @Override
    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbum(Long albumId, Album album) {
        Album existingAlbum = getAlbumById(albumId);
        existingAlbum.setName(album.getName());
        existingAlbum.setReleaseDate(album.getReleaseDate());
        return albumRepository.save(existingAlbum);
    }

    @Override
    public void deleteAlbum(Long albumId) {
        albumRepository.deleteById(albumId);
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Boolean checkIfAlbumExistsByName(String name) {
        Album album = albumRepository.findAlbumByName(name);

        return album != null;
    }
}