package com.oda.controller.patient;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.GetRating;
import com.oda.dto.doctor.ApplyDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.AppointmentDto;
import com.oda.dto.patient.FeedbackDto;
import com.oda.dto.patient.SearchDto;
import com.oda.model.doctor.ApplyHospital;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.FeedbackServiceImpl;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/5/22
 */
@Controller
@RequestMapping("/patient")
public class PatientController {
    private final FeedbackServiceImpl feedbackService;
    private final DoctorServiceImpl doctorService;
    private final ApplyHospitalServiceImpl applyHospitalService;
    private final ApplyAppointmentServiceImpl applyAppointmentService;

    public PatientController(FeedbackServiceImpl feedbackService, DoctorServiceImpl doctorService, ApplyHospitalServiceImpl applyHospitalService, ApplyAppointmentServiceImpl applyAppointmentService) {
        this.feedbackService = feedbackService;
        this.doctorService = doctorService;
        this.applyHospitalService = applyHospitalService;
        this.applyAppointmentService = applyAppointmentService;
    }

    @GetMapping("/home")
    public String getPatientHomePage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
       model.addAttribute("doctorList",doctorService.finDoctorByAddressByDefault());
       model.addAttribute("searchDto",new SearchDto());
        return "patient/patienthomepage";
    }

    @GetMapping("/feedback/{id}")
    public String getFeedbackForm(@PathVariable("id")Integer id, Model model) throws IOException {
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        DoctorDto doctor =doctorService.findById(id);
        model.addAttribute("doctor",doctor);
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setDoctorId(doctor.getId());
       model.addAttribute("feedbackDto",feedbackDto);
        return "patient/feedback";
    }

    @PostMapping("/feedback")
    public String getStoreFeedback(@ModelAttribute("feedbackDto")FeedbackDto feedbackDto,
                                   Model model) throws ParseException, IOException {
       //calculate rating
       Integer rating = GetRating.getRating(feedbackDto.getStar_one(),feedbackDto.getStar_two(),
               feedbackDto.getStar_three(),feedbackDto.getStar_four(),feedbackDto.getStar_five());

       //set rating
        feedbackDto.setRating(Double.valueOf(rating));
       DoctorDto doctor = doctorService.findById(feedbackDto.getDoctorId());
       feedbackDto.setDoctor(Doctor.builder().id(doctor.getId()).build());
        //save feedback data
        FeedbackDto feedbackDto1= feedbackService.save(feedbackDto);
//        if (feedbackDto1 !=null){
//            model.addAttribute("message","Feedback submitted successfully");
//        }else {
//            model.addAttribute("message","Unable to submit your feedback.");
//        }
//        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        return "redirect:/patient/home";
    }

    @GetMapping("/view/status")
    public String getViewStatusPage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("appointDetails",applyAppointmentService.findAppointmentOfPatient(AuthorizedUser.getPatient().getId()));
        return "patient/viewstatuspage";
    }

    @GetMapping("/request")
    public String getRequestPage(){
        return "admin/patientrequest";
    }

    @GetMapping("/profile/view")
    public String getProfileViewPage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("patientDetails",AuthorizedUser.getPatient());
//        model.addAttribute("pendingAppointment",
//                applyAppointmentService.getTotalPendingAppointmentOfPatient());
//        model.addAttribute("bookedAppointment",
//                applyAppointmentService.getTotalBookedAppointmentOfPatient());

        return "patient/profileview";
    }

    @GetMapping("/doctor-readmore/{id}")
    public String getDoctorViewPage(@PathVariable("id") Integer id,
                                    Model model) throws IOException {
        model.addAttribute("doctor",doctorService.findById(id));
        model.addAttribute("workingHospitalList",applyHospitalService.findApplyDetailsOfDoctor(id));
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("doctorId",id);
        return "patient/doctorreadmore";
    }

    @PostMapping("/search/doctor")
    public String getSearchDoctor(@ModelAttribute("searchDto")SearchDto searchDto,Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("doctorList",doctorService.findDoctorByANS(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        return "patient/patienthomepage";
    }


    @GetMapping("/hospital-appointment/{id}")
    public String getApplyForAppointMnt(@PathVariable("id") Integer id, Model model) throws IOException, ParseException {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setHospitalApplyId(id);
//        appointmentDto.setDoctorId();
        model.addAttribute("appointmentDto",appointmentDto);
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        //save log
        return "patient/furtherdetailsforappointment";
    }

    @PostMapping("/hospital-appointment")
    public String getApplyAppointMnt(@Valid @ModelAttribute("appointmentDto") AppointmentDto appointmentDto, BindingResult bindingResult,
                                     Model model) throws IOException, ParseException {
     if(!bindingResult.hasErrors())
     {
         ApplyDto applyHospital= applyHospitalService.findById(appointmentDto.getHospitalApplyId());

         //save log
         ApplyAppointment applyAppointment= applyAppointmentService.save(applyHospital,appointmentDto);
         if(applyAppointment !=null){
             model.addAttribute("msg","Submitted successfully.");
         }else {
             model.addAttribute("msg","Already submitted.");
         }
         model.addAttribute("doctor",doctorService.findById(applyAppointment.getDoctor().getId()));
         model.addAttribute("workingHospitalList",applyHospitalService.findApplyDetailsOfDoctor(applyAppointment.getDoctor().getId()));
         model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
         return "patient/doctorreadmore";
     }else {
         model.addAttribute("message","Maximum length is 100.");
         model.addAttribute("appointmentDto",appointmentDto);
         model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
         //save log
         return "patient/furtherdetailsforappointment";

     }
    }
}
