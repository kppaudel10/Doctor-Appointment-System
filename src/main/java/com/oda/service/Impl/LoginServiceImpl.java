package com.oda.service.Impl;

import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.enums.UserStatus;
import com.oda.model.admin.Admin;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/16/22
 */
@Service
public class LoginServiceImpl {
    private final AdminServiceImpl adminService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;

    public LoginServiceImpl(AdminServiceImpl adminService, PatientServiceImpl patientService, DoctorServiceImpl doctorService) {
        this.adminService = adminService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public UserStatus getLogin(String userName, String password) throws IOException {
        UserStatus userStatus = null;
        //first check in database patient
        for (Integer i = 0 ;i<3;i++){
            if (i == 0){
               PatientDto patient =  patientService.findByUserName(userName);
               if(patient !=null){
                   if (patient.getPassword().equals(password)){
                       userStatus = UserStatus.PATIENT;
                       break;
                   }
               }
            }

           if(i == 1){
                DoctorDto doctor = doctorService.findByUserName(userName);
                if (doctor !=null){
                    if (doctor.getPassword().equals(password)){
                        userStatus = UserStatus.DOCTOR;
                        break;
                    }
                }
            }
           if(i == 2){
               Admin admin = adminService.findByUserName(userName);
               if (admin !=null){
                   if (admin.getPassword().equals(password)){
                       userStatus = UserStatus.ADMIN;
                   }
               }
            }
        }
        return userStatus;
    }
}
