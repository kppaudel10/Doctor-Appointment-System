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

    @GetMapping("/feedback")
    public String getFeedbackForm(){
        return "patient/feedback";
    }

    @GetMapping("/view/status")
    public String getViewStatusPage(){
        return "patient/viewstatuspage";
    }

    @GetMapping("/request")
    public String getRequestPage(){
        return "admin/patientrequest";
    }
}
