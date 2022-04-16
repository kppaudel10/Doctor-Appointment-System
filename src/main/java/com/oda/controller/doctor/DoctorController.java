package com.oda.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/16/22
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/home")
    public String getDoctorHomePage(){
        return "doctor/doctorhomepage";
    }
}
