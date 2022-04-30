package com.oda.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/16/22
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public String getAdminHomePage(){
        return "admin/adminhomepage";
    }
}
