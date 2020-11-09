package com.example.seminarska_emt.Service;

import com.example.seminarska_emt.model.Artist;
import com.example.seminarska_emt.model.User;

import java.util.List;

public interface ArtistService {
    User findById(String id);
    Artist save(Artist artist);

    User save(User user);

    User getCurrentUser();

    String getCurrentUserId();
    User signUpUser(String username, String password, String repeatedPassword);

    Artist findById(Long artistId);

}
