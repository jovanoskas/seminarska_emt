package com.example.seminarska_emt.Service.Implementation;

import com.example.seminarska_emt.Repository.UserRepository;
import com.example.seminarska_emt.Service.UserService;
import com.example.seminarska_emt.model.Role;
import com.example.seminarska_emt.model.User;
import com.example.seminarska_emt.model.exceptions.UserAlreadyExists;
import com.example.seminarska_emt.model.exceptions.UserNotFound;
import com.example.seminarska_emt.web.controllers.dto.UserSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserSignup userSignup) {
        User user = new User();
        user.setFirstName(userSignup.getFirstName());
        user.setLastName(userSignup.getLastName());
        user.setEmail(userSignup.getEmail());
        user.setPassword(passwordEncoder.encode(userSignup.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public User findById(String userId) {
        return (User) this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));
    }

    @Override
    public User registerUser(User user) {
        if (this.userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExists(user.getUsername());
        }
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s){
        return (UserDetails) this.userRepository.findById(s)
            .orElseThrow(()-> new UsernameNotFoundException(s));
    }

}

