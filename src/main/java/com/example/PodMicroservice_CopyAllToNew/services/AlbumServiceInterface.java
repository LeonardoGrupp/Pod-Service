package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;

import java.util.List;

public interface AlbumServiceInterface {
    Album getAlbumById(Long albumId);
    Album createAlbum(Album album);
    Album updateAlbum(Long albumId, Album album);
    void deleteAlbum(Long albumId);
    Boolean checkIfAlbumExistsByName(String name);
    List<Album> getAllAlbums();

}
