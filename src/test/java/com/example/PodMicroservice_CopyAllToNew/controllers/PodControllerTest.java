package com.example.PodMicroservice_CopyAllToNew.controllers;

import com.example.PodMicroservice_CopyAllToNew.dto.PodDTO;
import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import com.example.PodMicroservice_CopyAllToNew.vo.*;
import com.example.PodMicroservice_CopyAllToNew.services.PodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PodControllerTest {
    private PodController podController;
    private PodService podServiceMock;

    @BeforeEach
    void setUp() {
        podServiceMock = mock(PodService.class);
        podController = new PodController(podServiceMock);
    }

    @Test
    void getAllPodShouldReturnList() {
        List<Pod> allPods = Arrays.asList(
                new Pod("title1", "url1", "releasedate1"),
                new Pod("title2", "url2", "release2"),
                new Pod("title3", "url3", "release3"),
                new Pod("title4", "url4", "release4")
        );

        when(podServiceMock.findAllPods()).thenReturn(allPods);

        ResponseEntity<List<Pod>> response = podController.getAllPods();

        assertEquals(allPods, response.getBody(), "ERROR: Lists was not identical");
        assertEquals(allPods.size(), response.getBody().size(), "ERROR: Sizes was not identical");

        verify(podServiceMock).findAllPods();
    }

    @Test
    void getAllPodsForArtistShouldReturnList() {
        List<Album> albumList = Arrays.asList(new Album("The Marshall Mathers LP"));
        List<Artist> artistList = Arrays.asList(new Artist("Eminem"));
        List<Genre> genreList = Arrays.asList(new Genre("Hip-Hop"));
        List<Pod> artistPods = Arrays.asList(
                new Pod("title1", "url1", "releasedate1", genreList, albumList, artistList),
                new Pod("title2", "url2", "releasedate2", genreList, albumList, artistList),
                new Pod("title3", "url3", "releasedate3", genreList, albumList, artistList),
                new Pod("title4", "url4", "releasedate4", genreList, albumList, artistList)
        );

        when(podServiceMock.findPodsByArtist("Eminem")).thenReturn(artistPods);

        ResponseEntity<List<Pod>> response = podController.getAllPodsForArtist("Eminem");

        assertEquals(artistPods, response.getBody(), "ERROR: Lists was not identical");
        assertEquals(artistPods.get(0).getArtists().get(0), response.getBody().get(0).getArtists().get(0), "ERROR: Artists was not identical");
        assertEquals(artistPods.size(), response.getBody().size(), "ERROR: Sizes was not identical");

        verify(podServiceMock).findPodsByArtist("Eminem");
    }

    @Test
    void getAllPodForAlbumShouldReturnList() {
        List<Album> albumList = Arrays.asList(new Album("The Marshall Mathers LP"));
        List<Artist> artistList = Arrays.asList(new Artist("Eminem"));
        List<Genre> genreList = Arrays.asList(new Genre("Hip-Hop"));
        List<Pod> albumPods = Arrays.asList(
                new Pod("title1", "url1", "releasedate1", genreList, albumList, artistList),
                new Pod("title2", "url2", "releasedate2", genreList, albumList, artistList),
                new Pod("title3", "url3", "releasedate3", genreList, albumList, artistList),
                new Pod("title4", "url4", "releasedate4", genreList, albumList, artistList)
        );

        when(podServiceMock.findPodsByAlbum("The Marshall Mathers LP")).thenReturn(albumPods);

        ResponseEntity<List<Pod>> response = podController.getAllPodsForAlbum("The Marshall Mathers LP");

        assertEquals(albumPods, response.getBody(), "ERROR: Lists was not identical");
        assertEquals(albumPods.size(), response.getBody().size(), "ERROR: Sizes was not identical");
        assertEquals("The Marshall Mathers LP", response.getBody().get(0).getAlbums().get(0).getName(), "ERROR: Albums was not identical");

        verify(podServiceMock).findPodsByAlbum("The Marshall Mathers LP");
    }

    @Test
    void getAllPodsForGenreShouldReturnList() {
        List<Album> albumList = Arrays.asList(new Album("The Marshall Mathers LP"));
        List<Artist> artistList = Arrays.asList(new Artist("Eminem"));
        List<Genre> genreList = Arrays.asList(new Genre("Hip-Hop"));
        List<Pod> genrePods = Arrays.asList(
                new Pod("title1", "url1", "releasedate1", genreList, albumList, artistList),
                new Pod("title2", "url2", "releasedate2", genreList, albumList, artistList),
                new Pod("title3", "url3", "releasedate3", genreList, albumList, artistList),
                new Pod("title4", "url4", "releasedate4", genreList, albumList, artistList)
        );

        when(podServiceMock.findPodsByGenre("Hip-Hop")).thenReturn(genrePods);

        ResponseEntity<List<Pod>> response = podController.getAllPodsForGenre("Hip-Hop");

        assertEquals(genrePods, response.getBody(), "ERROR: Lists was not identical");
        assertEquals(genrePods.size(), response.getBody().size(), "ERROR: Sizes was not identical");
        assertEquals("Hip-Hop", response.getBody().get(0).getGenres().get(0).getGenre(), "ERROR: Genres was not identical");

        verify(podServiceMock).findPodsByGenre("Hip-Hop");
    }

    @Test
    void getPodByUrlShouldReturnPod() {
        Pod pod = new Pod("title", "url", "releaseDate");

        when(podServiceMock.findPodByUrl("url")).thenReturn(pod);

        ResponseEntity<Pod> response = podController.getPodByUrl("url");

        assertEquals("title", response.getBody().getTitle(), "ERROR: Pod Titles was not identical");

        verify(podServiceMock).findPodByUrl("url");
    }

    @Test
    void createPodShouldReturnPod() {
        List<String> albumInputs = Arrays.asList("The Slim Shady LP");
        List<String> artistInputs = Arrays.asList("Eminem");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("The Real Slim Shady", "url1", "2000-02-22", genreInputs, albumInputs, artistInputs);

        List<Album> albumList = Arrays.asList(new Album("The Slim Shady LP"));
        List<Artist> artistList = Arrays.asList(new Artist("Eminem"));
        List<Genre> genreList = Arrays.asList(new Genre("Hip-Hop"));
        Pod podToBeCreated = new Pod(podDTO.getTitle(), podDTO.getUrl(), podDTO.getReleaseDate(), genreList, albumList, artistList);

        when(podServiceMock.createPod(podDTO)).thenReturn(podToBeCreated);

        ResponseEntity<Pod> response = podController.createPod(podDTO);

        assertEquals("The Real Slim Shady", response.getBody().getTitle(), "ERROR: Pod was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void createPodNoTypeShouldReturnException() {
        List<String> albumInputs = Arrays.asList("The Slim Shady LP");
        List<String> artistInputs = Arrays.asList("Eminem");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("title", "url1", "2022-02-02", genreInputs, albumInputs, artistInputs);

        when(podServiceMock.createPod(podDTO)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Type was not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.createPod(podDTO);
        }, "ERROR: Exception not thrown");

        assertEquals("ERROR: Pod Type was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void createPodNoTitleShouldReturnException() {
        List<String> albumInputs = Arrays.asList("The Slim Shady LP");
        List<String> artistInputs = Arrays.asList("Eminem");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("", "url1", "2022-02-02", genreInputs, albumInputs, artistInputs);

        when(podServiceMock.createPod(podDTO)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Title was not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.createPod(podDTO);
        }, "ERROR: Exception not thrown");

        assertEquals("ERROR: Pod Title was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void createPodNoUrlShouldReturnException() {
        List<String> albumInputs = Arrays.asList("The Slim Shady LP");
        List<String> artistInputs = Arrays.asList("Eminem");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("The Real Slim Shady", "", "2022-02-02", genreInputs, albumInputs, artistInputs);

        when(podServiceMock.createPod(podDTO)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod URL was not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.createPod(podDTO);
        }, "ERROR: Exception not thrown");

        assertEquals("ERROR: Pod URL was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void createPodNoReleaseDateShouldReturnException() {
        List<String> albumInputs = Arrays.asList("The Slim Shady LP");
        List<String> artistInputs = Arrays.asList("Eminem");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("The Real Slim Shady", "url", "", genreInputs, albumInputs, artistInputs);

        when(podServiceMock.createPod(podDTO)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Release date was not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.createPod(podDTO);
        }, "ERROR: Exception not thrown");

        assertEquals("ERROR: Pod Release date was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void createPodNoGenreShouldReturnException() {
        List<String> albumInputs = Arrays.asList("The Slim Shady LP");
        List<String> artistInputs = Arrays.asList("Eminem");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("The Real Slim Shady", "url1", "2022-02-02", genreInputs, albumInputs, artistInputs);

        when(podServiceMock.createPod(podDTO)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Genre was not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.createPod(podDTO);
        }, "ERROR: Exception not thrown");

        assertEquals("ERROR: Pod Genre was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void createPodNoAlbumInputsShouldReturnException() {
        List<String> albumInputs = Arrays.asList("");
        List<String> artistInputs = Arrays.asList("Eminem");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("The Real Slim Shady", "url1", "2022-02-02", genreInputs, albumInputs, artistInputs);

        when(podServiceMock.createPod(podDTO)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Album was not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.createPod(podDTO);
        }, "ERROR: Exception not thrown");

        assertEquals("ERROR: Pod Album was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void createPodNoArtistShouldReturnException() {
        List<String> albumInputs = Arrays.asList("The Slim Shady LP");
        List<String> artistInputs = Arrays.asList("");
        List<String> genreInputs = Arrays.asList("Hip-Hop");
        PodDTO podDTO = new PodDTO("The Real Slim Shady", "", "2022-02-02", genreInputs, albumInputs, artistInputs);

        when(podServiceMock.createPod(podDTO)).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "ERROR: Pod Artist was not provided"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.createPod(podDTO);
        }, "ERROR: Exception not thrown");

        assertEquals("ERROR: Pod Artist was not provided", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).createPod(podDTO);
    }

    @Test
    void updatePodShouldReturnMedia() {
        List<Album> albumList = Arrays.asList(new Album("album1"));
        List<Artist> artistList = Arrays.asList(new Artist("artist1"));
        List<Genre> genreList = Arrays.asList(new Genre("Hip-Hop"));
        long podId = 1;
        Pod existingPod = new Pod("title1", "url1", "2022-02-02", genreList, albumList, artistList);
        existingPod.setId(podId);

        List<String> albumInputs = Arrays.asList("other album");
        List<String> artistInputs = Arrays.asList("other artist");
        List<String> genreInputs = Arrays.asList("other genre");
        PodDTO newInfo = new PodDTO("new title", "url2", "2024-09-02", genreInputs, albumInputs, artistInputs);

        List<Album> newMediaAlbumList = Arrays.asList(new Album("other album"));
        List<Artist> newMediaArtistList = Arrays.asList(new Artist("other artist"));
        List<Genre> newMediaGenreList = Arrays.asList(new Genre("other genre"));
        Pod newPodInfo = new Pod("new title", "url2", "2024-09-02", newMediaGenreList, newMediaAlbumList, newMediaArtistList);

        when(podServiceMock.updatePod(podId, newInfo)).thenReturn(newPodInfo);

        ResponseEntity<Pod> response = podController.updatePod(podId, newInfo);

        assertEquals("new title", response.getBody().getTitle(), "ERROR: Titles was not identical");

        verify(podServiceMock).updatePod(podId, newInfo);
    }

    @Test
    void updatePodInvalidIdShouldReturnException() {
        List<String> albumList = Arrays.asList("new album");
        List<String> artistList = Arrays.asList("new artist");
        List<String> genreList = Arrays.asList("new genre");

        long podId = 1;
        PodDTO newInfo = new PodDTO("new title", "url1", "2022-02-02", genreList, albumList, artistList);

        when(podServiceMock.updatePod(podId, newInfo)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Could not find pod with id: " + podId));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.updatePod(podId, newInfo);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Could not find pod with id: " + podId, response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).updatePod(podId, newInfo);
    }

    @Test
    void deletePodShouldReturnString() {
        long podId = 1;
        Pod podToDelete = new Pod("title", "url1", "1991-01-19");
        podToDelete.setId(podId);

        when(podServiceMock.deletePod(podId)).thenReturn("Pod successfully deleted");

        ResponseEntity<String> response = podController.deletePod(podId);

        assertEquals("Pod successfully deleted", response.getBody(), "ERROR: Strings was not identical");

        verify(podServiceMock).deletePod(podId);
    }

    @Test
    void deletePodShouldReturnException() {
        long podId = 1;

        when(podServiceMock.deletePod(podId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Could not find pod with id: " + podId));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.deletePod(podId);
        }, "ERROR: Exceptions was not thrown");

        assertEquals("ERROR: Could not find pod with id: " + podId, response.getReason(), "ERROR: Exceptions was not identical");

        verify(podServiceMock).deletePod(podId);
    }

    @Test
    void playPodShouldReturnString() {
        String url = "url";
        Pod pod = new Pod("The Real Slim Shady", url, "2000-01-01");

        when(podServiceMock.playPod(url)).thenReturn("Playing " + pod.getType() + ": " + pod.getTitle());

        String result = podServiceMock.playPod(url);

        assertEquals("Playing pod: The Real Slim Shady", result, "ERROR: Strings was not identical");

        verify(podServiceMock).playPod(url);
    }

    @Test
    void playPodShouldReturnException() {
        String url = "url";

        when(podServiceMock.playPod(url)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Pod with URL not found"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.playPod(url);
        }, "ERROR: Exceptions was not thrown");

        assertEquals("ERROR: Pod with URL not found", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).playPod(url);
    }

    @Test
    void likePodShouldReturnString() {
        Pod podToLike = new Pod("title", "url", "release");
        podToLike.setLikes(0);

        when(podServiceMock.likePod(podToLike.getUrl())).thenReturn("Liked Pod: " + podToLike.getTitle());

        String result = podServiceMock.likePod(podToLike.getUrl());

        assertEquals("Liked Pod: " + podToLike.getTitle(), result, "ERROR: Strings was not identical");

        verify(podServiceMock).likePod(podToLike.getUrl());
    }

    @Test
    void likePodNotFoundShouldReturnException() {
        String url = "url";

        when(podServiceMock.likePod(url)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Pod with URL not found"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.likePod(url);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod with URL not found", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).likePod(url);
    }

    @Test
    void disLikePodShouldReturnString() {
        Pod podToDisLike = new Pod("title", "url", "release");
        podToDisLike.setLikes(0);

        when(podServiceMock.disLikePod(podToDisLike.getUrl())).thenReturn("Disliked Pod: " + podToDisLike.getTitle());

        String result = podServiceMock.disLikePod(podToDisLike.getUrl());

        assertEquals("Disliked Pod: " + podToDisLike.getTitle(), result, "ERROR: Strings was not identical");

        verify(podServiceMock).disLikePod(podToDisLike.getUrl());
    }

    @Test
    void disLikePodShouldReturnException() {
        String url = "url";

        when(podServiceMock.disLikePod(url)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Pod with URL not found"));

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> {
            podController.disLikePod(url);
        }, "ERROR: Exception was not thrown");

        assertEquals("ERROR: Pod with URL not found", response.getReason(), "ERROR: Exceptions was not identical");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "ERROR: Status Codes was not identical");

        verify(podServiceMock).disLikePod(url);
    }

    @Test
    void podExistShouldReturnTrue() {
        String url = "url";

        when(podServiceMock.checkIfPodExistByUrl(url)).thenReturn(true);

        ResponseEntity<Boolean> response = podController.podExist(url);

        assertTrue(response.getBody(), "ERROR: Response was false");

        verify(podServiceMock).checkIfPodExistByUrl(url);
    }

    @Test
    void podExistShouldReturnFalse() {
        String url = "url";

        when(podServiceMock.checkIfPodExistByUrl(url)).thenReturn(false);

        ResponseEntity<Boolean> response = podController.podExist(url);

        assertFalse(response.getBody(), "ERROR: Response was true");

        verify(podServiceMock).checkIfPodExistByUrl(url);
    }
}