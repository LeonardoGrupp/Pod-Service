package com.example.PodMicroservice_CopyAllToNew.repositories;

import com.example.PodMicroservice_CopyAllToNew.entities.Artist;
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
class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    void setUp() {
        artistRepository.save(new Artist("Proxyon"));
        Artist artist = new Artist("Ebba Grön");
        artistRepository.save(artist);
    }

    @Test
    void findByNameIgnoreCaseShouldReturnArtistWhenExistsIgnoringCase() {
        Optional<Artist> artist1 = artistRepository.findByNameIgnoreCase("proxyon");
        Optional<Artist> artist2 = artistRepository.findByNameIgnoreCase("PROXYON");
        assertEquals(artist1, artist2);
    }

    @Test
    void findByNameIgnoreCaseShouldReturnOptionalWhenArtistNameDoesntExists() {
        Optional<Artist> artist = artistRepository.findByNameIgnoreCase("Ebba Gul");
        assertFalse(artist.isPresent());
    }

    @Test
    void existsByNameIgnoreCaseShouldReturnTrueWhenArtistExistsIgnoringCase() {
        boolean artist = artistRepository.existsByNameIgnoreCase("PrOxYoN");
        assertTrue(artist);
    }

    @Test
    void existsByNameIgnoreCaseShouldReturnFalseWhenArtistDoesntExists() {
        boolean artist = artistRepository.existsByNameIgnoreCase("Perssons Pack");
        assertFalse(artist);
    }
}