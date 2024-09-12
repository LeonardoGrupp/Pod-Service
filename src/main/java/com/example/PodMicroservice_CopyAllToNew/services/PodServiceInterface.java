package com.example.PodMicroservice_CopyAllToNew.services;

import com.example.PodMicroservice_CopyAllToNew.dto.PodDTO;
import com.example.PodMicroservice_CopyAllToNew.entities.Genre;
import com.example.PodMicroservice_CopyAllToNew.entities.Pod;
import com.example.PodMicroservice_CopyAllToNew.vo.Album;
import com.example.PodMicroservice_CopyAllToNew.vo.Artist;

import java.util.List;

public interface PodServiceInterface {

    List<Pod> findAllPods();
    List<Pod> findPodsByArtist(String artistName);
    List<Pod> findPodsByAlbum(String albumName);
    List<Pod> findPodsByGenre(String genreName);
    Pod findPodByUrl(String url);
    Pod findPodById(long id);
    Pod createPod(PodDTO podDTO);
    List<Genre> getAllGenres(PodDTO podDTO);
    List<Album> getAllAlbums(PodDTO podDTO);
    List<Artist> getAllArtists(PodDTO podDTO);
    Pod updatePod(long id, PodDTO newMusicInfo);
    String deletePod(long id);
    String playPod(String url);
    String likePod(String url);
    String disLikePod(String url);
}
