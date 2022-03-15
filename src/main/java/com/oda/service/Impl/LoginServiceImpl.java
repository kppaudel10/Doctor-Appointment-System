package com.oda.service.Impl;

import com.oda.dto.admin.AdminDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl {
    private final AdminServiceImpl adminService;
    private final DoctorServiceImpl doctorService;
    private final PatientServiceImpl patientService;

    public LoginServiceImpl(AdminServiceImpl adminService,
                            DoctorServiceImpl doctorService,
                            PatientServiceImpl patientService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public String isValidUser(String userName, String password) {
        List<PatientDto> patientDtoList = patientService.findALl();
        List<AdminDto> adminDtoList = adminService.findALl();
        List<DoctorDto> doctorDtoList = doctorService.findALl();
        String isValid = null;
        //first verified patient or not
        boolean isPatient = false;
        if (patientDtoList.size() >0){
            for (Integer i = 0; i < patientDtoList.size(); i++) {
                PatientDto patient = patientDtoList.get(i);
                if (patient.getEmail().equals(userName) && patient.getPassword().equals(password)
                        || patient.getMobileNumber().equals(userName) && patient.getPassword().equals(password)) {
                    isPatient = true;
                    break;
                }
            }
        }
        //second verified doctor or not
        boolean isDoctor = false;
        if (doctorDtoList.size()>0){
            if (!isPatient) {
                for (Integer i = 0; i < doctorDtoList.size(); i++) {
                    DoctorDto doctor = doctorDtoList.get(i);
                    if (doctor.getEmail().equals(userName) && doctor.getPassword().equals(password)
                            || doctor.getMobileNumber().equals(userName) && doctor.getPassword().equals(password)) {
                        isDoctor = true;
                        break;
                    }
                }
            }
        }

        //third verified admin or not
        boolean isAdmin = false;
        if(adminDtoList.size()>0){
            if (!isDoctor) {
                for (Integer i = 0; i < adminDtoList.size(); i++) {
                    AdminDto admin = adminDtoList.get(i);
                    if (admin.getEmail().equals(userName) && admin.getPassword().equals(password)
                            || admin.getMobileNumber().equals(userName) && admin.getPassword().equals(password)) {
                        isAdmin = true;
                        break;
                    }
                }
            }
        }
        //find who is verified for access
        if (isPatient == true) {
            isValid = "patient";
        } else if (isDoctor == true) {
            isValid = "doctor";
        } else if (isAdmin == true) {
            isValid = "admin";
        } else {
            isValid = "invalid";
        }
        return isValid;
    }
}
