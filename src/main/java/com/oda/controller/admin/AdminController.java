package com.oda.controller.admin;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.dto.patient.SearchDto;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ApplyAppointmentServiceImpl applyAppointmentService;
    private final ApplyHospitalServiceImpl applyHospitalService;

    private final DoctorServiceImpl doctorService;

    private final PatientServiceImpl patientService ;


    @GetMapping("/home")
    public String getAdminHomePage(Model model){
        return "admin/adminhomepage";
    }

    @GetMapping("/doctor-view")
    public String getDoctorViewPage(Model model){
        model.addAttribute("doctorDetails",
                applyHospitalService.
                        findApplyHospitalListByHospitalBooked(AuthorizedUser.getAdmin().getHospital().getId()));
        model.addAttribute("searchDto",new SearchDto());
        return "admin/viewdoctor";
    }

    @GetMapping("/doctor-request")
    public String getDoctorRequestPage(Model model){
        model.addAttribute("doctorRequest",
                applyHospitalService.findApplyHospitalListOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        return "admin/doctorrequest";
    }
    @GetMapping("/patient-view")
    public String getPatientViewPage(Model model){
        model.addAttribute("patientRequestDetails",
                applyAppointmentService.findAppointmentForHospitalOfBooked(AuthorizedUser.getAdmin().getHospital().getId()));
       model.addAttribute("searchDto",new SearchDto());
        return "admin/viewpatient";
    }


    @GetMapping("/patient-request")
    public String getPatientRequestPage(Model model){
        model.addAttribute("patientRequest",
                applyAppointmentService.findAppointmentForHospitalOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        return "admin/patientrequest";
    }

    @GetMapping("/forward-patient/{id}")
    public String getAcceptPatient(@PathVariable("id")Integer id, Model model){
      ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
      //update appointment status
        applyAppointmentService.updateByAdmin(applyAppointment);

        model.addAttribute("patientRequest",
                applyAppointmentService.findAppointmentForHospitalOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        return "admin/patientrequest";
    }

    @GetMapping("/accept-doctor/{id}")
    public String getAcceptDoctor(@PathVariable("id")Integer id, Model model){
     ApplyDto applyHospital = applyHospitalService.findById(id);
        //update appointment status
        applyHospitalService.update(applyHospital);

        model.addAttribute("doctorRequest",
                applyHospitalService.findApplyHospitalListOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        return "admin/doctorrequest";
    }

    @PostMapping("/doctor-search")
    public String getListOfDoctor(@ModelAttribute("searchDto")SearchDto searchDto,Model model){
        model.addAttribute("doctorDetails",
                applyHospitalService.findDoctorApplyDetailsByContact(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        return "admin/viewdoctor";
    }

    @PostMapping("/patient-search")
    public String getListOfPatient(@ModelAttribute("searchDto")SearchDto searchDto, Model model){
        model.addAttribute("patientRequestDetails",applyAppointmentService.findAppointMentByPatientId(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        return "admin/viewpatient";
    }

    @GetMapping("/patient-appointment-delete/{id}")
    public String getDeletePatientAppointMent(@PathVariable("id")Integer id, Model model){
        applyAppointmentService.deleteBYId(id);
        return "redirect:/admin/patient-view";
    }

    @GetMapping("/doctor-hospital-apply-delete/{id}")
    public String getDeletDoctorHospitalApply(@PathVariable("id")Integer id, Model model){
        applyHospitalService.deleteById(id);
        return "redirect:/admin/doctor-view";
    }

    @GetMapping("/patient-view/{id}")
    public String viewPatient(@PathVariable("id")Integer id,Model model) throws IOException {
       ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
        //find patientby Id
       PatientDto patientDto = patientService.findById(applyAppointment.getPatient().getId());
        model.addAttribute("patientDetails",patientDto);
        model.addAttribute("ppPath",patientDto.getProfilePhotoPath());
        return "admin/viewpatientone";
    }

    @GetMapping("/doctor-view/{id}")
    public String viewDoctor(@PathVariable("id")Integer id,Model model) throws IOException {
        ApplyDto applyHospital = applyHospitalService.findById(id);
        //find patientby Id
        DoctorDto doctorDto = doctorService.findById(applyHospital.getDoctor().getId());
        model.addAttribute("doctorDetails",doctorDto);
        model.addAttribute("ppPath",doctorDto.getProfilePhotoPath());
        return "admin/viewdoctorone";
    }

    @GetMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable("id")Integer id,Model model){
        applyAppointmentService.deleteBYId(id);
        model.addAttribute("message","Successfully deleted.");
        model.addAttribute("patientRequest",
                applyAppointmentService.findAppointmentForHospitalOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        return "admin/patientrequest";
    }
}
