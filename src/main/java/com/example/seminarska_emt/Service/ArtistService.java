package com.example.seminarska_emt.Service;

import com.example.seminarska_emt.model.Artist;
import com.example.seminarska_emt.model.User;

import java.util.List;

public interface ArtistService {
    User findById(String id);
    Artist save(Artist artist);

    User save(User user);
    Artist findById(List<Artist> artist);

    User getCurrentUser();

    String getCurrentUserId();

}
