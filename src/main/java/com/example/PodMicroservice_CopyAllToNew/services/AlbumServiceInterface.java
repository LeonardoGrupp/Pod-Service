package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import java.util.List;

public interface AlbumServiceInterface {
    List<Album> getAllAlbums();
    Album getAlbumByName(String name);
    boolean albumExist(String name);
    Album createAlbum(Album album);
}
