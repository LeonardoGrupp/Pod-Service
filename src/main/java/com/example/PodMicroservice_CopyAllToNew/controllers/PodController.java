package com.example.PodMicroservice_CopyAllToNew.controllers;

import com.example.PodMicroservice_CopyAllToNew.dto.PodDTO;
import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import com.example.PodMicroservice_CopyAllToNew.services.PodService;
import com.example.PodMicroservice_CopyAllToNew.services.PodServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pod")
public class PodController {

    private PodService podService;

    @Autowired
    public PodController(PodService podService) {
        this.podService = podService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pod>> getAllPods() {
        return ResponseEntity.ok(podService.findAllPods());
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<Pod>> getAllPodsForArtist(@PathVariable("artist") String artist) {
        return ResponseEntity.ok(podService.findPodsByArtist(artist));
    }

    @GetMapping("/album/{album}")
    public ResponseEntity<List<Pod>> getAllPodsForAlbum(@PathVariable("album") String album) {
        return ResponseEntity.ok(podService.findPodsByAlbum(album));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Pod>> getAllPodsForGenre(@PathVariable("genre") String genre) {
        return ResponseEntity.ok(podService.findPodsByGenre(genre));
    }

    @GetMapping("/get/{url}")
    public ResponseEntity<Pod> getPodByUrl(@PathVariable("url") String url) {
        return ResponseEntity.ok(podService.findPodByUrl(url));
    }

    @PostMapping("/create")
    public ResponseEntity<Pod> createPod(@RequestBody PodDTO podDTO) {
        return ResponseEntity.ok(podService.createPod(podDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Pod> updatePod(@PathVariable("id") long id, @RequestBody PodDTO newPodInfo) {
        return ResponseEntity.ok(podService.updatePod(id, newPodInfo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePod(@PathVariable("id") long id) {
        return ResponseEntity.ok(podService.deletePod(id));
    }

    @GetMapping("/play/{url}")
    public ResponseEntity<String> playPod(@PathVariable("url") String url) {
        return ResponseEntity.ok(podService.playPod(url));
    }

    @GetMapping("/like/{url}")
    public ResponseEntity<String> likePod(@PathVariable("url") String url) {
        return ResponseEntity.ok(podService.likePod(url));
    }

    @GetMapping("/dislike/{url}")
    public ResponseEntity<String> disLikePod(@PathVariable("url") String url) {
        return ResponseEntity.ok(podService.disLikePod(url));
    }

    @GetMapping("/exists/{url}")
    public ResponseEntity<Boolean> podExist(@PathVariable("url") String url) {
        return ResponseEntity.ok(podService.checkIfPodExistByUrl(url));
    }
}
