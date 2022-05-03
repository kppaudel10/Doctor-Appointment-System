package com.oda.controller.admin;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.dto.patient.SearchDto;
import com.oda.model.doctor.ApplyHospital;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import com.sun.mail.imap.protocol.MODSEQ;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/accept-patient/{id}")
    public String getAcceptPatient(@PathVariable("id")Integer id, Model model){
      ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
      //update appointment status
        applyAppointmentService.update(applyAppointment);

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

    @GetMapping("/doctor-search")
    public String getListOfDoctor(@ModelAttribute("searchDto")SearchDto searchDto,Model model){
        model.addAttribute("doctorDetails",
                doctorService.findDoctorByMobileOrEmail(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        return "admin/viewdoctor";
    }

    @GetMapping("/patient-search")
    public String getListOfPatient(@ModelAttribute("searchDto")SearchDto searchDto, Model model){
        model.addAttribute("patientRequestDetails",patientService.findPatientByMobileOrEmail(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        return "admin/viewpatient";
    }
}
