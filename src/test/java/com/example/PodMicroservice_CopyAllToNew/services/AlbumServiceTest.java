package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import com.example.PodMicroservice_CopyAllToNew.repositories.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AlbumServiceTest {

    private AlbumRepository albumRepositoryMock;
    private AlbumService albumService;

    @BeforeEach
    void setUp() {
        albumRepositoryMock = mock(AlbumRepository.class);
        albumService = new AlbumService(albumRepositoryMock);
    }

    @Test
    void getAllAlbumsShouldReturnListOfThreeAlbumsAndIdTwoShouldBeAlchemy() {
        List<Album> albumList = Arrays.asList(
                new Album("Rock in Rio"),
                new Album("Alchemy"),
                new Album("The Chromatica Ball")
        );

        when(albumRepositoryMock.findAll()).thenReturn(albumList);
        List <Album> result = albumService.getAllAlbums();
        assertEquals(3, result.size());
        assertEquals("Alchemy", result.get(1).getName());
    }

    @Test
    void getAlbumByNameShouldReturnAlbumWhenExists() {
        List<Album> albumList = Arrays.asList(
                new Album("Rock in Rio"),
                new Album("Alchemy"),
                new Album("The Chromatica Ball")
        );

        when(albumRepositoryMock.findByNameIgnoreCase("Alchemy")).thenReturn(Optional.ofNullable(albumList.get(1)));
        Album result = albumService.getAlbumByName("Alchemy");
        assertEquals("Alchemy", result.getName());
    }

    @Test
    void getAlbumByNameShouldReturnNullWhenDoesntExists() {
        String albumName = "Doesn't Exists";

        when(albumRepositoryMock.findByNameIgnoreCase(albumName)).thenReturn(Optional.empty());
        Album result = albumService.getAlbumByName(albumName);
        assertNull(result);
    }

    @Test
    void albumExistsShouldReturnTrueWhenExists() {
        List<Album> albumList = Arrays.asList(
                new Album("Rock in Rio"),
                new Album("Alchemy"),
                new Album("The Chromatica Ball")
        );
        when(albumRepositoryMock.existsByNameIgnoreCase("Rock in Rio")).thenReturn(true);
        boolean result = albumService.albumExist("Rock in Rio");
        assertTrue(result);
    }

    @Test
    void albumExistsShouldReturnFalseWhenDoesntExists() {
        List<Album> albumList = Arrays.asList(
                new Album("Rock in Rio"),
                new Album("Alchemy"),
                new Album("The Chromatica Ball")
        );
        when(albumRepositoryMock.existsByNameIgnoreCase("Cheese Album")).thenReturn(false);
        boolean result = albumService.albumExist("Cheese Album");
        assertFalse(result);
    }

    @Test
    void createAlbumShouldReturnAlbumWhenSuccessful() {
        Album album = new Album();
        album.setName("New Album");

        when(albumRepositoryMock.save(album)).thenReturn(album);
        Album result = albumService.createAlbum(album);
        assertEquals(album.getName(), result.getName());
        verify(albumRepositoryMock, times(1)).save(album);
    }
}