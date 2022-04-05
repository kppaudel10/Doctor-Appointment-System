package com.oda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/home")
    public String getLandingPage() {
        return "homePage";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "loginpage/loginpage";
    }
}
