package com.example.seminarska_emt.web.rest_controllers;

import com.example.seminarska_emt.Service.SongService;
import com.example.seminarska_emt.model.Song;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongRestController {
    private final SongService songService;

    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Song> findAll() {
        return this.songService.findAll();
    }

    @GetMapping("/{id}")
    public Song findById(@PathVariable Long id) {
        return this.songService.findById(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.songService.deleteById(id);
    }

}
