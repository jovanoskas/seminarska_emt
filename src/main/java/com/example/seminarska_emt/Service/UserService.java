package com.example.seminarska_emt.Service;

import com.example.seminarska_emt.model.User;
import com.example.seminarska_emt.web.controllers.dto.UserSignup;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);
    User save(UserSignup userSignup);
    User findById(String userId);
    User registerUser(User user);
}
