package com.oda.controller.admin;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.model.doctor.ApplyHospital;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
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
@RequestMapping("/admin")
public class AdminController {
    private final ApplyAppointmentServiceImpl applyAppointmentService;
    private final ApplyHospitalServiceImpl applyHospitalService;

    public AdminController(ApplyAppointmentServiceImpl applyAppointmentService, ApplyHospitalServiceImpl applyHospitalService) {
        this.applyAppointmentService = applyAppointmentService;
        this.applyHospitalService = applyHospitalService;
    }

    @GetMapping("/home")
    public String getAdminHomePage(Model model){
        return "admin/adminhomepage";
    }

    @GetMapping("/doctor-view")
    public String getDoctorViewPage(Model model){
        model.addAttribute("doctorDetails",
                applyHospitalService.
                        findApplyHospitalListByHospitalBooked(AuthorizedUser.getAdmin().getHospital().getId()));
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

        model.addAttribute("patientRequest",
                applyAppointmentService.findAppointmentForHospitalOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        return "admin/patientrequest";
    }
}
