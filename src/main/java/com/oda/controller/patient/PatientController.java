package com.oda.controller.patient;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.GetRating;
import com.oda.dto.doctor.ApplyDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.doctor.DoctorSortDto;
import com.oda.dto.patient.AppointmentDto;
import com.oda.dto.patient.FeedbackDto;
import com.oda.dto.patient.SearchDto;
import com.oda.model.doctor.ApplyHospital;
import com.oda.model.doctor.Doctor;
import com.oda.model.doctor.DoctorAvailable;
import com.oda.model.patient.ApplyAppointment;
import com.oda.repo.doctor.DoctorAvailableRepo;
import com.oda.repo.patient.ApplyAppointmentRepo;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/5/22
 */
@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {
    private final FeedbackServiceImpl feedbackService;
    private final DoctorServiceImpl doctorService;
    private final ApplyHospitalServiceImpl applyHospitalService;
    private final ApplyAppointmentServiceImpl applyAppointmentService;

    private final ApplyAppointmentRepo applyAppointmentRepo;

    private final DoctorAvailableRepo doctorAvailableRepo;


    @GetMapping("/home")
    public String getPatientHomePage(Model model) {
        //if sort value is not low or high
        model.addAttribute("doctorSortDto", new DoctorSortDto());
        model.addAttribute("searchDto", new SearchDto());
        if (AuthorizedUser.getPatient() != null) {
            model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
            model.addAttribute("doctorList", doctorService.finDoctorByAddressByDefault());
        } else {
            return "redirect:/login";
        }
        return "patient/patienthomepage";
    }

    @GetMapping("/sort-doctor")
    public String getSortDoctor(@ModelAttribute("doctorSortDto") DoctorSortDto doctorSortDto, Model model) {
        if (doctorSortDto.getSortValue().equals(1)) {
            model.addAttribute("doctorList", doctorService.sortDoctorByLowCharge());
        } else if (doctorSortDto.getSortValue().equals(2)) {
            model.addAttribute("doctorList", doctorService.sortDoctorByHighCharge());
        } else {
            model.addAttribute("doctorList", doctorService.finDoctorByAddressByDefault());
        }
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        return "patient/patienthomepage";
    }

    @GetMapping("/feedback/{id}")
    public String getFeedbackForm(@PathVariable("id") Integer id, Model model) throws IOException {
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        DoctorDto doctor = doctorService.findById(id);
        model.addAttribute("doctor", doctor);
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setDoctorId(doctor.getId());
        model.addAttribute("feedbackDto", feedbackDto);
        return "patient/feedback";
    }

    @PostMapping("/feedback")
    public String getStoreFeedback(@ModelAttribute("feedbackDto") FeedbackDto feedbackDto, Model model) throws ParseException, IOException {
        if (feedbackService.checkPatientCanGiveFeedBackOrNot(feedbackDto)) {
            //calculate rating
            Integer rating = GetRating.getRating(feedbackDto.getStar_one(), feedbackDto.getStar_two(), feedbackDto.getStar_three(), feedbackDto.getStar_four(), feedbackDto.getStar_five());

            //set rating
            feedbackDto.setRating(Double.valueOf(rating));
            DoctorDto doctor = doctorService.findById(feedbackDto.getDoctorId());
            feedbackDto.setDoctor(Doctor.builder().id(doctor.getId()).build());
            //save feedback data
            FeedbackDto feedbackDto1 = feedbackService.save(feedbackDto);
            model.addAttribute("msg", "Your feedback submitted successfully.");
        } else {
            model.addAttribute("msg", "At least one appointment is required for doctor feedback");
        }

        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        //set doctor id null or zero so that's second time can not give feedback
        DoctorDto doctor = doctorService.findById(feedbackDto.getDoctorId());
        model.addAttribute("doctor", doctor);
        FeedbackDto feedbackDto1 = new FeedbackDto();
        feedbackDto1.setDoctorId(doctor.getId());
        model.addAttribute("feedbackDto", feedbackDto1);
        return "patient/feedback";
    }

    @GetMapping("/view/status")
    public String getViewStatusPage(Model model) {
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("appointDetails", applyAppointmentService.findAppointmentOfPatient(AuthorizedUser.getPatient().getId()));
        return "patient/viewstatuspage";
    }

    @GetMapping("/request")
    public String getRequestPage() {
        return "admin/patientrequest";
    }

    @GetMapping("/profile/view")
    public String getProfileViewPage(Model model) {
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("patientDetails", AuthorizedUser.getPatient());
        return "patient/profileview";
    }

    @GetMapping("/doctor-readmore/{id}")
    public String getDoctorViewPage(@PathVariable("id") Integer id, Model model) throws IOException {
        List<ApplyHospital> applyHospitalList = applyHospitalService.findApplyDetailsOfDoctor(id);
        if (applyHospitalList.size() == 0) {
            model.addAttribute("notWorkMsg", "Not Working Yet.");
        } else {
            model.addAttribute("workingHospitalList", applyHospitalList);

        }
        model.addAttribute("doctor", doctorService.findById(id));
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("doctorId", id);
        return "patient/doctorreadmore";
    }

    @PostMapping("/search/doctor")
    public String getSearchDoctor(@ModelAttribute("searchDto") SearchDto searchDto, Model model) {
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        model.addAttribute("doctorList", doctorService.findDoctorByANS(searchDto.getUserInput()));
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("doctorSortDto", new DoctorSortDto());
        return "patient/patienthomepage";
    }


    @GetMapping("/doctor-appointment/{id}")
    public String getApplyForAppointMnt(@PathVariable("id") Integer id, Model model) throws IOException, ParseException {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setHospitalApplyId(id);
//        appointmentDto.setDoctorId();
        model.addAttribute("appointmentDto", appointmentDto);
        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
        //save log
        return "patient/furtherdetailsforappointment";
    }
    @PostMapping("/doctor-appointment")
    public String getApplyAppointMnt(@Valid @ModelAttribute("appointmentDto") AppointmentDto appointmentDto, BindingResult bindingResult, Model model) throws IOException, ParseException {
        if (!bindingResult.hasErrors()) {
            ApplyDto applyHospital = applyHospitalService.findById(appointmentDto.getHospitalApplyId());
            if (applyHospital != null) {
                //check doctor is available or not
                if (doctorService.checkDoctorAvailability(applyHospital.getDoctor().getId(), appointmentDto.getAppointmentDate(), applyHospital.getHospital().getId()) > 0) {
                    //check if there is already exists appointment or not
                    if (applyAppointmentRepo.countAppointmentOfDatePatient(applyHospital.getDoctor().getId(), AuthorizedUser.getPatient().getId(), applyHospital.getHospital().getId(), appointmentDto.getAppointmentDate()) == 0) {
                        //save log
                        ApplyAppointment applyAppointment = applyAppointmentService.save(applyHospital, appointmentDto);
                        if (applyAppointment != null) {
                            model.addAttribute("msg", "Applied Successfully.");
                        } else {
                            model.addAttribute("msg", "Already submitted.");
                        }
                        model.addAttribute("doctor", doctorService.findById(applyAppointment.getDoctor().getId()));
                        model.addAttribute("workingHospitalList", applyHospitalService.findApplyDetailsOfDoctor(applyAppointment.getDoctor().getId()));
                        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
                        return "patient/doctorreadmore";
                    } else {
                        model.addAttribute("msg", "Appointment already in pending");
                        model.addAttribute("doctor", doctorService.findById(applyHospital.getDoctor().getId()));
                        model.addAttribute("workingHospitalList", applyHospitalService.findApplyDetailsOfDoctor(applyHospital.getDoctor().getId()));
                        model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
                        return "patient/doctorreadmore";
                    }
                }else {
                    model.addAttribute("msg", "Doctor is unavailable in that date");
                    model.addAttribute("doctor", doctorService.findById(applyHospital.getDoctor().getId()));
                    model.addAttribute("workingHospitalList", applyHospitalService.findApplyDetailsOfDoctor(applyHospital.getDoctor().getId()));
                    model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
                    return "patient/doctorreadmore";
                }
            } else {
                return "redirect:/patient/home";
            }
        } else {
            model.addAttribute("message", "Maximum length is 100.");
            model.addAttribute("appointmentDto", appointmentDto);
            model.addAttribute("ppPath", AuthorizedUser.getPatient().getProfilePhotoPath());
            //save log
            return "patient/furtherdetailsforappointment";
        }
    }
}
