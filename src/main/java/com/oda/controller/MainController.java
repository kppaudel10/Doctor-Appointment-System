package com.oda.controller;

import com.oda.dto.admin.AdminDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/home")
    public String getLandingPage() {
        return "signup/signupformchoose";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "loginpage/loginpage";
    }
}
