package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.entities.Artist;
import com.example.PodMicroservice_CopyAllToNew.repositories.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ArtistServiceTest {

    private ArtistRepository artistRepositoryMock;
    private ArtistService artistService;

    @BeforeEach
    void setUp() {
        artistRepositoryMock = mock(ArtistRepository.class);
        artistService = new ArtistService(artistRepositoryMock);
    }

    @Test
    void getAllArtistsShouldReturnListOfThreeArtistsAndIdTwoShouldBeDireStraits() {
        List<Artist> artistList = Arrays.asList(
                new Artist("Iron Maiden"),
                new Artist("Dire Straits"),
                new Artist("Lady Gaga")
        );

        when(artistRepositoryMock.findAll()).thenReturn(artistList);
        List <Artist> result = artistService.getAllArtists();
        assertEquals(3, result.size());
        assertEquals("Dire Straits", result.get(1).getName());
    }

    @Test
    void getArtistByNameShouldReturnArtistWhenExists() {
        List<Artist> artistList = Arrays.asList(
                new Artist("Iron Maiden"),
                new Artist("Dire Straits"),
                new Artist("Lady Gaga")
        );

        when(artistRepositoryMock.findByNameIgnoreCase("Dire Straits")).thenReturn(Optional.ofNullable(artistList.get(1)));
        Artist result = artistService.getArtistByName("Dire Straits");
        assertEquals("Dire Straits", result.getName());
    }

    @Test
    void getArtistByNameShouldReturnNullWhenDoesntExists() {
        String artistName = "Doesn't Exists";

        when(artistRepositoryMock.findByNameIgnoreCase(artistName)).thenReturn(Optional.empty());
        Artist result = artistService.getArtistByName(artistName);
        assertNull(result);
    }

    @Test
    void artistExistsShouldReturnTrueWhenExists() {
        List<Artist> artistList = Arrays.asList(
                new Artist("Iron Maiden"),
                new Artist("Dire Straits"),
                new Artist("Lady Gaga")
        );
        when(artistRepositoryMock.existsByNameIgnoreCase("Iron Maiden")).thenReturn(true);
        boolean result = artistService.artistExist("Iron Maiden");
        assertTrue(result);
    }

    @Test
    void artistExistsShouldReturnFalseWhenDoesntExists() {
        List<Artist> artistList = Arrays.asList(
                new Artist("Iron Maiden"),
                new Artist("Dire Straits"),
                new Artist("Lady Gaga")
        );
        when(artistRepositoryMock.existsByNameIgnoreCase("No Way Near An Artist")).thenReturn(false);
        boolean result = artistService.artistExist("No Way Near An Artist");
        assertFalse(result);
    }

    @Test
    void createArtistShouldReturnArtistWhenSuccessful() {
        Artist artist = new Artist();
        artist.setName("Incredible Bad Artist");

        when(artistRepositoryMock.save(artist)).thenReturn(artist);
        Artist result = artistService.createArtist(artist);
        assertEquals(artist.getName(), result.getName());
        verify(artistRepositoryMock, times(1)).save(artist);
    }
}