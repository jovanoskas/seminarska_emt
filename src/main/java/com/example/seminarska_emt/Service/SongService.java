package com.example.seminarska_emt.Service;

import com.example.seminarska_emt.model.Song;

import java.util.List;

public interface SongService {
    List<Song> findAll();
    Song findById(Long id);
    void deleteById(Long id);
    List<Song> findAllByArtistId(Long artistId);
    Song saveSong(String name, Float price, Long artistId);

}

