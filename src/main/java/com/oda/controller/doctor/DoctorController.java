package com.oda.controller.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.mail.MailSend;
import com.oda.dto.MailSendDto;
import com.oda.dto.patient.PatientDto;
import com.oda.enums.ApplyStatus;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.FeedbackServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getAcceptPatient(@PathVariable("id")Integer id, Model model) throws EmailException {
        ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
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

    @GetMapping("/patient-view/{id}")
    public String getViewPatient(@PathVariable("id")Integer id,Model model) throws IOException {
        ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
        //find patientby Id
        PatientDto patientDto = patientService.findById(applyAppointment.getPatient().getId());
        model.addAttribute("appointmentDetails",applyAppointment);
        model.addAttribute("patientDetails",patientDto);
        model.addAttribute("ppPath",patientDto.getProfilePhotoPath());
        return "doctor/viewpatientone";
    }
    
}
