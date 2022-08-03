package com.oda.controller.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.service.Impl.HospitalServiceImpl;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import com.oda.service.Impl.patient.ApplyAppointmentServiceImpl;
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
 * @Date 4/30/22
 */
@Controller
@RequestMapping("doctor-hospital")
public class HospitalApplyController {
    private final HospitalServiceImpl hospitalService;
    private final ApplyHospitalServiceImpl applyHospitalService;

    private final ApplyAppointmentServiceImpl applyAppointmentService;

    public HospitalApplyController(HospitalServiceImpl hospitalService, ApplyHospitalServiceImpl applyHospitalService, ApplyAppointmentServiceImpl applyAppointmentService) {
        this.hospitalService = hospitalService;
        this.applyHospitalService = applyHospitalService;
        this.applyAppointmentService = applyAppointmentService;
    }

    @GetMapping("/apply")
    public String getHospitalApplyPage(Model model){
        model.addAttribute("applyDto",new ApplyDto());
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("hospitalList",hospitalService.findALl());
        //patient booking request count
        model.addAttribute("patientrequest_count",applyAppointmentService.countApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId()));
        return "doctor/applyhospital";
    }

    @PostMapping("/apply")
    public String hospitalApplyPage( @ModelAttribute("applyDto")ApplyDto applyDto, Model model) throws ParseException {
            //save into database
           ApplyDto applyDto1 =  applyHospitalService.save(applyDto);
           if(applyDto1 !=null){
               model.addAttribute("message","Apply successfully.");
           }else {
               model.addAttribute("message","You already applied to this hospital.");
           }
        model.addAttribute("applyDto",new ApplyDto());
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("hospitalList",hospitalService.findALl());
        return "doctor/applyhospital";
    }
}
