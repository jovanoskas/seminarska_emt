package com.example.seminarska_emt.Service.Implementation;

import com.example.seminarska_emt.Repository.SongRepository;
import com.example.seminarska_emt.Service.ArtistService;
import com.example.seminarska_emt.Service.SongService;
import com.example.seminarska_emt.model.Artist;
import com.example.seminarska_emt.model.Song;
import com.example.seminarska_emt.model.exceptions.SongIsAlreadyInShoppingCart;
import com.example.seminarska_emt.model.exceptions.SongNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistService artistService;

    public SongServiceImpl(SongRepository songRepository, ArtistService artistService) {
        this.songRepository = songRepository;
        this.artistService = artistService;
    }

    @Override
    public List<Song> findAll() {
        return this.songRepository.findAll();
    }


    @Override
    public Song findById(Long id) {
        return this.songRepository.findById(id)
                .orElseThrow(() -> new SongNotFound(id));
    }


    @Override
    public void deleteById(Long id) {
        Song song = this.findById(id);
        if (song.getShoppingCarts().size() > 0) {
            throw new SongIsAlreadyInShoppingCart(song.getName());
        }

        this.songRepository.deleteById(id);
    }

    @Override
    public List<Song> findAllByArtistId(Long artistId) {
        return this.songRepository.findAllByArtistId(artistId);
    }

    @Override
    public Song saveSong(String name, Float price, Long artistId) {
        Artist artist = this.artistService.findById(artistId);
        Song song = new Song(null, name, price, artistId);
        return this.songRepository.save(song);

    }


}
