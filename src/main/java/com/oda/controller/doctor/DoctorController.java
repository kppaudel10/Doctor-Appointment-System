package com.oda.controller.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.mail.MailSend;
import com.oda.dto.MailSendDto;
import com.oda.dto.patient.PatientDto;
import com.oda.enums.ApplyStatus;
import com.oda.model.patient.ApplyAppointment;
import com.oda.repo.doctor.DoctorRepo;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.FeedbackServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/16/22
 */
@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorServiceImpl doctorService;
    private final FeedbackServiceImpl feedbackService;
    private final ApplyAppointmentServiceImpl applyAppointmentService;

    private final PatientServiceImpl patientService;

    private final DoctorRepo doctorRepo;



    @GetMapping("/home")
    public String getDoctorHomePage(Model model){
       if (AuthorizedUser.getDoctor() != null){
           model.addAttribute("ppPath",
                   AuthorizedUser.getDoctor().getProfilePhotoPath());
           model.addAttribute("appointmentList",applyAppointmentService.getListOfToDayPatient());
           //patient booking request count
           model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
       }else {
           return "redirect:/login";
       }
        return "doctor/home";
    }
    
    @GetMapping("/patient-booking-request")
    public String bookingRequest(Model model){
        model.addAttribute("ppPath",
                AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("appointmentList",applyAppointmentService.findAppointmentThatIsBooked());
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/bookingrequest";
    }

    @GetMapping("/feedback")
    public String getDoctorFeedback(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
       model.addAttribute("feedbackList",
               feedbackService.findFeedbackBYDoctorID(AuthorizedUser.getDoctor().getId()));
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/doctorfeedback";
    }

    @GetMapping("/booking/request")
    public String getBookReuestPage(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/bookingrequest";
    }

    @GetMapping("/apply/hospital")
    public String getApply(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/applyhospital";
    }

    @GetMapping("/view")
    public String getViewProfile(Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("doctorDetails",AuthorizedUser.getDoctor());
        model.addAttribute("hospitalList",doctorRepo.getHospitalByDoctorId(AuthorizedUser.getDoctor().getId()));
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/profileview";
    }
    @GetMapping("/accept-patient/add-time/{id}")
    public String addMoreTimeOneUserAccept(@PathVariable("id")Integer id, Model model){
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
        model.addAttribute("appointment",applyAppointment);
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/furtherdetailsforbooked";
    }
    @PostMapping("/accept-patient")
    public String getAcceptPatient(@ModelAttribute("appointment") ApplyAppointment applyAppointmentByForm, Model model) throws EmailException {
        ApplyAppointment applyAppointment = applyAppointmentService.findById(applyAppointmentByForm.getId());
        applyAppointment.setVisitTime(ApplyAppointment.getTimeWithAmPm(applyAppointmentByForm.getVisitTime()));
        applyAppointment.setApplyStatus(ApplyStatus.BOOKED);
        //update appointment status
      ApplyAppointment applyAppointment1=  applyAppointmentService.updateByDoctor(applyAppointment);
      if(applyAppointment1 !=null){
          //send confirm mail to patient
          MailSendDto mailSendDto = new MailSendDto();
          mailSendDto.setUserName(applyAppointment.getPatient().getName());
          mailSendDto.setMessage("Your request for doctor appointment has been confirmed,\nFor date "
                  +applyAppointment1.getAppointmentDate()+" Time "+applyAppointment1.getFromTime()+" \n" +
                  "Please Visit hospital on time.\nThank you..");
          mailSendDto.setEmail(applyAppointment1.getPatient().getEmail());
          MailSend mailSend = new MailSend();
          mailSend.sendConfirmMail(mailSendDto);

          model.addAttribute("message","Request accepted successfully.");
      }else {
          model.addAttribute("message","Failed to accept request.");
      }
        model.addAttribute("ppPath",
                AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("appointmentList",applyAppointmentService.findAppointmentThatIsBooked());
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
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
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/bookingrequest";
    }

    @GetMapping("/patient-view/{id}")
    public String getViewPatient(@PathVariable("id")Integer id,Model model) throws IOException {
        ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
        //find patientby Id
        PatientDto patientDto = patientService.findById(applyAppointment.getPatient().getId());
        model.addAttribute("appointmentDetails",applyAppointment);
        model.addAttribute("patientDetails",patientDto);
        model.addAttribute("ppPath",patientDto.getProfilePhotoPath());
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/viewpatientone";
    }
    
}
