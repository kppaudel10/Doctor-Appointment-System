package com.oda.controller.patient;

import com.oda.authorizeduser.AuthorizedUser;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getPatientHomePage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        return "patient/patienthomepage";
    }

    @GetMapping("/feedback")
    public String getFeedbackForm(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        return "patient/feedback";
    }

    @GetMapping("/view/status")
    public String getViewStatusPage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        return "patient/viewstatuspage";
    }

    @GetMapping("/request")
    public String getRequestPage(){
        return "admin/patientrequest";
    }

    @GetMapping("/profile/view")
    public String getProfileViewPage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        return "patient/profileview";
    }
}
