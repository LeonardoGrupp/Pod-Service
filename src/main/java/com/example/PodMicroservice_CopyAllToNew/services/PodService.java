package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.dto.PodDTO;
import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import com.example.PodMicroservice_CopyAllToNew.repositories.PodRepository;
import com.example.PodMicroservice_CopyAllToNew.vo.Album;
import com.example.PodMicroservice_CopyAllToNew.vo.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PodService implements PodServiceInterface {

    private PodRepository podRepository;
    private RestTemplate restTemplate;
    private GenreService genreService;

    @Autowired
    public PodService(PodRepository podRepository, RestTemplate restTemplate, GenreService genreService) {
        this.podRepository = podRepository;
        this.restTemplate = restTemplate;
        this.genreService = genreService;
    }

    @Override
    public List<Pod> findAllPods() {
        return podRepository.findAll();
    }

    @Override
    public List<Pod> findPodsByArtist(String artistName) {
        List<Pod> podsByArtist = new ArrayList<>();

        for (Pod pod : podRepository.findAll()) {
            for (Artist artist : pod.getArtists()) {
                if (artistName.toLowerCase().equals(artist.getName().toLowerCase())) {
                    podsByArtist.add(pod);
                }
            }
        }

        return podsByArtist;
    }

    @Override
    public List<Pod> findPodsByAlbum(String albumName) {
        List<Pod> podsByAlbum = new ArrayList<>();

        for (Pod pod : podRepository.findAll()) {
            for (Album album : pod.getAlbums()) {
                if (albumName.toLowerCase().equals(album.getName().toLowerCase())) {
                    podsByAlbum.add(pod);
                }
            }
        }

        return podsByAlbum;
    }

    @Override
    public List<Pod> findPodsByGenre(String genreName) {
        List<Pod> podsByGenre = new ArrayList<>();

        for (Pod pod : podRepository.findAll()) {
            for (Genre genre : pod.getGenres()) {
                if (genreName.toLowerCase().equals(genre.getGenre().toLowerCase())) {
                    podsByGenre.add(pod);
                }
            }
        }

        return podsByGenre;
    }

    @Override
    public Pod findPodByUrl(String url) {
        return podRepository.findPodByUrl(url);
    }

    @Override
    public Pod findPodById(long id) {
        Optional<Pod> optionalPod = podRepository.findById(id);

        if (optionalPod.isPresent()) {
            return optionalPod.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Could not find pod with id: " + id);
        }
    }

    @Override
    public Pod createPod(PodDTO podDTO) {
        if (podDTO.getTitle().isEmpty() || podDTO.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Title was not provided");
        }
        if (podDTO.getUrl().isEmpty() || podDTO.getUrl() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod URL was not provided");
        }
        if (podDTO.getReleaseDate().isEmpty() || podDTO.getReleaseDate() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Release date was not provided");
        }
        if (podDTO.getGenreInputs().isEmpty() || podDTO.getGenreInputs() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Genre was not provided");
        }
        if (podDTO.getAlbumInputs().isEmpty() || podDTO.getAlbumInputs() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Album was not provided");
        }
        if (podDTO.getArtistInputs().isEmpty() || podDTO.getArtistInputs() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Artist was not provided");
        }

        podDTO.setType("pod");

        for (String genreName : podDTO.getGenreInputs()) {
            boolean genreExist = genreService.genreExists(genreName);

            if (!genreExist) {
                genreService.create(new Genre(genreName));
            }
        }

        // Check to see if album exist
        for (String albumName : podDTO.getAlbumInputs()) {
            ResponseEntity<Boolean> albumNameResponse = restTemplate.getForEntity("lb://album-service/album/exists/" + albumName, Boolean.class);

            Boolean albumExist = albumNameResponse.getBody();

            if (!albumExist) {
                ResponseEntity<Album> createAlbum = restTemplate.postForEntity("lb://album-service/album/createAlbum", new Album(albumName), Album.class);
            }

        }

        // Check to see if artist exist
        for (String artistName : podDTO.getArtistInputs()) {
            ResponseEntity<Boolean> artistNameResponse = restTemplate.getForEntity("lb://artist-service/artist/exists/" + artistName, Boolean.class);

            Boolean artistExist = artistNameResponse.getBody();

            if (!artistExist) {
                ResponseEntity<Artist> createArtist = restTemplate.postForEntity("lb://artist-service/artist/createArtist", new Artist(artistName), Artist.class);
            }
        }


        return podRepository.save(new Pod(podDTO.getTitle(), podDTO.getUrl(), podDTO.getReleaseDate(), getAllGenres(podDTO), getAllAlbums(podDTO), getAllArtists(podDTO)));
    }

    @Override
    public List<Genre> getAllGenres(PodDTO podDTO) {
        List<Genre> genreList = genreService.findAllGenres();

        List<Genre> genres = new ArrayList<>();

        if (genres != null) {
            for (Genre genre : genreList) {
                for (String genreName : podDTO.getGenreInputs()) {
                    if (genreName.toLowerCase().equals(genre.getGenre().toLowerCase())) {
                        genres.add(genre);
                    }
                }
            }
        }

        return genres;
    }

    @Override
    public List<Album> getAllAlbums(PodDTO podDTO) {
        ResponseEntity<Album[]> allAlbumsArray = restTemplate.getForEntity("lb://album-service/album/getAllAlbums", Album[].class);

        List<Album> albumList = new ArrayList<>();

        if (allAlbumsArray != null) {
            for (Album album : allAlbumsArray.getBody()) {
                for (String albumName : podDTO.getAlbumInputs()) {
                    if (albumName.toLowerCase().equals(album.getName().toLowerCase())) {
                        albumList.add(album);
                    }
                }
            }
        }

        return albumList;
    }

    @Override
    public List<Artist> getAllArtists(PodDTO podDTO) {
        ResponseEntity<Artist[]> allArtistsArray = restTemplate.getForEntity("lb://artist-service/artist/getAllArtists", Artist[].class);
        List<Artist> artistList = new ArrayList<>();
        if (allArtistsArray != null) {
            for (Artist artist : allArtistsArray.getBody()) {
                for (String artistName : podDTO.getArtistInputs()) {
                    if (artistName.toLowerCase().equals(artist.getName().toLowerCase())) {
                        artistList.add(artist);
                    }
                }
            }
        }

        return artistList;
    }

    @Override
    public Pod updatePod(long id, PodDTO newPodInfo) {
        Pod existingPod = findPodById(id);

        if (newPodInfo.getTitle() != null && !newPodInfo.getTitle().isEmpty()) {
            existingPod.setTitle(newPodInfo.getTitle());
        }
        if (newPodInfo.getReleaseDate() != null && !newPodInfo.getReleaseDate().isEmpty()) {
            existingPod.setReleaseDate(newPodInfo.getReleaseDate());
        }
        if (newPodInfo.getAlbumInputs() != null && !newPodInfo.getAlbumInputs().isEmpty()) {

            List<Album> albums = getAllAlbums(newPodInfo);

            existingPod.setAlbums(albums);
        }
        if (newPodInfo.getGenreInputs() != null && !newPodInfo.getGenreInputs().isEmpty()) {

            List<Genre> genres = getAllGenres(newPodInfo);

            existingPod.setGenres(genres);
        }
        if (newPodInfo.getArtistInputs() != null && !newPodInfo.getArtistInputs().isEmpty()) {

            List<Artist> artists = getAllArtists(newPodInfo);

            existingPod.setArtists(artists);
        }

        return podRepository.save(existingPod);
    }

    @Override
    public String deletePod(long id) {
        Pod podToDelete = findPodById(id);

        podRepository.delete(podToDelete);

        return "Pod successfully deleted";
    }

    @Override
    public String playPod(String url) {
        Pod podToPlay = podRepository.findPodByUrl(url);

        if (podToPlay == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Pod with URL not found");
        }

        podToPlay.countPlay();

        List<Genre> genres = podToPlay.getGenres();
        for (Genre genre : genres) {
            genre.countPlay();
        }

        podRepository.save(podToPlay);

        return "Playing " + podToPlay.getType() + ": " + podToPlay.getTitle();
    }

    @Override
    public String likePod(String url) {
        Pod podToLike = podRepository.findPodByUrl(url);

        if (podToLike == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: pod with URL not found");
        }

        podToLike.addLike();

        List<Genre> genres = podToLike.getGenres();
        for (Genre genre : genres) {
            genre.addLike();
        }

        return "Liked pod: " + podToLike.getTitle();
    }

    @Override
    public String disLikePod(String url) {
        Pod podToDisLike = podRepository.findPodByUrl(url);

        if (podToDisLike == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: pod with URL not found");
        }

        podToDisLike.addDisLike();

        return "Disliked " + podToDisLike.getType() + ": " + podToDisLike.getTitle();
    }

    @Override
    public Boolean checkIfPodExistByUrl(String url) {
        Pod pod = podRepository.findPodByUrl(url);

        return pod != null;
    }
}
