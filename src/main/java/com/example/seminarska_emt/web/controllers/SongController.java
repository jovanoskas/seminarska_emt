package com.example.seminarska_emt.web.controllers;

import com.example.seminarska_emt.Repository.ArtistRepository;
import com.example.seminarska_emt.Service.SongService;
import com.example.seminarska_emt.model.Song;
import com.example.seminarska_emt.model.exceptions.SongIsAlreadyInShoppingCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class SongController {


    private final SongService songService;
    private final ArtistRepository artistRepository;

    public SongController(SongService songService,
                             ArtistRepository artistRepository) {
        this.songService = songService;
        this.artistRepository = artistRepository;
    }

    @GetMapping
    public String getSongPage(Model model) {
        List<Song> songs = this.songService.findAll();
        model.addAttribute("songs", songs);
        return "songs";
    }


    @GetMapping("/add-new")
    public String addNewSongPage(Model model) {
        model.addAttribute("artists", this.artistRepository.findAll());
        model.addAttribute("songs", new Song());
        return "add-song";
    }

    @GetMapping("/{id}/edit")
    public String editSongPage(Model model, @PathVariable Long id) {
        try {
            Song song = this.songService.findById(id);
            model.addAttribute("song", song);
            model.addAttribute("artists", this.artistRepository.findAll());
            return "add-song";
        } catch (RuntimeException ex) {
            return "redirect:/songs?error=" + ex.getMessage();
        }
    }


    @PostMapping("/{id}/delete")
    public String deleteSongWithPost(@PathVariable Long id) {
        try {
            this.songService.deleteById(id);
        } catch (SongIsAlreadyInShoppingCart ex) {
            return String.format("redirect:/songs?error=%s", ex.getMessage());
        }
        return "redirect:/songs";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProductWithDelete(@PathVariable Long id) {
        this.songService.deleteById(id);
        return "redirect:/songs";
    }
}
