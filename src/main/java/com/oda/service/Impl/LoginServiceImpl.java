package com.oda.service.Impl;

import com.oda.dto.PasswordResetDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.enums.UserStatus;
import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import com.oda.repo.admin.AdminRepo;
import com.oda.repo.doctor.DoctorRepo;
import com.oda.repo.patient.PatientRepo;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.Impl.patient.PatientServiceImpl;
import com.oda.utils.PasswordEncryption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/16/22
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl {
    private final AdminServiceImpl adminService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;

    private final PasswordEncryption passwordEncryption;

    private final AdminRepo adminRepo;

    private final DoctorRepo doctorRepo;

    private final PatientRepo patientRepo;


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

    public Boolean updatePassword(PasswordResetDto passwordResetDto){
        Boolean result = true;
        String encryptedPassword = passwordEncryption.getEncryptedPassword(passwordResetDto.getNewPassword());
        if (passwordResetDto.getUserStatus().equals(UserStatus.ADMIN)){
            Admin admin = adminService.findByUserName(passwordResetDto.getEmail());
            if (admin == null)
                result = false;
            else
                adminRepo.updatePassword(encryptedPassword,admin.getId());
        }
        else if (passwordResetDto.getUserStatus().equals(UserStatus.DOCTOR)){
            Doctor doctor = doctorService.findByEmail(passwordResetDto.getEmail());
            if (doctor == null)
                result = false;
            else
                doctorRepo.updatePassword(encryptedPassword,doctor.getId());
        }
        else {
            Patient patient = patientService.findByEmail(passwordResetDto.getEmail());
            if (patient == null)
                result = false;
            else
                patientRepo.updatePassword(encryptedPassword,patient.getId());
        }
        return result;
    }
}
