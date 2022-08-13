package com.oda.controller.admin;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.FileStorageComponent;
import com.oda.dto.admin.ReportUploadDto;
import com.oda.dto.doctor.ApplyDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.dto.patient.SearchDto;
import com.oda.dto.response.ResponseDto;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import com.oda.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private final AdminService adminService;


    @GetMapping("/home")
    public String getAdminHomePage(Model model){
        if (AuthorizedUser.getAdmin() != null){
            model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
            model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());
        }else {
            return "redirect:/login";
        }
        return "admin/adminhomepage";
    }

    @GetMapping("/doctor-view")
    public String getDoctorViewPage(Model model){
        model.addAttribute("doctorDetails",
                applyHospitalService.
                        findApplyHospitalListByHospitalBooked(AuthorizedUser.getAdmin().getHospital().getId()));
        model.addAttribute("searchDto",new SearchDto());

        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/viewdoctor";
    }

    @GetMapping("/doctor-request")
    public String getDoctorRequestPage(Model model){
        model.addAttribute("doctorRequest",
                applyHospitalService.findApplyHospitalListOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/doctorrequest";
    }
    @GetMapping("/patient-view")
    public String getPatientViewPage(Model model){
        model.addAttribute("patientRequestDetails",
                applyAppointmentService.findAppointmentForHospitalOfBooked(AuthorizedUser.getAdmin().getHospital().getId()));
       model.addAttribute("searchDto",new SearchDto());
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/viewpatient";
    }


    @GetMapping("/patient-request")
    public String getPatientRequestPage(Model model){
        model.addAttribute("patientRequest",
                applyAppointmentService.findAppointmentForHospitalOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/patientrequest";
    }

//    @GetMapping("/patient-request")
//    public String getPatientRequestPage(Model model) throws IOException {
//        model.addAttribute("appointmentList",applyAppointmentService.getPatientAppointmentDetails(AuthorizedUser.getAdmin().getHospital().getId()));
//        // doctor and patient request count
//        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
//        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());
//
//        return "admin/cart_patientrequest";
//    }

    @GetMapping("/forward-patient/{id}")
    public String getAcceptPatient(@PathVariable("id")Integer id, Model model){
      ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
      //update appointment status
       ApplyAppointment applyAppointment1 = applyAppointmentService.updateByAdmin(applyAppointment);
        if(applyAppointment1 != null){
            model.addAttribute("message","Successfully Forwarded.");
        }else {
            model.addAttribute("message","Failed To Forward");
        }
        model.addAttribute("patientRequest",
                applyAppointmentService.findAppointmentForHospitalOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/patientrequest";
    }

    @GetMapping("/accept-doctor/{id}")
    public String getAcceptDoctor(@PathVariable("id")Integer id, Model model){
     ApplyDto applyHospital = applyHospitalService.findById(id);
        //update appointment status
        applyHospitalService.update(applyHospital);

        model.addAttribute("doctorRequest",
                applyHospitalService.findApplyHospitalListOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/doctorrequest";
    }

    @PostMapping("/doctor-search")
    public String getListOfDoctor(@ModelAttribute("searchDto")SearchDto searchDto,Model model){
        model.addAttribute("doctorDetails",
                applyHospitalService.findDoctorApplyDetailsByContact(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/viewdoctor";
    }

    @PostMapping("/patient-search")
    public String getListOfPatient(@ModelAttribute("searchDto")SearchDto searchDto, Model model){
        model.addAttribute("patientRequestDetails",applyAppointmentService.findAppointMentByPatientId(searchDto.getUserInput()));
        model.addAttribute("searchDto",new SearchDto());
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/viewpatient";
    }

    @GetMapping("/patient-appointment-delete/{id}")
    public String getDeletePatientAppointMent(@PathVariable("id")Integer id, Model model){
        applyAppointmentService.deleteBYId(id);
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "redirect:/admin/patient-view";
    }

    @GetMapping("/doctor-hospital-apply-delete/{id}")
    public String getDeletDoctorHospitalApply(@PathVariable("id")Integer id, Model model){
        applyHospitalService.deleteById(id);
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "redirect:/admin/doctor-view";
    }

    @GetMapping("/patient-view/{id}")
    public String viewPatient(@PathVariable("id")Integer id,Model model) throws IOException {
       ApplyAppointment applyAppointment = applyAppointmentService.findById(id);
        //find patientby Id
       PatientDto patientDto = patientService.findById(applyAppointment.getPatient().getId());
       model.addAttribute("appointmentDetails",applyAppointment);
        model.addAttribute("patientDetails",patientDto);
        model.addAttribute("ppPath",patientDto.getProfilePhotoPath());
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/viewpatientone";
    }

    @GetMapping("/doctor-view/{id}")
    public String viewDoctor(@PathVariable("id")Integer id,Model model) throws IOException {
        ApplyDto applyHospital = applyHospitalService.findById(id);
        //find patientby Id
        DoctorDto doctorDto = doctorService.findById(applyHospital.getDoctor().getId());
        model.addAttribute("doctorDetails",doctorDto);
        model.addAttribute("ppPath",doctorDto.getProfilePhotoPath());
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/viewdoctorone";
    }

    @GetMapping("/delete-patient/{id}")
    public String deletePatientRequest(@PathVariable("id")Integer id,Model model){
        applyAppointmentService.deleteBYId(id);
        model.addAttribute("message","Successfully deleted.");
        model.addAttribute("patientRequest",
                applyAppointmentService.findAppointmentForHospitalOfPending(AuthorizedUser.getAdmin().getHospital().getId()));
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/patientrequest";
    }

    @GetMapping("/reject-doctor/{id}")
    public String deleteDoctorRequest(@PathVariable("id")Integer id,Model model){
        applyHospitalService.deleteById(id);
        model.addAttribute("message","Successfully Rejected.");
        model.addAttribute("doctorRequest",
                applyHospitalService.findApplyHospitalListOfPending(AuthorizedUser.getAdmin().getHospital().getId()));

        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());

        return "admin/doctorrequest";
    }

    @GetMapping("/upload-reports/{id}")
    public String uploadReport(@PathVariable("id") Integer id, Model model){
        ReportUploadDto reportUploadDto = new ReportUploadDto();
        reportUploadDto.setAppointmentId(id);
        model.addAttribute("reportUploadDto",reportUploadDto);
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());
        return "admin/uploadreport";
    }

    @PostMapping("/report-uploaded")
    public String uploaded(@ModelAttribute("reportUploadDto") ReportUploadDto reportUploadDto,Model model) throws IOException {
        model.addAttribute("reportUploadDto",reportUploadDto);

        ResponseDto responseDto = FileStorageComponent.uploaedReport(reportUploadDto.getMultipartFileReport());
        reportUploadDto.setReportUrl(responseDto.getMessage());
       ReportUploadDto reportUploadDto1 = adminService.uploadedReport(reportUploadDto);
        if (reportUploadDto1.getId() != null){
            model.addAttribute("message","Report uploaded successfully");
        }else {
            model.addAttribute("message","Unable to upload report");
        }
        // doctor and patient request count
        model.addAttribute("patientRequestSize",applyAppointmentService.getTodayAppointmentSize());
        model.addAttribute("doctorRequestSize",applyHospitalService.getTodayDoctorApply());
        return "admin/uploadreport";
    }
}
