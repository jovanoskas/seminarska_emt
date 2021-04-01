package com.example.seminarska_emt.Service.Implementation;

import com.example.seminarska_emt.Repository.RoleRepository;
import com.example.seminarska_emt.Repository.UserRepository;
import com.example.seminarska_emt.Service.ArtistService;
import com.example.seminarska_emt.Service.UserService;
import com.example.seminarska_emt.model.Artist;
import com.example.seminarska_emt.model.Role;
import com.example.seminarska_emt.model.User;
import com.example.seminarska_emt.model.exceptions.PasswordDoesntMatch;
import com.example.seminarska_emt.model.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public ArtistServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public User findById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
    }

    @Override
    public Artist save(Artist artist) {
        return null;
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }


    @Override
    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }

    @Override
    public User signUpUser(String username, String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            throw new PasswordDoesntMatch();
        }
        User user = new User();
        user.setUsername(username);
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);

    }

    @Override
    public Artist findById(Long artistId) {
        return null;
    }

    @PostConstruct
    public void init() {
        if (!this.userRepository.existsById("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }
    }

}

