package com.oda.controller.signup;

import com.oda.component.FileStorageComponent;
import com.oda.component.mail.MailSend;
import com.oda.dto.MailSendDto;
import com.oda.dto.admin.AdminDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.dto.response.ResponseDto;
import com.oda.service.Impl.AdminServiceImpl;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.HospitalServiceImpl;
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
@RequestMapping("/signup")
public class SignUpController {
    private final DoctorServiceImpl doctorService;
    private final AdminServiceImpl adminService;
    private final PatientServiceImpl patientService;
    private final HospitalServiceImpl hospitalService;
    private final FileStorageComponent fileStorageComponent;

    public SignUpController(DoctorServiceImpl doctorService, AdminServiceImpl adminService, PatientServiceImpl patientService, HospitalServiceImpl hospitalService, FileStorageComponent fileStorageComponent) {
        this.doctorService = doctorService;
        this.adminService = adminService;
        this.patientService = patientService;
        this.hospitalService = hospitalService;
        this.fileStorageComponent = fileStorageComponent;
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

    @GetMapping("/patient")
    public String getPatientSignUpPage(Model model){
        model.addAttribute("patientDto", new PatientDto());
        return "patient/patientregisterpage";
    }

    @PostMapping("/patient")
    public String savePatient(@Valid @ModelAttribute("patientDto")PatientDto patientDto,
                              BindingResult bindingResult,Model model) throws EmailException, IOException {
        if (!bindingResult.hasErrors()){
            if (doctorService.checkEmailDuplicate(patientDto.getEmail()) == 0 &&
                    patientService.checkEmailDuplicate(patientDto.getEmail()) == 0 &&
                    adminService.checkEmailDuplicate(patientDto.getEmail()) == 0) {
                if (patientDto.getPassword().equals(patientDto.getReEnterPassword())) {
                    //send pin verification code
                    MailSendDto mailSendDto = new MailSendDto(patientDto.getName(), patientDto.getEmail(), "Use This pin code for verification.");

                    Integer pinCode = new MailSend().sendMail(mailSendDto);

                    patientDto.setCorrectPinCode(pinCode);

                    ResponseDto responseDto = FileStorageComponent.storeFile(patientDto.getMultipartFilePP());

                    patientDto.setProfilePhotoPath(responseDto.getMessage());
                    //go to verification page
                    model.addAttribute("patientDto", patientDto);
                    return "patient/emailverification";
                } else {
                    model.addAttribute("passwordMatchMsg", "password does not match");
                }
            }
            else {
                model.addAttribute("message","Email already exists.");
            }

        }
        model.addAttribute("patientDto",patientDto);
        return "patient/patientregisterpage";
    }
    @PostMapping("/patient/verify")
    public String verifiyPatientPage(@ModelAttribute("patientDto")PatientDto patientDto,Model model) throws ParseException, IOException {
        if (patientDto.getCorrectPinCode().equals(patientDto.getUserPinCode())){

//         ResponseDto responseDto = patientFileStorageComponent.storeFile(patientDto.getMultipartFilePP());
//         if (responseDto.getStatus()){
//             patientDto.setProfilePhotoPath(responseDto.getMessage());
             //then save into database
             PatientDto patientDto1 = patientService.save(patientDto);
             if(patientDto1 !=null){
                 model.addAttribute("message","Your account created successfully");
             }else {
                 model.addAttribute("message","Unable to create your account");
             }
//         }else {
//             model.addAttribute("message","Unable to create your account");
//
//         }

        }else {
            model.addAttribute("message","invalid pin code");
            return "patient/emailverification";
        }
        model.addAttribute("patientDto",patientDto);
        return "patient/patientregisterpage";
    }

    @PostMapping("/doctor")
    public String saveDoctor(@Valid @ModelAttribute("doctorDto")DoctorDto doctorDto,
                             BindingResult bindingResult,Model model) throws EmailException, IOException {
        if (!bindingResult.hasErrors()){
          if (doctorService.checkEmailDuplicate(doctorDto.getEmail()) == 0 &&
          patientService.checkEmailDuplicate(doctorDto.getEmail()) == 0 &&
          adminService.checkEmailDuplicate(doctorDto.getEmail()) == 0){
              if (doctorDto.getPassword().equals(doctorDto.getReEnterPassword())){
                  //if no error then send email and verify that
                  MailSendDto mailSendDto = new MailSendDto(doctorDto.getName(),doctorDto.getEmail(),"Use this pin code for verification");

                  Integer pinCode = new MailSend().sendMail(mailSendDto);

                  doctorDto.setCorrectPinCode(pinCode);
                  ResponseDto responseDto =   FileStorageComponent.storeFile(doctorDto.getMultipartFilePhoto());

                  doctorDto.setProfilePhotoPath(responseDto.getMessage());

                  model.addAttribute("doctorDto",doctorDto);

                  return "doctor/doctoremailverify";
              }else {
                  model.addAttribute("messagePassword","Password must match.");
              }
          }else {
              model.addAttribute("message","Email already exists");
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
    @GetMapping("/admin")
    public String getAdminSignUpPage(Model model){
        model.addAttribute("adminDto",new AdminDto());
        model.addAttribute("hospitalList",hospitalService.findALl());
        return "admin/adminregisterpage";
    }


    @PostMapping("/admin")
    public String saveAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                            BindingResult bindingResult,Model model) throws EmailException {
        if (!bindingResult.hasErrors()){
            if (doctorService.checkEmailDuplicate(adminDto.getEmail()) == 0 &&
                    patientService.checkEmailDuplicate(adminDto.getEmail()) == 0 &&
                    adminService.checkEmailDuplicate(adminDto.getEmail()) == 0) {
                if (adminDto.getPassword().equals(adminDto.getReEnterPassword())) {
                    //if no error then send email and verify that
                    MailSendDto mailSendDto = new MailSendDto(adminDto.getName(), adminDto.getEmail(), "Use this pin code for verification");

                    Integer pinCode = new MailSend().sendMail(mailSendDto);

                    adminDto.setCorrectPincode(pinCode);

                    model.addAttribute("adminDto", adminDto);

                    return "admin/emailverification";
                } else {
                    model.addAttribute("passwordMsg", "Password must match.");
                }
            }else {
                model.addAttribute("message","Email Already exists");
            }

        }
        model.addAttribute("admindto",adminDto);
        model.addAttribute("hospitalList",hospitalService.findALl());
        return "admin/adminregisterpage";

    }

    @PostMapping("/admin/verify")
    public String adminVerifiy(@Valid @ModelAttribute("adminDto")AdminDto adminDto,Model model) throws EmailException {
        if (adminDto.getCorrectPincode().equals(adminDto.getUserPinCode())){
            //then save into database
           AdminDto adminDto1 = adminService.save(adminDto);
            if (adminDto1 !=null){
                model.addAttribute("message","Your account created successfully.");
            }else {
                model.addAttribute("message","Failed to create account.");
            }
            model.addAttribute("adminDto",adminDto);
            return "admin/adminregisterpage";

        }else {
            model.addAttribute("message","Invalid pin code");
            model.addAttribute("adminDto",adminDto);
            return "admin/emailverification";
        }
    }

}
