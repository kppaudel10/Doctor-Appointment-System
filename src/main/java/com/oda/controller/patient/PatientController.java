package com.oda.controller.patient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/5/22
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    @GetMapping("/home")
    public String getPatientHomePage(){
        return "patient/patienthomepage";
    }
}
