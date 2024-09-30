package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.dto.PodDTO;
import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import com.example.PodMicroservice_CopyAllToNew.entities.Artist;
import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import com.example.PodMicroservice_CopyAllToNew.repositories.PodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class PodServiceTest {
    private PodRepository podRepositoryMock;
    private AlbumService albumService;
    private ArtistService artistService;
    private GenreService genreService;
    private PodService podService;

    @BeforeEach
    void setUp() {
        podRepositoryMock = mock(PodRepository.class);
        albumService = mock(AlbumService.class);
        artistService = mock(ArtistService.class);
        genreService = mock(GenreService.class);
        podService = new PodService(podRepositoryMock, genreService, albumService, artistService);
    }

    @Test
    void findAllPodsShouldReturnList() {
        List<Pod> allPodList = Arrays.asList(
                new Pod("The Wicker Man", "url1001", "2002-03-25"),
                new Pod("Ghost Of the Navigator", "url1002", "2002-03-25"),
                new Pod("Brave New World", "url1003", "2002-03-25"),
                new Pod("Wrathchild", "url1004", "2002-03-25"),
                new Pod("2 Minutes To Midnight", "url1005", "2002-03-25")
        );

        when(podRepositoryMock.findAll()).thenReturn(allPodList);

        List<Pod> response = podService.findAllPods();

        assertEquals(allPodList, response, "ERROR: Pod lists was not identical");
        assertEquals(5, response.size(), "ERROR: Sizes was not identical");
        assertEquals("Brave New World", response.get(2).getTitle(), "ERROR: Titles was not identical");

        verify(podRepositoryMock).findAll();
    }

    @Test
    void findPodsByArtistShouldReturnList() {
        Artist artist = new Artist("Dire Straits");
        Album album = new Album("Alchemy");
        Genre genre = new Genre("Rock");
        List<Pod> podList = Arrays.asList(
                new Pod("Once Upon A Time in the West", "'url1019", "1984-03-16", List.of(genre), List.of(album), List.of(artist)),
                new Pod("Romeo And Juliet", "'url1020", "1984-03-16", List.of(genre), List.of(album), List.of(artist)),
                new Pod("Expresso Love", "'url1021", "1984-03-16", List.of(genre), List.of(album), List.of(artist))
        );

        when(podRepositoryMock.findAll()).thenReturn(podList);

        List<Pod> response = podService.findPodsByArtist("Dire Straits");

        assertEquals(podList, response, "ERROR: Pod lists was not identical");
        assertEquals(3, response.size(), "ERROR: Sizes was not identical");
        assertEquals("Romeo And Juliet", response.get(1).getTitle(), "ERROR: Artists was not identical");

        verify(podRepositoryMock).findAll();
    }

    @Test
    void findPodsByAlbumShouldReturnList() {
        Artist avicii = new Artist("Lady Gaga");
        Album stories = new Album("The Chromatica Ball");
        Genre genre = new Genre("Pop");
        List<Pod> albumPodList = Arrays.asList(
                new Pod("Bad Romance", "url1028", "2022-07-21", List.of(genre), List.of(stories), List.of(avicii)),
                new Pod("Just Dance", "url1029", "2022-07-21", List.of(genre), List.of(stories), List.of(avicii))
        );

        when(podRepositoryMock.findAll()).thenReturn(albumPodList);

        List<Pod> response = podService.findPodsByAlbum("The Chromatica Ball");

        assertEquals(albumPodList, response, "ERROR: Pod lists was not identical");
        assertEquals(2, response.size(), "ERROR: Sizes was not identical");
        assertEquals(stories, response.get(0).getAlbums().get(0), "ERROR: Albums was not identical");

        verify(podRepositoryMock).findAll();
    }

    @Test
    void findPodsByGenreShouldReturnList() {
        Artist avicii = new Artist("Ramones");
        Album stories = new Album("Live At the Roxy");
        Genre genre = new Genre("Punk");
        List<Pod> genrePodList = Arrays.asList(
                new Pod("Loud Mouth", "url1049", "1976-08-12", List.of(genre), List.of(stories), List.of(avicii)),
                new Pod("Beat On the Brat", "url1050", "1976-08-12", List.of(genre), List.of(stories), List.of(avicii))
        );

        when(podRepositoryMock.findAll()).thenReturn(genrePodList);

        List<Pod> response = podService.findPodsByGenre("Punk");

        assertEquals(genrePodList, response, "ERROR: Pod lists was not identical");
        assertEquals(2, response.size(), "ERROR: Sizes was not identical");
        assertEquals(genre, response.get(0).getGenres().get(0), "ERROR: Genres was not identical");

        verify(podRepositoryMock).findAll();
    }

    @Test
    void findPodByUrlShouldReturnPod() {
        Pod pod = new Pod("titel", "url", "releasedate");

        when(podRepositoryMock.findPodByUrl("url")).thenReturn(pod);

        Pod response = podService.findPodByUrl("url");

        assertEquals(pod, response, "ERROR: Pod was not identical");

        verify(podRepositoryMock).findPodByUrl("url");
    }

    @Test
    void findPodByIdShouldReturnPod() {
        Pod pod = new Pod("Clint Eastwood", "url1", "2001-03-05");
        pod.setId(1);

        long podId = 1;

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(pod));

        Pod response = podService.findPodById(podId);

        assertEquals(podId, response.getId(), "ERROR: IDs was not identical");
        assertEquals("Clint Eastwood", response.getTitle(), "ERROR: Titles was not identical");

        verify(podRepositoryMock).findById(podId);
    }

    @Test
    void findPodByIdShouldReturnException() {
        long podId = 1;

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.empty());

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.findPodById(podId);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Could not find pod with id: " + podId, response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podRepositoryMock).findById(podId);
    }

    @Test
    void createPodShouldReturnPod() {
        List<String> albumInputs = Arrays.asList("Album1", "Album2");
        List<String> artistInputs = Arrays.asList("Artist1", "Artist2");
        List<String> genreInputs = Arrays.asList("Pop", "RNB");
        PodDTO podDTO = new PodDTO("PodTitle", "url1", "2024-09-02", genreInputs, albumInputs, artistInputs);

        List<Album> albums = Arrays.asList(new Album("Album1"), new Album("Album2"));
        List<Artist> artists = Arrays.asList(new Artist("Artist1"), new Artist("Artist2"));
        List<Genre> genres = Arrays.asList(new Genre("Pop"), new Genre("RNB"));
        Pod pod = new Pod("PodTitle", "url1", "2024-09-02", genres, albums, artists);

        Pod savedPod = podRepositoryMock.save(pod);

        when(podRepositoryMock.save(pod)).thenReturn(pod);

        Pod response = podService.createPod(podDTO);

        assertEquals(savedPod, response, "ERROR: Responses was not the same");

        verify(podRepositoryMock).save(pod);
    }


    @Test
    void createPodNoTitleShouldReturnException() {
        List<String> albumStringList = List.of("Good Girl Gone Bad");
        List<String> artistStringList = Arrays.asList("Rihanna", "Jay-Z");
        List<String> genreStringList = Arrays.asList("Pop", "RNB");
        PodDTO podDTO = new PodDTO("", "url", "release", albumStringList, genreStringList, artistStringList);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.createPod(podDTO);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod Title was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podRepositoryMock, never()).save(any(Pod.class));
    }

    @Test
    void createPodNoUrlShouldReturnException() {
        PodDTO podDTO = new PodDTO("title", "", "release");
        List<String> albumStringList = List.of("Good Girl Gone Bad");
        List<String> artistStringList = Arrays.asList("Rihanna", "Jay-Z");
        List<String> genreStringList = Arrays.asList("Pop", "RNB");
        podDTO.setAlbumInputs(albumStringList);
        podDTO.setArtistInputs(artistStringList);
        podDTO.setGenreInputs(genreStringList);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.createPod(podDTO);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod URL was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podRepositoryMock, never()).save(any(Pod.class));
    }

    @Test
    void createPodNoReleaseDateShouldReturnException() {
        PodDTO podDTO = new PodDTO("title", "url", "");
        List<String> albumStringList = List.of("Good Girl Gone Bad");
        List<String> artistStringList = Arrays.asList("Rihanna", "Jay-Z");
        List<String> genreStringList = Arrays.asList("Pop", "RNB");
        podDTO.setAlbumInputs(albumStringList);
        podDTO.setArtistInputs(artistStringList);
        podDTO.setGenreInputs(genreStringList);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.createPod(podDTO);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod Release date was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podRepositoryMock, never()).save(any(Pod.class));
    }

    @Test
    void createMusicNoGenreShouldReturnException() {
        PodDTO podDTO = new PodDTO("title", "url", "release");
        List<String> albumStringList = List.of("Good Girl Gone Bad");
        List<String> artistStringList = Arrays.asList("Rihanna", "Jay-Z");
        podDTO.setAlbumInputs(albumStringList);
        podDTO.setArtistInputs(artistStringList);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.createPod(podDTO);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod Genre was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podRepositoryMock, never()).save(any(Pod.class));
    }

    @Test
    void createPodNoAlbumShouldReturnException() {
        PodDTO podDTO = new PodDTO("title", "url", "release");
        List<String> artistStringList = Arrays.asList("Rihanna", "Jay-Z");
        List<String> genreStringList = Arrays.asList("Pop", "RNB");
        podDTO.setArtistInputs(artistStringList);
        podDTO.setGenreInputs(genreStringList);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.createPod(podDTO);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod Album was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podRepositoryMock, never()).save(any(Pod.class));
    }

    @Test
    void createPodNoArtistShouldReturnException() {
        PodDTO podDTO = new PodDTO("title", "url", "release");
        List<String> albumStringList = List.of("Good Girl Gone Bad");
        List<String> genreStringList = Arrays.asList("Pop", "RNB");
        podDTO.setAlbumInputs(albumStringList);
        podDTO.setGenreInputs(genreStringList);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.createPod(podDTO);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod Artist was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podRepositoryMock, never()).save(any(Pod.class));
    }

    @Test
    void getAllGenresShouldReturnList() {
        List<String> genreStrings = Arrays.asList("Pop", "RNB");
        List<String> albumStrings = List.of("Album");
        List<String> artistStrings = List.of("Artist");

        PodDTO podDTO = new PodDTO("titel", "url", "release", genreStrings, albumStrings, artistStrings);

        List<Genre> genres = Arrays.asList(new Genre("Pop"), new Genre("RNB"));

        when(genreService.findAllGenres()).thenReturn(genres);

        List<Genre> responseList = podService.getAllGenres(podDTO);

        assertEquals(genres, responseList, "ERROR: Lists was not identical");

        verify(genreService).findAllGenres();
    }

    @Test
    void getAllAlbumsShouldReturnList() {
        List<String> genreStrings = List.of("Pop");
        List<String> albumStrings = Arrays.asList("Good Girl Gone Bad", "Come Clarity");
        List<String> artistStrings = List.of("Artist");

        PodDTO podDTO = new PodDTO("titel", "url", "release", genreStrings, albumStrings, artistStrings);

        List<Album> albums = Arrays.asList(new Album("Good Girl Gone Bad"), new Album("Come Clarity"));

        when(albumService.getAllAlbums()).thenReturn(albums);

        List<Album> responseList = podService.getAllAlbums(podDTO);

        assertEquals(albums, responseList, "ERROR: Lists was not identical");

        verify(albumService).getAllAlbums();
    }

    @Test
    void getAllArtistsShouldReturnList() {
        List<String> genreStrings = List.of("Pop");
        List<String> albumStrings = List.of("Good Girl Gone Bad");
        List<String> artistStrings = Arrays.asList("Rihanna", "Jay-Z");

        PodDTO podDTO = new PodDTO("titel", "url", "release", genreStrings, albumStrings, artistStrings);

        List<Artist> artists = Arrays.asList(new Artist("Rihanna"), new Artist("Jay-Z"));

        when(artistService.getAllArtists()).thenReturn(artists);

        List<Artist> responseList = podService.getAllArtists(podDTO);

        assertEquals(artists, responseList, "ERROR: Lists was not identical");

        verify(artistService).getAllArtists();
    }

    @Test
    void updateMusicShouldReturnMusic() {
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02");
        existingPod.setId(podId);

        PodDTO newInfo = new PodDTO("Waiting For Love", "url2", "2011-11-02");

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(existingPod);

        Pod response = podService.updatePod(podId, newInfo);

        assertEquals("Waiting For Love", response.getTitle(), "ERROR: Titles was not identical");
        assertEquals("2011-11-02", response.getReleaseDate(), "ERROR: ReleaseDates was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).save(existingPod);
    }

    @Test
    void updatePodInvalidIdShouldReturnException() {
        long podId = 1;
        PodDTO newInfo = new PodDTO("title", "url1", "2022-02-02");

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.empty());

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.updatePod(podId, newInfo);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Could not find pod with id: " + podId, response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes not identical");

        verify(podRepositoryMock).findById(podId);
    }

    @Test
    void updatePodOnlyTypeShouldReturnPod() {
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02");
        existingPod.setId(podId);

        PodDTO newInfo = new PodDTO("", "", "");

        Pod updatedMusic = new Pod("Umbrella", "url1", "2002-02-02");

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(updatedMusic);

        Pod response = podService.updatePod(podId, newInfo);

        assertEquals("pod", response.getType(), "ERROR: Types was not identical");
        assertEquals("url1", response.getUrl(), "ERROR: URLs was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).save(existingPod);
    }

    @Test
    void updatePodOnlyTitleShouldReturnPod() {
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02");
        existingPod.setId(podId);

        PodDTO newInfo = new PodDTO("Cheers", "", "");

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(existingPod);

        Pod response = podService.updatePod(podId, newInfo);

        assertEquals("Cheers", response.getTitle(), "ERROR: Titles was not identical");
        assertEquals("url1", response.getUrl(), "ERROR: URLs was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).save(existingPod);
    }

    @Test
    void updatePodOnlyReleaseDateShouldReturnPod() {
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02");
        existingPod.setId(podId);

        PodDTO newInfo = new PodDTO("", "", "2024-09-06");

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(existingPod);

        Pod response = podService.updatePod(podId, newInfo);

        assertEquals("2024-09-06", response.getReleaseDate(), "ERROR: Release dates was not identical");
        assertEquals("url1", response.getUrl(), "ERROR: URLs was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).save(existingPod);
    }

    @Test
    void updatePodOnlyAlbumInputsShouldReturnPod() {
        List<Album> albumList = List.of(new Album("album"));
        List<Artist> artistList = List.of(new Artist("artist"));
        List<Genre> genreList = List.of(new Genre("Pop"));
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02", genreList, albumList, artistList);
        existingPod.setId(podId);

        List<String> albumStringInputs = List.of("new album");
        List<String> artistStringInputs = Collections.emptyList();
        List<String> genreStringInputs = List.of("new genre");

        PodDTO newInfo = new PodDTO("", "", "2024-09-06", genreStringInputs, albumStringInputs, artistStringInputs);

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(existingPod);

        Pod response = podService.updatePod(podId, newInfo);

        assertEquals("2024-09-06", response.getReleaseDate(), "ERROR: Release dates was not identical");
        assertEquals("url1", response.getUrl(), "ERROR: URLs was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).save(existingPod);
    }
    @Test
    void updatePodOnlyGenreShouldReturnPod() {
        List<Album> albumList = List.of(new Album("album"));
        List<Artist> artistList = List.of(new Artist("artist"));
        List<Genre> genreList = List.of(new Genre("Pop"));
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02", genreList, albumList, artistList);
        existingPod.setId(podId);

        List<String> albumStringInputs = Collections.emptyList();
        List<String> artistStringInputs = Collections.emptyList();
        List<String> genreStringInputs = Arrays.asList("Pop", "RNB");
        PodDTO newInfo = new PodDTO("", "", "", genreStringInputs, albumStringInputs, artistStringInputs);

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(existingPod);

        Pod response = podService.updatePod(podId, newInfo);

        assertEquals("Umbrella", response.getTitle(), "ERROR: Titles was not identical");
        assertEquals("artist", response.getArtists().get(0).getName(), "ERROR: Genres was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).save(existingPod);
    }

    @Test
    void updatePodOnlyArtistInputsShouldReturnPod() {
        List<Album> albumList = List.of(new Album("album"));
        List<Artist> artistList = List.of(new Artist("artist"));
        List<Genre> genreList = List.of(new Genre("genre"));
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02", genreList, albumList, artistList);
        existingPod.setId(podId);

        List<String> albumStringInputs = Collections.emptyList();
        List<String> genreStringInputs = Collections.emptyList();
        List<String> artistStringInputs = List.of("new artist");

        PodDTO newInfo = new PodDTO("", "", "2024-09-06", genreStringInputs, albumStringInputs, artistStringInputs);

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(existingPod);

        Pod response = podService.updatePod(podId, newInfo);

        assertEquals("2024-09-06", response.getReleaseDate(), "ERROR: Release dates was not identical");
        assertEquals("genre", response.getGenres().get(0).getGenre(), "ERROR: Genres was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).save(existingPod);
    }

    @Test
    void deletePodShouldReturnString() {
        long podId = 1;
        Pod podToBeDeleted = new Pod("Umbrella", "url1", "2001-11-11");
        podToBeDeleted.setId(podId);

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(podToBeDeleted));

        String response = podService.deletePod(podId);

        assertEquals("Pod successfully deleted", response, "ERROR: Strings was not identical");

        verify(podRepositoryMock).findById(podId);
        verify(podRepositoryMock).delete(podToBeDeleted);
    }

    @Test
    void playPodShouldReturnString() {
        String url = "url";
        Pod pod = new Pod("The Real Slim Shady", url, "release date");
        List<Genre> genreList = Arrays.asList(new Genre("Rock"), new Genre("Pop"));
        pod.setGenres(genreList);

        when(podRepositoryMock.findPodByUrl(url)).thenReturn(pod);

        String result = podService.playPod(url);

        assertEquals("Playing " + pod.getType() + ": " + pod.getTitle(), result, "ERROR: Strings was not identical");

        verify(podRepositoryMock).findPodByUrl(url);
    }

    @Test
    void playPodShouldReturnException() {
        String nonExistingURL = "url";

        when(podRepositoryMock.findPodByUrl(nonExistingURL)).thenReturn(null);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.playPod(nonExistingURL);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod with URL not found", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");
    }

    @Test
    void likePodShouldReturnString() {
        String url = "url";
        Pod podToLike = new Pod("The Real Slim Shady", url, "releasedate");
        podToLike.setLikes(0);
        List<Genre> genreLists = Arrays.asList(new Genre("Rock"), new Genre("Pop"));
        podToLike.setGenres(genreLists);

        when(podRepositoryMock.findPodByUrl(url)).thenReturn(podToLike);

        String result = podService.likePod(url);

        assertEquals(1, podToLike.getLikes(), "ERROR: Total likes was not identical");
        assertEquals("Liked pod: " + podToLike.getTitle(), result, "ERROR: Strings was not identical");
    }

    @Test
    void likePodIdNotFoundShouldReturnException() {
        String nonExistingURL = "url";

        when(podRepositoryMock.findPodByUrl(nonExistingURL)).thenReturn(null);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.likePod(nonExistingURL);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: pod with URL not found", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");
    }

    @Test
    void dislikePodShouldReturnString() {
        String url = "url";
        Pod podToDislike = new Pod("The Real Slim Shady", url, "releasedate");
        podToDislike.setDisLikes(0);

        when(podRepositoryMock.findPodByUrl(url)).thenReturn(podToDislike);

        String result = podService.disLikePod(url);

        assertEquals(1, podToDislike.getDisLikes(), "ERROR: Total likes was not identical");
        assertEquals("Disliked " + podToDislike.getType() + ": " + podToDislike.getTitle(), result, "ERROR: Strings was not identical");
    }

    @Test
    void dislikePodIdNotFoundShouldReturnException() {
        String nonExistingURL = "url";

        when(podRepositoryMock.findPodByUrl(nonExistingURL)).thenReturn(null);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podService.disLikePod(nonExistingURL);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: pod with URL not found", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");
    }

    @Test
    void checkIfPodExistByUrlShouldReturnTrue() {
        String url = "url";
        Pod pod = new Pod("title", "url", "release");

        when(podRepositoryMock.findPodByUrl(url)).thenReturn(pod);

        Boolean response = podService.checkIfPodExistByUrl(url);

        assertTrue(response, "ERROR: Response was false");

        verify(podRepositoryMock).findPodByUrl(url);
    }

    @Test
    void checkIfPodExistByUrlShouldReturnFalse() {
        String url = "url";

        when(podRepositoryMock.findPodByUrl(url)).thenReturn(null);

        Boolean response = podService.checkIfPodExistByUrl(url);

        assertFalse(response, "ERROR: Response was true");

        verify(podRepositoryMock).findPodByUrl(url);
    }

}