package com.example.seminarska_emt.web.controllers;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String indexPage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHomePage(ServletServerHttpResponse res, ServletServerHttpRequest req) {
        return "home";
    }

}
