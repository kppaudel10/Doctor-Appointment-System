package com.oda.controller.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.service.Impl.HospitalServiceImpl;
import com.oda.service.Impl.doctor.ApplyHospitalServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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

    public HospitalApplyController(HospitalServiceImpl hospitalService, ApplyHospitalServiceImpl applyHospitalService) {
        this.hospitalService = hospitalService;
        this.applyHospitalService = applyHospitalService;
    }

    @GetMapping("/apply")
    public String getHospitalApplyPage(Model model){
        model.addAttribute("applyDto",new ApplyDto());
        model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        model.addAttribute("hospitalList",hospitalService.findALl());
        return "doctor/applyhospital";
    }

    @PostMapping("/apply")
    public String hospitalApplyPage( @ModelAttribute("applyDto")ApplyDto applyDto, Model model) throws ParseException {
            //save into database
           ApplyDto applyDto1 =  applyHospitalService.save(applyDto);
           if(applyDto1 !=null){
               model.addAttribute("message","Apply successfully.");
           }else {
               model.addAttribute("message","Failed to apply");
           }
            model.addAttribute("ppPath", AuthorizedUser.getDoctor().getProfilePhotoPath());
        return "doctor/applyhospital";
    }
}
