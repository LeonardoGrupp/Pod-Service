package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import com.example.PodMicroservice_CopyAllToNew.repositories.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAlbumById_ReturnsAlbum() {
        Long albumId = 1L;
        Album mockAlbum = new Album();
        mockAlbum.setId(albumId);
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(mockAlbum));
        Album result = albumService.getAlbumById(albumId);

        assertNotNull(result);
    }

    @Test
    void testGetAlbumById_AlbumHasCorrectId() {
        Long albumId = 1L;
        Album mockAlbum = new Album();
        mockAlbum.setId(albumId);
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(mockAlbum));
        Album result = albumService.getAlbumById(albumId);

        assertEquals(albumId, result.getId());
    }

    @Test
    void testGetAlbumById_VerifiesRepositoryCalled() {
        Long albumId = 1L;
        when(albumRepository.findById(albumId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            albumService.getAlbumById(albumId);
        });

        verify(albumRepository, times(1)).findById(albumId);
    }

    @Test
    void testGetAlbumById_ThrowsExceptionIfNotFound() {
        Long albumId = 1L;
        when(albumRepository.findById(albumId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            albumService.getAlbumById(albumId);
        });

        assertEquals("Album not found", exception.getMessage());
    }

    @Test
    void testCreateAlbum_ReturnsAlbum() {
        Album album = new Album();
        album.setName("New Album");
        when(albumRepository.save(album)).thenReturn(album);
        Album result = albumService.createAlbum(album);

        assertNotNull(result);
    }

    @Test
    void testCreateAlbum_AlbumHasCorrectName() {
        Album album = new Album();
        album.setName("New Album");
        when(albumRepository.save(album)).thenReturn(album);
        Album result = albumService.createAlbum(album);

        assertEquals("New Album", result.getName());
    }

    @Test
    void testCreateAlbum_VerifiesRepositoryCalled() {
        Album album = new Album();
        when(albumRepository.save(album)).thenReturn(album);
        albumService.createAlbum(album);

        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void testUpdateAlbum_ReturnsUpdatedAlbum() {
        Long albumId = 1L;
        Album existingAlbum = new Album();
        existingAlbum.setId(albumId);
        existingAlbum.setName("Old Album");

        Album updatedAlbum = new Album();
        updatedAlbum.setName("Updated Album");

        when(albumRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));
        when(albumRepository.save(existingAlbum)).thenReturn(existingAlbum);

        Album result = albumService.updateAlbum(albumId, updatedAlbum);

        assertNotNull(result);
    }

    @Test
    void testUpdateAlbum_AlbumNameIsUpdated() {
        Long albumId = 1L;
        Album existingAlbum = new Album();
        existingAlbum.setId(albumId);
        existingAlbum.setName("Old Album");

        Album updatedAlbum = new Album();
        updatedAlbum.setName("Updated Album");

        when(albumRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));
        when(albumRepository.save(existingAlbum)).thenReturn(existingAlbum);

        Album result = albumService.updateAlbum(albumId, updatedAlbum);

        assertEquals("Updated Album", result.getName());
    }

    @Test
    void testUpdateAlbum_ReleaseDateIsUpdated() {
        Long albumId = 1L;
        Album existingAlbum = new Album();
        existingAlbum.setId(albumId);
        existingAlbum.setReleaseDate("2020-01-01");

        Album updatedAlbum = new Album();
        updatedAlbum.setReleaseDate("2021-01-01");

        when(albumRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));
        when(albumRepository.save(existingAlbum)).thenReturn(existingAlbum);

        Album result = albumService.updateAlbum(albumId, updatedAlbum);

        assertEquals("2021-01-01", result.getReleaseDate());
    }

    @Test
    void testUpdateAlbum_VerifiesFindByIdCalled() {
        Long albumId = 1L;
        Album existingAlbum = new Album();
        existingAlbum.setId(albumId);

        Album updatedAlbum = new Album();
        updatedAlbum.setName("Updated Album");

        when(albumRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));

        albumService.updateAlbum(albumId, updatedAlbum);

        verify(albumRepository, times(1)).findById(albumId);
    }

    @Test
    void testUpdateAlbum_VerifiesSaveCalled() {
        Long albumId = 1L;
        Album existingAlbum = new Album();
        existingAlbum.setId(albumId);

        Album updatedAlbum = new Album();
        updatedAlbum.setName("Updated Album");

        when(albumRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));

        albumService.updateAlbum(albumId, updatedAlbum);

        verify(albumRepository, times(1)).save(existingAlbum);
    }

    @Test
    void testDeleteAlbum_VerifiesDeleteByIdCalled() {
        Long albumId = 1L;

        albumService.deleteAlbum(albumId);

        verify(albumRepository, times(1)).deleteById(albumId);
    }

    @Test
    void testGetAllAlbums_ReturnsAlbumList() {
        List<Album> albums = Arrays.asList(new Album(), new Album());
        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> result = albumService.getAllAlbums();

        assertNotNull(result);
    }

    @Test
    void testGetAllAlbums_AlbumListHasCorrectSize() {
        List<Album> albums = Arrays.asList(new Album(), new Album());
        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> result = albumService.getAllAlbums();

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllAlbums_VerifiesFindAllCalled() {
        List<Album> albums = Arrays.asList(new Album(), new Album());
        when(albumRepository.findAll()).thenReturn(albums);

        albumService.getAllAlbums();

        verify(albumRepository, times(1)).findAll();
    }
}