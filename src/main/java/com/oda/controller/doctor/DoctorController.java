package com.oda.controller.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.enums.ApplyStatus;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.FeedbackServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/16/22
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorServiceImpl doctorService;
    private final FeedbackServiceImpl feedbackService;
    private final ApplyAppointmentServiceImpl applyAppointmentService;

    public DoctorController(DoctorServiceImpl doctorService, FeedbackServiceImpl feedbackService, ApplyAppointmentServiceImpl applyAppointmentService) {
        this.doctorService = doctorService;
        this.feedbackService = feedbackService;
        this.applyAppointmentService = applyAppointmentService;
    }

    @GetMapping("/home")
    public String getDoctorHomePage(Model model){
        model.addAttribute("ppPath",
                AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("appointmentList",applyAppointmentService.getListOfToDayPatient());
        return "doctor/home";
    }
    
    @GetMapping("/patient-booking-request")
    public String bookingRequest(Model model){
        model.addAttribute("ppPath",
                AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("appointmentList",applyAppointmentService.findAppointmentThatIsBooked());
        return "doctor/bookingrequest";
    }

    @GetMapping("/feedback")
    public String getDoctorFeedback(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
       model.addAttribute("feedbackList",
               feedbackService.findFeedbackBYDoctorID(AuthorizedUser.getDoctor().getId()));
        return "doctor/doctorfeedback";
    }

    @GetMapping("/booking/request")
    public String getBookReuestPage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        return "doctor/bookingrequest";
    }

    @GetMapping("/apply/hospital")
    public String getApply(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        return "doctor/applyhospital";
    }

    @GetMapping("/view")
    public String getViewProfile(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("doctorDetails",AuthorizedUser.getDoctor());
        return "doctor/profileview";
    }

    @GetMapping("/accept-patient/{id}")
    public String getAcceptPatient(@PathVariable("id")Integer id, Model model){
        ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
        applyAppointment.setApplyStatus(ApplyStatus.BOOKED);
        //update appointment status
      ApplyAppointment applyAppointment1=  applyAppointmentService.updateByDoctor(applyAppointment);
      if(applyAppointment1 !=null){
          model.addAttribute("message","Request accepted successfully.");
      }else {
          model.addAttribute("message","Failed to accept request.");
      }
        model.addAttribute("ppPath",
                AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("appointmentList",applyAppointmentService.findAppointmentThatIsBooked());
        return "doctor/bookingrequest";

    }

    @GetMapping("/reject-patient/{id}")
    public String getRejectPatient(@PathVariable("id")Integer id, Model model){
        ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
        applyAppointment.setApplyStatus(ApplyStatus.REJECTED);
        //update appointment status
        ApplyAppointment applyAppointment1=  applyAppointmentService.updateByDoctor(applyAppointment);
        if(applyAppointment1 !=null){
            model.addAttribute("message","Request Rejected successfully.");
        }else {
            model.addAttribute("message","Failed to Reject request.");
        }
        model.addAttribute("ppPath",
                AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("appointmentList",applyAppointmentService.findAppointmentThatIsBooked());
        return "doctor/bookingrequest";
    }
    
}
