package com.example.PodMicroservice_CopyAllToNew.repositories;

import com.example.PodMicroservice_CopyAllToNew.entities.Album;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")                                                             // kör konfigurationen från application-test.properties
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)       // Gör en rollback efter varje test så att inget förändras i H2-databasen
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @BeforeEach
    void setUp() {
        albumRepository.save(new Album("Greatest Hits"));
        Album album = new Album("Not So Good");
        albumRepository.save(album);
    }

    @Test
    void findByNameIgnoreCaseShouldReturnAlbumWhenExistsIgnoringCase() {
        Optional<Album> album1 = albumRepository.findByNameIgnoreCase("GREATEST hits");
        Optional<Album> album2 = albumRepository.findByNameIgnoreCase("greatest HITS");
        assertEquals(album1, album2);
    }

    @Test
    void findByNameIgnoreCaseShouldReturnOptionalWhenAlbumNameDoesntExists() {
        Optional<Album> album = albumRepository.findByNameIgnoreCase("Extremely Wonderful Album");
        assertFalse(album.isPresent());
    }

    @Test
    void existsByNameIgnoreCaseShouldReturnTrueWhenAlbumExistsIgnoringCase() {
        boolean album = albumRepository.existsByNameIgnoreCase("NOT so GoOd");
        assertTrue(album);
    }

    @Test
    void existsByNameIgnoreCaseShouldReturnFalseWhenAlbumDoesntExists() {
        boolean album = albumRepository.existsByNameIgnoreCase("Just Perfect");
        assertFalse(album);
    }
}