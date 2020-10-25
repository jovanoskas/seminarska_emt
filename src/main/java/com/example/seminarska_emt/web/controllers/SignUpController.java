package com.example.seminarska_emt.web.controllers;

import com.example.seminarska_emt.Service.ArtistService;
import com.example.seminarska_emt.Service.UserService;
import com.example.seminarska_emt.model.User;
import com.example.seminarska_emt.web.controllers.dto.UserSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserSignup userSignup() {
        return new UserSignup();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "signup";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Validated UserSignup userSignup,
                                      BindingResult result){

        User existing = userService.findByEmail(userSignup.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userSignup);
        return "redirect:/registration?success";
    }




}