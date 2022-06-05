package com.oda.service.Impl.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.FileStorageComponent;
import com.oda.dto.doctor.DoctorDto;
import com.oda.model.doctor.Doctor;
import com.oda.repo.doctor.DoctorRepo;
import com.oda.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final FileStorageComponent fileStorageComponent;

    private static final DecimalFormat df = new DecimalFormat("0.00");



    @Override
    public DoctorDto save(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
         if(doctorDto.getId() == null){
             doctor = Doctor.builder()
                     .id(doctorDto.getId())
                     .name(doctorDto.getName())
                     .address(doctorDto.getAddress().toLowerCase())
                     .email(doctorDto.getEmail())
                     .genderStatus(doctorDto.getGenderStatus())
                     .profilePhotoPath(doctorDto.getProfilePhotoPath())
                     .mobileNumber(doctorDto.getMobileNumber())
                     .specialization(doctorDto.getSpecialization().toLowerCase())
                     .experience(doctorDto.getExperience())
//                     .rating(3D)
                     .numberOfFeedback(1)
                     .basicCharge(doctorDto.getBasicCharge())
                     .feedbackList(doctorDto.getFeedbackList()).
                     password(doctorDto.getPassword()).build();

             if (doctorDto.getExperience() >= 10D){
                 doctor.setRating(3.75D);
             }else if (doctorDto.getExperience() >= 5D) {
                 doctor.setRating(3.40D);
             }else {
                 doctor.setRating(3.2D);
             }
         }

        //for update
        if(doctorDto.getId() !=null){
          Doctor  doctor1 = doctorRepo.findById(doctorDto.getId()).get();
          doctor = doctor1;
            doctor.setProfilePhotoPath(doctor1.getProfilePhotoPath());
            doctor.setRating(Double.valueOf(df.format(doctorDto.getRating())));
            doctor.setNumberOfFeedback(doctorDto.getNumberOfFeedback());
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
                .basicCharge(doctor.getBasicCharge())
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
                           .specialization(doctor.getSpecialization())
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
                       .basicCharge(doctor.getBasicCharge())
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
               findDoctorByNameAndAddressAndSpecialization(userInput.toLowerCase()).stream().map(doctor -> {
                   try {
                       return DoctorDto.builder()
                               .id(doctor.getId())
                               .name(doctor.getName())
                               .address(doctor.getAddress())
                               .email(doctor.getEmail())
                               .experience(doctor.getExperience())
                               .rating(doctor.getRating())
                               .basicCharge(doctor.getBasicCharge())
                               .specialization(doctor.getSpecialization())
                               .genderStatus(doctor.getGenderStatus())
                               .profilePhotoPath(fileStorageComponent.base64Encoded(doctor.getProfilePhotoPath())).build();
                   } catch (IOException e) {
                       e.printStackTrace();
                       return null;
                   }
               }).collect(Collectors.toList());
    }


    public Doctor findIdByContactOrEmail(String details){
        return doctorRepo.findIdByContactOrEmail(details);
    }

    public List<DoctorDto> oneTimeVisitedDoctor(){
        return doctorRepo.findOneTimeVisitedDoctor(AuthorizedUser.getPatient().getId()).stream().map(doctor -> {
            try {
                return DoctorDto.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .address(doctor.getAddress())
                        .email(doctor.getEmail())
                        .experience(doctor.getExperience())
                        .rating(doctor.getRating())
                        .basicCharge(doctor.getBasicCharge())
                        .specialization(doctor.getSpecialization())
                        .genderStatus(doctor.getGenderStatus())
                        .profilePhotoPath(fileStorageComponent.base64Encoded(doctor.getProfilePhotoPath())).build();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    public List<DoctorDto> sortDoctorByLowCharge(){
        return doctorRepo.shortDoctorByLowCharge().stream().map(doctor -> {
            try {
                return DoctorDto.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .address(doctor.getAddress())
                        .email(doctor.getEmail())
                        .experience(doctor.getExperience())
                        .rating(doctor.getRating())
                        .basicCharge(doctor.getBasicCharge())
                        .specialization(doctor.getSpecialization())
                        .genderStatus(doctor.getGenderStatus())
                        .profilePhotoPath(fileStorageComponent.base64Encoded(doctor.getProfilePhotoPath())).build();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    public List<DoctorDto> sortDoctorByHighCharge(){
        return doctorRepo.shortDoctorByHighCharge().stream().map(doctor -> {
            try {
                return DoctorDto.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .address(doctor.getAddress())
                        .email(doctor.getEmail())
                        .experience(doctor.getExperience())
                        .rating(doctor.getRating())
                        .basicCharge(doctor.getBasicCharge())
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