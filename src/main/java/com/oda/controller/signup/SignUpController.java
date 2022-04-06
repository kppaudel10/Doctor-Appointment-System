package com.oda.controller.signup;

import com.oda.component.mail.MailSend;
import com.oda.dto.MailSendDto;
import com.oda.dto.admin.AdminDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.service.Impl.AdminServiceImpl;
import com.oda.service.Impl.DoctorServiceImpl;
import com.oda.service.Impl.HospitalServiceImpl;
import com.oda.service.Impl.PatientServiceImpl;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final DoctorServiceImpl doctorService;
    private final AdminServiceImpl adminService;
    private final PatientServiceImpl patientService;
    private final HospitalServiceImpl hospitalService;

    public SignUpController(DoctorServiceImpl doctorService, AdminServiceImpl adminService, PatientServiceImpl patientService, HospitalServiceImpl hospitalService) {
        this.doctorService = doctorService;
        this.adminService = adminService;
        this.patientService = patientService;
        this.hospitalService = hospitalService;
    }

    @GetMapping("/who")
    public String getChooseSignUp(){
        return "signup/signupformchoose";
    }

    @GetMapping("/doctor")
    public String getDoctorSignUpPage(Model model){
        model.addAttribute("doctorDto",new DoctorDto());
        return "doctor/doctorregisterpage";
    }

    @GetMapping("/admin")
    public String getAdminSignUpPage(Model model){
        model.addAttribute("adminDto",new AdminDto());
        model.addAttribute("hospitalList",hospitalService.findALl());
        return "admin/adminregisterpage";
    }

    @GetMapping("/patient")
    public String getPatientSignUpPage(Model model){
        model.addAttribute("patientDto", new PatientDto());
        return "patient/patientregisterpage";
    }

    @PostMapping("/patient")
    public String savePatient(@Valid @ModelAttribute("patientDto")PatientDto patientDto,
                              BindingResult bindingResult,Model model) throws EmailException {
        if (!bindingResult.hasErrors()){
            if(patientDto.getPassword().equals(patientDto.getReEnterPassword())){
                //send pin verification code
                MailSendDto mailSendDto = new MailSendDto(patientDto.getName(),patientDto.getEmail(),"Use This pin code for verification.");

                Integer pinCode = new MailSend().sendMail(mailSendDto);

                patientDto.setCorrectPinCode(pinCode);
                //go to verification page
                model.addAttribute("patientDto",patientDto);
                return "patient/emailverification";
            }else {
                model.addAttribute("passwordMatchMsg","password does not match");
            }

        }
        model.addAttribute("patientDto",patientDto);
        return "patient/patientregisterpage";
    }
    @PostMapping("/patient/verify")
    public String verifiyPatientPage(@ModelAttribute("patientDto")PatientDto patientDto,Model model) throws ParseException {
        if (patientDto.getCorrectPinCode().equals(patientDto.getUserPinCode())){
            //then save into database
           PatientDto patientDto1 = patientService.save(patientDto);
           if(patientDto1 !=null){
               model.addAttribute("message","Your account created successfully");
           }else {
               model.addAttribute("message","Unable to create your account");
           }
        }else {
            model.addAttribute("message","invalid pin code");
            return "patient/emailverification";
        }
        model.addAttribute("patientDto",patientDto);
        return "patient/patientregisterpage";
    }

    @PostMapping("/doctor")
    public String saveDoctor(@Valid @ModelAttribute("doctorDto")DoctorDto doctorDto,
                             BindingResult bindingResult,Model model) throws EmailException {
        if (!bindingResult.hasErrors()){
          if (doctorDto.getPassword().equals(doctorDto.getReEnterPassword())){
              //if no error then send email and verify that
              MailSendDto mailSendDto = new MailSendDto(doctorDto.getName(),doctorDto.getEmail(),"Use this pin code for verification");

              Integer pinCode = new MailSend().sendMail(mailSendDto);

              doctorDto.setCorrectPinCode(pinCode);
              model.addAttribute("doctorDto",doctorDto);

              return "doctor/doctoremailverify";
          }else {
              model.addAttribute("messagePassword","Password must match.");
          }

        }
            model.addAttribute("doctorDto",doctorDto);
            return "doctor/doctorregisterpage";
    }

    @PostMapping("/doctor/verify")
    public String doctorEmailVerify(@ModelAttribute("doctorDto")DoctorDto doctorDto, Model model){
        if (doctorDto.getCorrectPinCode().equals(doctorDto.getUserPinCode())){
            //then save into database
           DoctorDto doctorDto1 = doctorService.save(doctorDto);
           if (doctorDto1 !=null){
               model.addAttribute("message","Your account created successfully.");
           }else {
               model.addAttribute("message","Failed to create account.");
           }
           model.addAttribute("doctorDto",doctorDto);
           return "doctor/doctorregisterpage";

        }else {
            model.addAttribute("message","Invalid pin code");
            model.addAttribute("doctorDto",doctorDto);
            return "doctor/doctoremailverify";
        }
    }

}
