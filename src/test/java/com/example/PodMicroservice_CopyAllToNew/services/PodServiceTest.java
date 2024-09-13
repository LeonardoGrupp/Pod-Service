package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.dto.PodDTO;
import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import com.example.PodMicroservice_CopyAllToNew.repositories.PodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class PodServiceTest {

    private PodRepository podRepositoryMock;
    private PodService podService;
    private RestTemplate restTemplate;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        podRepositoryMock = mock(PodRepository.class);
        restTemplate = mock(RestTemplate.class);
        genreService = mock(GenreService.class);
        podService = new PodService(podRepositoryMock, restTemplate, genreService);
    }

    @Test
    void findAllPodShouldReturnList() {
        List<Pod> allPodList = Arrays.asList(
                new Pod("The Way I Am", "url1", "1999-01-01"), // The Marshall Mathers LP
                new Pod("Slim Shady", "url2", "1998-02-03"), // The Slim Shady LP
                new Pod("Eminem låt 3", "url3", "1997-07-07"), // The Marshall Mathers LP
                new Pod("Diamonds", "url4", "2005-06-05"), // Loud
                new Pod("Waiting For Love", "url5", "2011-11-02"), // Stories
                new Pod("Talk To Myself", "url6", "2013-12-31"), // Stories
                new Pod("Hey Brother", "url7", "2010-02-05") // True
        );

        when(podRepositoryMock.findAll()).thenReturn(allPodList);

        List<Pod> response = podService.findAllPods();

        assertEquals(allPodList, response, "ERROR: Pod lists was not identical");
        assertEquals(7, response.size(), "ERROR: Sizes was not identical");
        assertEquals("The Way I Am", response.get(0).getTitle(), "ERROR: Titles was not identical");

        verify(podRepositoryMock).findAll();
    }

    @Test
    void findPodByArtistShouldReturnList() {
        Artist eminem = new Artist("Eminem");
        Album album = new Album("Eminem album");
        Genre genre = new Genre("Hip-Hop");
        List<Pod> eminemPodList = Arrays.asList(
                new Pod("The Way I Am", "url1", "1999-01-01", Arrays.asList(genre), Arrays.asList(album), Arrays.asList(eminem)),
                new Pod("Slim Shady", "url2", "1998-02-03", Arrays.asList(genre), Arrays.asList(album), Arrays.asList(eminem)),
                new Pod("Eminem låt 3", "url3", "1997-07-07", Arrays.asList(genre), Arrays.asList(album), Arrays.asList(eminem))
        );

        when(podRepositoryMock.findAll()).thenReturn(eminemPodList);

        List<Pod> response = podService.findPodsByArtist("Eminem");

        assertEquals(eminemPodList, response, "ERROR: Pod lists was not identical");
        assertEquals(3, response.size(), "ERROR: Sizes was not identical");
        assertEquals("Slim Shady", response.get(1).getTitle(), "ERROR: Artists was not identical");

        verify(podRepositoryMock).findAll();
    }

    @Test
    void findPodByAlbumShouldReturnList() {
        Artist avicii = new Artist("Avicii");
        Album stories = new Album("Stories");
        Genre genre = new Genre("EDM");
        List<Pod> albumPodList = Arrays.asList(
                new Pod("Waiting For Love", "url1", "2011-11-02", Arrays.asList(genre), Arrays.asList(stories), Arrays.asList(avicii)),
                new Pod("Talk To Myself", "url2", "2013-12-31", Arrays.asList(genre), Arrays.asList(stories), Arrays.asList(avicii))
        );

        when(podRepositoryMock.findAll()).thenReturn(albumPodList);

        List<Pod> response = podService.findPodsByAlbum("Stories");

        assertEquals(albumPodList, response, "ERROR: Pod lists was not identical");
        assertEquals(2, response.size(), "ERROR: Sizes was not identical");
        assertEquals(stories, response.get(0).getAlbums().get(0), "ERROR: Albums was not identical");

        verify(podRepositoryMock).findAll();
    }

    @Test
    void findPodByGenreShouldReturnList() {
        Artist avicii = new Artist("Avicii");
        Album stories = new Album("Stories");
        Genre genre = new Genre("EDM");
        List<Pod> genrePodList = Arrays.asList(
                new Pod("Waiting For Love", "url1", "2011-11-02", Arrays.asList(genre), Arrays.asList(stories), Arrays.asList(avicii)),
                new Pod("Talk To Myself", "url2", "2013-12-31", Arrays.asList(genre), Arrays.asList(stories), Arrays.asList(avicii))
        );

        when(podRepositoryMock.findAll()).thenReturn(genrePodList);

        List<Pod> response = podService.findPodsByGenre("EDM");

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
        // Arrange: Prepare mock data
        List<String> albumInputs = Arrays.asList("Album1", "Album2");
        List<String> artistInputs = Arrays.asList("Artist1", "Artist2");
        List<String> genreInputs = Arrays.asList("Pop", "RNB");
        PodDTO podDTO = new PodDTO("SongTitle", "url1", "2024-09-02", genreInputs, albumInputs, artistInputs);

        // Mock album existence check (returns true)
        ResponseEntity<Boolean> albumExistsResponse = new ResponseEntity<>(true, HttpStatus.OK);
        doReturn(albumExistsResponse).when(restTemplate).getForEntity(contains("exists"), eq(Boolean.class));

        // Mock artist existence check (returns true)
        ResponseEntity<Boolean> artistExistsResponse = new ResponseEntity<>(true, HttpStatus.OK);
        doReturn(artistExistsResponse).when(restTemplate).getForEntity(contains("exists"), eq(Boolean.class));

        // Mock the list of albums returned by the album-service
        Album[] albums = { new Album("Album1"), new Album("Album2") };
        ResponseEntity<Album[]> albumListResponse = new ResponseEntity<>(albums, HttpStatus.OK);
        doReturn(albumListResponse).when(restTemplate).getForEntity(contains("getAllAlbums"), eq(Album[].class));

        // Mock the list of artists returned by the artist-service
        Artist[] artists = { new Artist("Artist1"), new Artist("Artist2") };
        ResponseEntity<Artist[]> artistListResponse = new ResponseEntity<>(artists, HttpStatus.OK);
        doReturn(artistListResponse).when(restTemplate).getForEntity(contains("getAllArtists"), eq(Artist[].class));

        // Mock the list of genres returned by the genreService
        List<Genre> genres = Arrays.asList(new Genre("Pop"), new Genre("RNB"));
        when(genreService.findAllGenres()).thenReturn(genres);


        // Mock the repository save
        Pod savedPod = new Pod("SongTitle", "url1", "2024-09-02", genres, Arrays.asList(albums), Arrays.asList(artists));
        when(podRepositoryMock.save(any(Pod.class))).thenReturn(savedPod);

        // Act: Call the service method
        Pod result = podService.createPod(podDTO);

        // Assert: Verify that the result matches the expected saved pod
        assertEquals(podDTO.getTitle(), result.getTitle(), "ERROR: Titles was not identical");
        assertEquals(podDTO.getReleaseDate(), result.getReleaseDate(), "ERROR: Release dates was not identical");
        assertEquals(podDTO.getGenreInputs().get(0), result.getGenres().get(0).getGenre(), "ERROR: Genres was not identical");
        assertEquals(2, result.getAlbums().size(), "ERROR: Album list sizes was not identical");
        assertEquals(2, result.getArtists().size(), "ERROR: Artist list sizes was not identical");

        // Verify the spy was used and calls were made
        verify(restTemplate, times(4)).getForEntity(contains("exists"), eq(Boolean.class));
        verify(restTemplate, times(1)).getForEntity(contains("getAllAlbums"), eq(Album[].class));
        verify(restTemplate, times(1)).getForEntity(contains("getAllArtists"), eq(Artist[].class));
        verify(genreService).findAllGenres();
    }

    @Test
    void createPodArtistAndAlbumDontExistShouldReturnPod() {
        List<String> albumInputs = Arrays.asList("Album1", "Album2");
        List<String> artistInputs = Arrays.asList("Artist1", "Artist2");
        List<String> genreInputs = Arrays.asList("Pop", "RNB");
        PodDTO podDTO = new PodDTO("SongTitle", "url1", "2024-09-02", genreInputs, albumInputs, artistInputs);

        // Mock album existence check (returns true)
        ResponseEntity<Boolean> albumExistsResponse = new ResponseEntity<>(true, HttpStatus.OK);
        doReturn(albumExistsResponse).when(restTemplate).getForEntity(contains("exists"), eq(Boolean.class));

        // Mock artist existence check (returns false for both artists)
        ResponseEntity<Boolean> artistExistsResponse = new ResponseEntity<>(false, HttpStatus.OK);
        doReturn(artistExistsResponse).when(restTemplate).getForEntity(contains("exists"), eq(Boolean.class));

        // Mock the list of albums returned by the album-service
        ResponseEntity<Album> createAlbumResponse1 = new ResponseEntity<>(new Album("Album1"), HttpStatus.OK);
        ResponseEntity<Album> createAlbumResponse2 = new ResponseEntity<>(new Album("Album2"), HttpStatus.OK);
        doReturn(createAlbumResponse1).when(restTemplate).postForEntity(contains("createAlbum"), any(Album.class), eq(Album.class));
        doReturn(createAlbumResponse2).when(restTemplate).postForEntity(contains("createAlbum"), any(Album.class), eq(Album.class));


        // Mock artist creation when artist does not exist
        ResponseEntity<Artist> createArtistResponse1 = new ResponseEntity<>(new Artist("Artist1"), HttpStatus.OK);
        ResponseEntity<Artist> createArtistResponse2 = new ResponseEntity<>(new Artist("Artist2"), HttpStatus.OK);
        doReturn(createArtistResponse1).when(restTemplate).postForEntity(contains("createArtist"), any(Artist.class), eq(Artist.class));
        doReturn(createArtistResponse2).when(restTemplate).postForEntity(contains("createArtist"), any(Artist.class), eq(Artist.class));

        // Mock the list of genres returned by the genreService
        List<Genre> genres = Arrays.asList(new Genre("Pop"), new Genre("RNB"));
        when(genreService.findAllGenres()).thenReturn(genres);

        // Mock the repository save
        Pod savedPod = new Pod("SongTitle", "url1", "2024-09-02", genres, Arrays.asList(new Album("Album1"), new Album("Album2")), Arrays.asList(new Artist("Artist1"), new Artist("Artist2")));
        when(podRepositoryMock.save(any(Pod.class))).thenReturn(savedPod);

        // Act: Call the service method
        Pod result = podService.createPod(podDTO);

        // Assert: Verify that the result matches the expected saved pod
        assertEquals(podDTO.getTitle(), result.getTitle(), "ERROR: Titles was not identical");
        assertEquals(podDTO.getReleaseDate(), result.getReleaseDate(), "ERROR: Release dates was not identical");
        assertEquals(2, result.getAlbums().size(), "ERROR: Album list sizes was not identical");
        assertEquals(2, result.getArtists().size(), "ERROR: Artist list sizes was not identical");

        // Verify that the artist existence check was made and the creation calls were triggered
        verify(restTemplate, times(4)).getForEntity(contains("exists"), eq(Boolean.class));
        verify(restTemplate, times(2)).postForEntity(contains("createAlbum"), any(Album.class), eq(Album.class));
        verify(restTemplate, times(2)).postForEntity(contains("createArtist"), any(Artist.class), eq(Artist.class));
        verify(genreService).findAllGenres();
    }

    @Test
    void createPodNoTitleShouldReturnException() {
        List<String> albumStringList = Arrays.asList("Good Girl Gone Bad");
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
        List<String> albumStringList = Arrays.asList("Good Girl Gone Bad");
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
        List<String> albumStringList = Arrays.asList("Good Girl Gone Bad");
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
    void createPodNoGenreShouldReturnException() {
        PodDTO podDTO = new PodDTO("title", "url", "release");
        List<String> albumStringList = Arrays.asList("Good Girl Gone Bad");
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
        List<String> albumStringList = Arrays.asList("Good Girl Gone Bad");
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
    void updatePodShouldReturnPod() {
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

        Pod updatedPod = new Pod("Umbrella", "url1", "2002-02-02");

        when(podRepositoryMock.findById(podId)).thenReturn(Optional.of(existingPod));
        when(podRepositoryMock.save(existingPod)).thenReturn(updatedPod);

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
        List<Album> albumList = Arrays.asList(new Album("album"));
        List<Artist> artistList = Arrays.asList(new Artist("artist"));
        List<Genre> genreList = Arrays.asList(new Genre("Pop"));
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02", genreList, albumList, artistList);
        existingPod.setId(podId);

        List<String> albumStringInputs = Arrays.asList("new album");
        List<String> artistStringInputs = Collections.emptyList();
        List<String> genreStringInputs = Arrays.asList("new genre");

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
        List<Album> albumList = Arrays.asList(new Album("album"));
        List<Artist> artistList = Arrays.asList(new Artist("artist"));
        List<Genre> genreList = Arrays.asList(new Genre("Pop"));
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
        List<Album> albumList = Arrays.asList(new Album("album"));
        List<Artist> artistList = Arrays.asList(new Artist("artist"));
        List<Genre> genreList = Arrays.asList(new Genre("genre"));
        long podId = 1;
        Pod existingPod = new Pod("Umbrella", "url1", "2002-02-02", genreList, albumList, artistList);
        existingPod.setId(podId);

        List<String> albumStringInputs = Collections.emptyList();
        List<String> genreStringInputs = Collections.emptyList();
        List<String> artistStringInputs = Arrays.asList("new artist");

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
        Pod pod = new Pod("The Real Slim Shady", url, "releasdate");
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
    void disLikePodShouldReturnString() {
        String url = "url";
        Pod podToDisLike = new Pod("The Real Slim Shady", url, "releasedate");
        podToDisLike.setDisLikes(0);

        when(podRepositoryMock.findPodByUrl(url)).thenReturn(podToDisLike);

        String result = podService.disLikePod(url);

        assertEquals(1, podToDisLike.getDisLikes(), "ERROR: Total likes was not identical");
        assertEquals("Disliked " + podToDisLike.getType() + ": " + podToDisLike.getTitle(), result, "ERROR: Strings was not identical");
    }

    @Test
    void disLikePodIdNotFoundShouldReturnException() {
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