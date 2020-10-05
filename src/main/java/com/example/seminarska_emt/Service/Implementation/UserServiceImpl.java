package com.example.seminarska_emt.Service.Implementation;

import com.example.seminarska_emt.Repository.UserRepository;
import com.example.seminarska_emt.Service.UserService;
import com.example.seminarska_emt.model.User;
import com.example.seminarska_emt.model.exceptions.UserAlreadyExists;
import com.example.seminarska_emt.model.exceptions.UserNotFound;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

