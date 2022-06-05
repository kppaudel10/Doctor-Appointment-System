package com.oda.service.Impl.patient;

import com.oda.component.FileStorageComponent;
import com.oda.dto.patient.PatientDto;
import com.oda.model.patient.Patient;
import com.oda.repo.patient.PatientRepo;
import com.oda.service.patient.PatientService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final FileStorageComponent fileStorageComponent;

    public PatientServiceImpl(PatientRepo patientRepo, FileStorageComponent fileStorageComponent) {
        this.patientRepo = patientRepo;
        this.fileStorageComponent = fileStorageComponent;
    }


    @Override
    public PatientDto save(PatientDto patientDto) throws ParseException {
        Patient patient = Patient.builder()
                .id(patientDto.getId())
                .name(patientDto.getName())
                .address(patientDto.getAddress().toLowerCase())
                .mobileNumber(patientDto.getMobileNumber())
                .genderStatus(patientDto.getGenderStatus())
                .birthDate(new SimpleDateFormat("yyyy-MM-dd").parse(patientDto.getBirthDate()))
                .email(patientDto.getEmail())
                .profilePhotoPath(patientDto.getProfilePhotoPath())
                .password(patientDto.getPassword()).build();
        //save into database
        Patient patient1 = patientRepo.save(patient);

//       //save into database
//        User user = new User();
//        user.setEmail(patient.getEmail());
//        user.setPassword(passwordEncoder.encode(patientDto.getPassword()));
//        user.setUserStatus(UserStatus.PATIENT);
//
//        userService.save(user);

        return PatientDto.builder().id(patient1.getId()).build();
    }

    @Override
    public PatientDto findById(Integer integer) throws IOException {
        Patient patient = patientRepo.findById(integer).get();
        return PatientDto.builder()
                .id(patient.getId())
                .address(patient.getAddress())
                .email(patient.getEmail())
                .mobileNumber(patient.getMobileNumber())
                .genderStatus(patient.getGenderStatus())
                .name(patient.getName())
                .password(patient.getPassword())
                .birthDate(String.valueOf(patient.getBirthDate()))
                .profilePhotoPath(fileStorageComponent.base64Encoded(patient.getProfilePhotoPath())).build();

    }

    @Override
    public List<PatientDto> findALl() {
        return patientRepo.findAll().stream().map(patient -> {
            return PatientDto.builder()
                    .id(patient.getId())
                    .name(patient.getName())
                    .address(patient.getAddress())
                    .mobileNumber(patient.getMobileNumber())
                    .email(patient.getEmail()).build();
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer integer) {
    }

    public Patient findPatientByEmail(String email) {
        return patientRepo.findPatientByEmail(email);
    }

    public PatientDto findByUserName(String username) throws IOException {

        Patient patient = patientRepo.findByUserName(username);
        PatientDto patientDto = null;
       if(patient !=null){
           try {
               patientDto = PatientDto.builder()
                       .id(patient.getId())
                       .address(patient.getAddress())
                       .email(patient.getEmail())
                       .mobileNumber(patient.getMobileNumber())
                       .genderStatus(patient.getGenderStatus())
                       .name(patient.getName())
                       .password(patient.getPassword())
                       .birthDate(String.valueOf(patient.getBirthDate()))
                       .profilePhotoPath(fileStorageComponent.base64Encoded(patient.getProfilePhotoPath())).build();

           }catch (Exception e){
               e.printStackTrace();
           }
       }
        return patientDto;
    }

   public List<Patient> findPatientByMobileOrEmail(String userInput){
        return patientRepo.findPatientByEmailAndMobileNumber(userInput);
    }

    public Patient findPatientByContactDetails(String contactDetails){
        return patientRepo.findPatientByContactDetails(contactDetails);
    }

    public Integer checkEmailDuplicate(String email){
        return patientRepo.getEmailCount(email);
    }

    public Patient findByEmail(String email){
        return patientRepo.findByUserName(email);
    }
}
