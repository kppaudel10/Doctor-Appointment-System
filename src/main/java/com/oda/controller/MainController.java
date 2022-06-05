package com.oda.controller;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.mail.MailSend;
import com.oda.conversion.DtoEntityConversion;
import com.oda.dto.MailSendDto;
import com.oda.dto.PasswordResetDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.login.LoginDto;
import com.oda.dto.patient.PatientDto;
import com.oda.enums.UserStatus;
import com.oda.model.admin.Admin;
import com.oda.service.Impl.AdminServiceImpl;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.LoginServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
               DoctorDto doctor = doctorService.findByUserName(loginDto.getUsername());
               AuthorizedUser.setDoctor(new DtoEntityConversion().getDoctor(doctor));
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
       UserStatus userStatus = AuthorizedUser.getUserStatus();
//       if (userStatus.equals(UserStatus.ADMIN)){
           AuthorizedUser.setAdmin(null);
//       }else if(userStatus.equals(UserStatus.DOCTOR)){
           AuthorizedUser.setDoctor(null);
//       }else if(userStatus.equals(UserStatus.PATIENT)) {
           AuthorizedUser.setPatient(null);
//       }
        model.addAttribute("message","Successfully logout.");
       model.addAttribute("loginDto",new LoginDto());
     return "loginpage/loginpage";
    }


    @GetMapping("/forget-password")
    public String forgetPassword(Model model){
        model.addAttribute("passwordResetDto",new PasswordResetDto());
        return "signup/forgetPassword";
    }

    @PostMapping("/forget-password/verify")
    public String forgetPasswordVerify(@ModelAttribute("passwordResetDto") PasswordResetDto passwordResetDto,Model model) throws EmailException {
        if (loginService.isEmailExists(passwordResetDto) == false){
            model.addAttribute("message","Enter valid email address.");
            return "signup/forgetPassword";
        }
        else {
            model.addAttribute("passwordResetDto",passwordResetDto);
            MailSend mailSend = new MailSend();
            MailSendDto mailSendDto = new MailSendDto("You",passwordResetDto.getEmail(),"Use this pin code for verification");
           Integer validPinCode = mailSend.sendMail(mailSendDto);
            passwordResetDto.setPinCode(validPinCode);
            return "signup/forgetPasswordPinVerify";
        }
    }

    @PostMapping("/forget-password/pin-verify")
    public String forgetPasswordPinVerify(@ModelAttribute("passwordResetDto") PasswordResetDto passwordResetDto,Model model){
        if (!passwordResetDto.getPinCode().equals(passwordResetDto.getUserInputPinCode())){
            model.addAttribute("message","invalid pin code");
            model.addAttribute("passwordResetDto",passwordResetDto);
            return "signup/forgetPasswordPinVerify";
        }
        model.addAttribute("passwordResetDto",passwordResetDto);
        return "signup/createNewPassword";
    }

    @PostMapping("/create-new-password")
    public String createNewPassword(@Valid @ModelAttribute("passwordResetDto") PasswordResetDto passwordResetDto,
                                    Model model, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            //Todo
        }
        model.addAttribute("passwordResetDto",passwordResetDto);
        return "signup/createNewPassword";
    }


}
