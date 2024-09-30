package com.example.PodMicroservice_CopyAllToNew.repositories;

import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodRepository extends JpaRepository<Pod, Long> {
    Pod findPodByUrl(String url);
}
