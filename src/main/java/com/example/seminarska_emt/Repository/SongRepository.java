package com.example.seminarska_emt.Repository;

import com.example.seminarska_emt.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByArtistId(Long artistId);
}
