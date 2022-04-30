package com.oda.controller.admin;

import com.oda.authorizeduser.AuthorizedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/doctor-view")
    public String getDoctorViewPage(){
        return "admin/viewdoctor";
    }

    @GetMapping("/patient-view")
    public String getPatientViewPage(){
        return "admin/viewpatient";
    }

    @GetMapping("/doctor-request")
    public String getDoctorRequestPage(){
        return "admin/doctorrequest";
    }

    @GetMapping("/patient-request")
    public String getPatientRequestPage(){
        return "admin/patientrequest";
    }
}
