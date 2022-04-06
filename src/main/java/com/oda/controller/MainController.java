package com.oda.controller;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import com.oda.service.Impl.DoctorServiceImpl;
import com.oda.service.Impl.PatientServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;

    public MainController(PatientServiceImpl patientService, DoctorServiceImpl doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/home")
    public String getLandingPage() {
        return "homePage";
    }

    @GetMapping("/")
    public String getHomePage(){
        if (AuthorizedUser.getUserStatus().ordinal() == 2){
            Patient patient = patientService.findPatientByEmail(AuthorizedUser.getPatient().getEmail());
            //make patient null
            AuthorizedUser.setPatient(null);
            //asign again
            AuthorizedUser.setPatient(patient);

            return "patient/patienthomepage";
        }else if (AuthorizedUser.getUserStatus().ordinal() == 1){
            Doctor doctor = doctorService.findDoctorByEmail(AuthorizedUser.getDoctor().getEmail());
            AuthorizedUser.setDoctor(null);
            AuthorizedUser.setDoctor(doctor);
            return "doctor/doctorhomepage";
        }else {
            return null;
        }
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "loginpage/loginpage";
    }
}
