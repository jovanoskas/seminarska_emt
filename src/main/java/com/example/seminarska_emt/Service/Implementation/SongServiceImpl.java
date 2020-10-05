package com.example.seminarska_emt.Service.Implementation;

import com.example.seminarska_emt.Repository.SongRepository;
import com.example.seminarska_emt.Service.SongService;
import com.example.seminarska_emt.model.Song;
import com.example.seminarska_emt.model.exceptions.SongIsAlreadyInShoppingCart;
import com.example.seminarska_emt.model.exceptions.SongNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> findAll() {
        return this.songRepository.findAll();
    }


    @Override
    public Song findById(Long id) {
        return (Song) this.songRepository.findById(id)
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


}
