package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import com.example.PodMicroservice_CopyAllToNew.entities.Artist;
import com.example.PodMicroservice_CopyAllToNew.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService implements ArtistServiceInterface {

    private ArtistRepository artistRepository;
    private AlbumService albumService;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, AlbumService albumService) {
        this.artistRepository = artistRepository;
        this.albumService = albumService;
    }

    @Override
    public Artist createArtist(Artist artist) {
        if (!validateAlbumIds(artist.getAlbumIds())) {
            throw new IllegalArgumentException("Invalid album IDs");
        }
        return artistRepository.save(artist);
    }

    @Override
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found"));
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist updateArtist(Long id, Artist artist) {
        Artist existingArtist = getArtistById(id);
        existingArtist.setName(artist.getName());
        existingArtist.setAlbumIds(artist.getAlbumIds());
        return artistRepository.save(existingArtist);
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    @Override
    public boolean validateAlbumIds(List<Long> albumIds) {
        for (Long albumId : albumIds) {

            Album album = albumService.getAlbumById(albumId);

            if (album != null && album.getId() != 0 && album.getId() == albumId) {
                continue;
            } else {
                System.out.println("Error validating album ID " + albumId + ": Album is either null, album.getId could be 0 or album.getId() was not equal to albumId");
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean checkIfArtistExistByName(String artistName) {
        Artist artist = artistRepository.findArtistByName(artistName);

        return artist != null;
    }
}
