package com.oda.controller.patient;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.GetRating;
import com.oda.dto.patient.FeedbackDto;
import com.oda.dto.patient.SearchDto;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.FeedbackServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public PatientController(FeedbackServiceImpl feedbackService, DoctorServiceImpl doctorService) {
        this.feedbackService = feedbackService;
        this.doctorService = doctorService;
    }

    @GetMapping("/home")
    public String getPatientHomePage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
       model.addAttribute("doctorList",doctorService.finDoctorByAddressByDefault());
       model.addAttribute("searchDto",new SearchDto());
        return "patient/patienthomepage";
    }

    @GetMapping("/feedback")
    public String getFeedbackForm(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
       model.addAttribute("feedbackDto",new FeedbackDto());
        return "patient/feedback";
    }

    @PostMapping("/feedback")
    public String getStoreFeedback(@ModelAttribute("feedbackDto")FeedbackDto feedbackDto,
                                   Model model) throws ParseException {
       //calculate rating
       Integer rating = GetRating.getRating(feedbackDto.getStar_one(),feedbackDto.getStar_two(),
               feedbackDto.getStar_three(),feedbackDto.getStar_four(),feedbackDto.getStar_five());

       //set rating
        feedbackDto.setRating(Double.valueOf(rating));
        //save feedback data
        FeedbackDto feedbackDto1= feedbackService.save(feedbackDto);
        if (feedbackDto1 !=null){
            model.addAttribute("message","Feedback submitted successfully");
        }else {
            model.addAttribute("message","Unable to submit your feedback.");
        }

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

    @GetMapping("/readmore")
    public String getDoctorViewPage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        return "patient/doctorreadmore";
    }

    @PostMapping("/search/doctor")
    public String getSearchDoctor(@ModelAttribute("searchDto")SearchDto searchDto,Model model){
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("doctorList",doctorService.findDoctorByANS(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        return "patient/patienthomepage";
    }
}
