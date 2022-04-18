package com.oda.controller;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.conversion.DtoEntityConversion;
import com.oda.dto.login.LoginDto;
import com.oda.dto.patient.PatientDto;
import com.oda.enums.UserStatus;
import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.service.Impl.AdminServiceImpl;
import com.oda.service.Impl.DoctorServiceImpl;
import com.oda.service.Impl.LoginServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping("/")
public class MainController {
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;
    private final AdminServiceImpl adminService;
    private final LoginServiceImpl loginService;

    public MainController(PatientServiceImpl patientService, DoctorServiceImpl doctorService, AdminServiceImpl adminService, LoginServiceImpl loginService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.adminService = adminService;
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String getLandingPage() {
        return "homePage";
    }
//
//    @GetMapping("/")
//    public String getHomePage(){
//        if (AuthorizedUser.getUserStatus().ordinal() == 2){
//            Patient patient = patientService.findPatientByEmail(AuthorizedUser.getPatient().getEmail());
//            //make patient null
//            AuthorizedUser.setPatient(null);
//            //asign again
//            AuthorizedUser.setPatient(patient);
//
////            return "patient/patienthomepage";
//            return "redirect:/patient/home";
//        }else if (AuthorizedUser.getUserStatus().ordinal() == 1){
//            Doctor doctor = doctorService.findDoctorByEmail(AuthorizedUser.getDoctor().getEmail());
//            AuthorizedUser.setDoctor(null);
//            AuthorizedUser.setDoctor(doctor);
//            return "doctor/doctorhomepage";
//        }else if(AuthorizedUser.getUserStatus().ordinal() == 0){
//           Admin admin= adminService.findAdminByEmail(AuthorizedUser.getAdmin().getEmail());
//           AuthorizedUser.setAdmin(null);
//           AuthorizedUser.setAdmin(admin);
//            return "admin/adminhomepage";
//        }else {
//          return null;
//        }
//    }
//
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "loginpage/loginpage";
    }

    @PostMapping("/login/verify")
    public String getVerify(@ModelAttribute("loginDto")LoginDto loginDto,Model model) throws IOException, ParseException {
        //get check valid user or not
       UserStatus userStatus = loginService.getLogin(loginDto.getUsername(),loginDto.getPassword());
       if (userStatus != null){
           if (userStatus.equals(UserStatus.ADMIN)){
              Admin admin = adminService.findByUserName(loginDto.getUsername());
               AuthorizedUser.setAdmin(admin);
               return "redirect:/admin/home";

           }else if(userStatus.equals(UserStatus.DOCTOR)){
               Doctor doctor = doctorService.findByUserName(loginDto.getUsername());
               AuthorizedUser.setDoctor(doctor);
               return "redirect:/doctor/home";

           }
           else {
               PatientDto patient = patientService.findByUserName(loginDto.getUsername());
               AuthorizedUser.setPatient(new DtoEntityConversion().getPatient(patient));
               return "redirect:/patient/home";
           }
       }
       model.addAttribute("message","Invalid username and password.");
        return "loginpage/loginpage";
    }

    @GetMapping("/logout")
    public String logOut(Model model){
        //set Authorized user null
        AuthorizedUser.setUserStatus(null);
        AuthorizedUser.setPatient(null);
        AuthorizedUser.setDoctor(null);
        AuthorizedUser.setAdmin(null);
        model.addAttribute("message","Successfully logout.");
     return "loginpage/loginpage";
    }

}
