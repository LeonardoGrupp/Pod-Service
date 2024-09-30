package com.example.PodMicroservice_CopyAllToNew.repositories;

import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")                                                             // kör konfigurationen från application-test.properties
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)       // Gör en rollback efter varje test så att inget förändras i H2-databasen
class PodRepositoryTest {

    @Autowired
    private PodRepository podRepository;

    @BeforeEach
    void setUp() {
        podRepository.save(new Pod("The Greatest", "url2000", "2016"));
        Pod pod = new Pod("Kickstart My Heart", "url2001", "1990");
        podRepository.save(pod);
    }

    @Test
    void findPodByUrlShouldReturnPodWithTitleTheGreatest() {
        Pod pod = podRepository.findPodByUrl("url2000");
        assertEquals("The Greatest", pod.getTitle());
    }

    @Test
    void findPodByUrlShouldReturnPodWithIdTwo() {
        Pod pod = podRepository.findPodByUrl("url2001");
        assertEquals(2, pod.getId());
    }

    @Test
    void findPodByUrlShouldReturnNullWhenUrlDoesNotExist() {
        Pod pod = podRepository.findPodByUrl("url9999");
        assertNull(pod);
    }
}