package com.oda.service.Impl;

import com.oda.dto.PasswordResetDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.enums.UserStatus;
import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import com.oda.utils.PasswordEncryption;
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

    private final PasswordEncryption passwordEncryption;

    public LoginServiceImpl(AdminServiceImpl adminService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, PasswordEncryption passwordEncryption) {
        this.adminService = adminService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.passwordEncryption = passwordEncryption;
    }

    public UserStatus getLogin(String userName, String password) throws IOException {
        UserStatus userStatus = null;
        //encrypt user request password
        String encryptedPassword = passwordEncryption.getEncryptedPassword(password);
        for (Integer i = 0 ;i<3;i++){
            if (i == 0){
               PatientDto patient =  patientService.findByUserName(userName);
               if(patient !=null){
                   if (patient.getPassword().equals(encryptedPassword)){
                       userStatus = UserStatus.PATIENT;
                       break;
                   }
               }
            }

           if(i == 1){
                DoctorDto doctor = doctorService.findByUserName(userName);
                if (doctor !=null){
                    if (doctor.getPassword().equals(encryptedPassword)){
                        userStatus = UserStatus.DOCTOR;
                        break;
                    }
                }
            }
           if(i == 2){
               Admin admin = adminService.findByUserName(userName);
               if (admin !=null){
                   if (admin.getPassword().equals(encryptedPassword)){
                       userStatus = UserStatus.ADMIN;
                   }
               }
            }
        }
        return userStatus;
    }

    public Boolean isEmailExists(PasswordResetDto passwordResetDto){
        Boolean result = false;
        for (Integer i = 0;i<3 ;i++){
            if (passwordResetDto.getUserStatus().equals(UserStatus.ADMIN)){
                //check in admin repo
                if (adminService.checkEmailDuplicate(passwordResetDto.getEmail())  != 0 ){
                    result = true;
                }
                break;
            }
            else if(passwordResetDto.getUserStatus().equals(UserStatus.DOCTOR)){
                if (doctorService.checkEmailDuplicate(passwordResetDto.getEmail()) !=0){
                    result = true;
                }
                break;
            }else {
                if (patientService.checkEmailDuplicate(passwordResetDto.getEmail()) !=0){
                    result = true;
                }
            }
        }
        return result;
    }

    public void createAndUpdatePassword(PasswordResetDto passwordResetDto) throws IOException {
        String encryptedPassword = passwordEncryption.getEncryptedPassword(passwordResetDto.getNewPassword());
        if (passwordResetDto.getUserStatus().equals(UserStatus.ADMIN)){
          Admin admin = adminService.findByUserName(passwordResetDto.getEmail());
          admin.setPassword(encryptedPassword);
        }
        else if (passwordResetDto.getUserStatus().equals(UserStatus.DOCTOR)){
           Doctor doctor = doctorService.findByEmail(passwordResetDto.getEmail());
           doctor.setPassword(encryptedPassword);
        }
        else {
         Patient patient = patientService.findByEmail(passwordResetDto.getEmail());
         patient.setPassword(encryptedPassword);
        }
    }
}
