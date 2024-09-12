package com.example.PodMicroservice_CopyAllToNew.repositories;

import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PodRepositoryTest {

    @Autowired
    private PodRepository podRepository;
    private Pod pod1;

    @BeforeEach
    void setUp() {
        pod1 = new Pod("podtitle", "url10", "2010-02-10");
        Pod comedy1 = new Pod("comedy pod", "url11", "2009-01-11");
        Pod comedy2 = new Pod("funny pod", "url12", "2011-05-13");

        podRepository.save(pod1);
        podRepository.save(comedy1);
        podRepository.save(comedy2);
    }

    @Test
    void findPodByUrlShouldReturnPod() {
        Pod pod = podRepository.findPodByUrl("url99");

        assertEquals(pod, pod1, "ERROR: Pod was not identical");
    }
}