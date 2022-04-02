package com.oda.service.Impl;

import com.oda.dto.doctor.DoctorDto;
import com.oda.enums.UserStatus;
import com.oda.model.User;
import com.oda.model.doctor.Doctor;
import com.oda.repo.doctor.DoctorRepo;
import com.oda.service.doctor.DoctorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final UserServiceImpl userService;

    public DoctorServiceImpl(DoctorRepo doctorRepo, UserServiceImpl userService) {
        this.doctorRepo = doctorRepo;

        this.userService = userService;
    }


    @Override
    public DoctorDto save(DoctorDto doctorDto) {
        Doctor doctor = Doctor.builder()
                .id(doctorDto.getId())
                .name(doctorDto.getName())
                .address(doctorDto.getAddress())
                .email(doctorDto.getEmail())
                .mobileNumber(doctorDto.getMobileNumber())
                .specialization(doctorDto.getSpecialization())
                .experience(doctorDto.getExperience()).build();
        //save into database
        Doctor doctor1 = doctorRepo.save(doctor);

        //save into user table
        User user = new User();
        user.setEmail(doctorDto.getEmail());
        user.setPassword(doctorDto.getPassword());
        user.setUserStatus(UserStatus.DOCTOR);
        userService.save(user);

        return DoctorDto.builder().id(doctor1.getId()).build();
    }

    @Override
    public DoctorDto findById(Integer integer) {
       Doctor doctor= doctorRepo.findById(integer).get();
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .address(doctor.getAddress())
                .mobileNumber(doctor.getMobileNumber())
                .email(doctor.getEmail()).build();
    }

    @Override
    public List<DoctorDto> findALl() {
        return doctorRepo.findAll().stream().map(doctor -> {
            return DoctorDto.builder()
                    .id(doctor.getId())
                    .name(doctor.getName())
                    .mobileNumber(doctor.getMobileNumber())
                    .email(doctor.getEmail()).build();
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer integer) {

    }

    public List<DoctorDto> findDoctorByAddress(String address){
        List<Doctor> doctorList = doctorRepo.findAll();
        List<DoctorDto> sortedDoctorList = new ArrayList<>();

        for (Integer i =0;i<doctorList.size();i++){
            if (doctorList.get(i).getAddress().equals(address)){
                Doctor doctor = doctorList.get(i);
                DoctorDto doctorDto = DoctorDto.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .address(doctor.getAddress())
                        .email(doctor.getEmail())
                        .mobileNumber(doctor.getMobileNumber())
                        .specialization(doctor.getSpecialization())
                        .experience(doctor.getExperience()).build();
                sortedDoctorList.add(doctorDto);
            }
        }
        return sortedDoctorList;
    }

    public List<DoctorDto> findDoctorsBySpecialization(String specialization){
        List<Doctor> doctorList = doctorRepo.findAll();
        List<DoctorDto> sortedDoctorList = new ArrayList<>();

        for (Integer i =0;i<doctorList.size();i++){
            if (doctorList.get(i).getSpecialization().equals(specialization)){
                Doctor doctor = doctorList.get(i);
                DoctorDto doctorDto = DoctorDto.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .address(doctor.getAddress())
                        .email(doctor.getEmail())
                        .mobileNumber(doctor.getMobileNumber())
                        .specialization(doctor.getSpecialization())
                        .experience(doctor.getExperience()).build();
                sortedDoctorList.add(doctorDto);
            }
        }
        return sortedDoctorList;
    }
}
