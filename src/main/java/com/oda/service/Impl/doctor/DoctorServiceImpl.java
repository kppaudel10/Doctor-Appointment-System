package com.oda.service.Impl.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.FileStorageComponent;
import com.oda.dto.doctor.DoctorDto;
import com.oda.model.doctor.Doctor;
import com.oda.repo.doctor.DoctorRepo;
import com.oda.service.doctor.DoctorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final FileStorageComponent fileStorageComponent;

    public DoctorServiceImpl(DoctorRepo doctorRepo, FileStorageComponent fileStorageComponent) {
        this.doctorRepo = doctorRepo;
        this.fileStorageComponent = fileStorageComponent;
    }


    @Override
    public DoctorDto save(DoctorDto doctorDto) {
        Doctor doctor = Doctor.builder()
                .id(doctorDto.getId())
                .name(doctorDto.getName())
                .address(doctorDto.getAddress().toLowerCase())
                .email(doctorDto.getEmail())
                .genderStatus(doctorDto.getGenderStatus())
                .profilePhotoPath(doctorDto.getProfilePhotoPath())
                .mobileNumber(doctorDto.getMobileNumber())
                .specialization(doctorDto.getSpecialization().toLowerCase())
                .experience(doctorDto.getExperience())
                .rating(doctorDto.getRating())
                .numberOfFeedback(doctorDto.getNumberOfFeedback())
                .feedbackList(doctorDto.getFeedbackList()).
                password(doctorDto.getPassword()).build();

        //for update
        if(doctor.getId() !=null){
            doctor.setProfilePhotoPath(doctorRepo.findById(doctor.getId()).get().getProfilePhotoPath());
        }else {
            //for new save(first time)
            doctor.setRating(3D);
            doctor.setNumberOfFeedback(1);
        }
        //save into database
        Doctor doctor1 = doctorRepo.save(doctor);


        return DoctorDto.builder().id(doctor1.getId()).build();
    }

    @Override
    public DoctorDto findById(Integer integer) throws IOException {
        Doctor doctor = doctorRepo.findById(integer).get();
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .address(doctor.getAddress())
                .mobileNumber(doctor.getMobileNumber())
                .specialization(doctor.getSpecialization())
                .experience(doctor.getExperience())
                .rating(doctor.getRating())
                .password(doctor.getPassword())
                .profilePhotoPath(fileStorageComponent.base64Encoded(doctor.getProfilePhotoPath()))
                .numberOfFeedback(doctor.getNumberOfFeedback())
                .genderStatus(doctor.getGenderStatus())
                .feedbackList(doctor.getFeedbackList())
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

    public List<DoctorDto> findDoctorByAddress(String address) {
        List<Doctor> doctorList = doctorRepo.findAll();
        List<DoctorDto> sortedDoctorList = new ArrayList<>();

        for (Integer i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getAddress().equals(address)) {
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

    public List<DoctorDto> findDoctorsBySpecialization(String specialization) {
        List<Doctor> doctorList = doctorRepo.findAll();
        List<DoctorDto> sortedDoctorList = new ArrayList<>();

        for (Integer i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getSpecialization().equals(specialization)) {
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

    public Doctor findDoctorByEmail(String email) {
        return doctorRepo.findDoctorByEmail(email);
    }

    public DoctorDto findByUserName(String userName) throws IOException {
        Doctor doctor = doctorRepo.findByUserName(userName);
        DoctorDto doctorDto = null;
               if(doctor !=null){
                   doctorDto = DoctorDto.builder()
                           .id(doctor.getId())
                           .name(doctor.getName())
                           .email(doctor.getEmail())
                           .address(doctor.getAddress())
                           .experience(doctor.getExperience())
                           .genderStatus(doctor.getGenderStatus())
                           .mobileNumber(doctor.getMobileNumber())
                           .numberOfFeedback(doctor.getNumberOfFeedback())
                           .profilePhotoPath(fileStorageComponent.base64Encoded(doctor.getProfilePhotoPath()))
                           .password(doctor.getPassword()).build();
               }
               return doctorDto;
    }

    public List<DoctorDto> finDoctorByAddressByDefault(){
        String address = AuthorizedUser.getPatient().getAddress().toLowerCase();

       return doctorRepo.findDoctorByAddress(address).stream().map(doctor -> {
           try {
               return DoctorDto.builder()
                       .id(doctor.getId())
                       .name(doctor.getName())
                       .address(doctor.getAddress())
                       .email(doctor.getEmail())
                       .experience(doctor.getExperience())
                       .rating(doctor.getRating())
                       .specialization(doctor.getSpecialization())
                       .genderStatus(doctor.getGenderStatus())
                       .profilePhotoPath(fileStorageComponent.base64Encoded(doctor.getProfilePhotoPath())).build();
           } catch (IOException e) {
               e.printStackTrace();
               return null;
           }
       }).collect(Collectors.toList());
    }
    public List<DoctorDto> findDoctorByANS(String userInput){
       return doctorRepo.
               findDoctorByNameAndAddressAndSpecialization(userInput).stream().map(doctor -> {
                   try {
                       return DoctorDto.builder()
                               .id(doctor.getId())
                               .name(doctor.getName())
                               .address(doctor.getAddress())
                               .email(doctor.getEmail())
                               .experience(doctor.getExperience())
                               .rating(doctor.getRating())
                               .specialization(doctor.getSpecialization())
                               .genderStatus(doctor.getGenderStatus())
                               .profilePhotoPath(fileStorageComponent.base64Encoded(doctor.getProfilePhotoPath())).build();
                   } catch (IOException e) {
                       e.printStackTrace();
                       return null;
                   }
               }).collect(Collectors.toList());
    }
}