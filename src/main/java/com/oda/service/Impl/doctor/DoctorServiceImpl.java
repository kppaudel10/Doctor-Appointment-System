package com.oda.service.Impl.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.FileStorageComponent;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.response.ResponseDto;
import com.oda.model.doctor.Doctor;
import com.oda.model.doctor.DoctorAvailable;
import com.oda.model.hospital.Hospital;
import com.oda.repo.doctor.DoctorAvailableRepo;
import com.oda.repo.doctor.DoctorRepo;
import com.oda.repo.hospital.HospitalRepo;
import com.oda.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final FileStorageComponent fileStorageComponent;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final DoctorAvailableRepo doctorAvailableRepo;

    private final HospitalRepo hospitalRepo;


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
        String modifiedString = userInput.concat("%");

       return doctorRepo.
               findDoctorByNameAndAddressAndSpecialization(modifiedString.toLowerCase()).stream().map(doctor -> {
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

    public Integer checkEmailDuplicate(String email){
        return doctorRepo.getEmailCount(email);
    }

    public Integer checkMobileNumberDuplicate(String mobileNumber){
        return doctorRepo.getMobileNumberDuplicate(mobileNumber);
    }

    public Doctor findByEmail(String userName){
      return  doctorRepo.findByUserName(userName);
    }

    @Override
    public ResponseDto saveDoctorAvailableDetails(Boolean isAvailable,Integer hospitalId) throws ParseException {
        DoctorAvailable doctorAvailable = new DoctorAvailable();
        ResponseDto responseDto = new ResponseDto();
//        Date dt = new Date();
//        LocalDateTime date = LocalDateTime.from(dt.toInstant()).plusDays(1);
//        String dateStr =   new SimpleDateFormat("yyyy-MM-dd").format(date);
        LocalDate today = LocalDate.now();
        String tomorrow = (today.plusDays(1)).format(DateTimeFormatter.ISO_DATE);
        doctorAvailable.setDoctor(AuthorizedUser.getDoctor());
        doctorAvailable.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(tomorrow));
        if (doctorAvailableRepo.getCountDoctorAlreadyCheckOrNot(AuthorizedUser.getDoctor().getId(), tomorrow, hospitalId) == 0) {
            //find hospital by id
            Optional<Hospital> optionalHospital = hospitalRepo.findById(hospitalId);
            if (optionalHospital.isPresent()) {
                Hospital hospital = optionalHospital.get();
                doctorAvailable.setHospitalId(hospital);
                doctorAvailable.setIsAvailable(isAvailable);
                DoctorAvailable doctorAvailable1 = doctorAvailableRepo.save(doctorAvailable);
                if (doctorAvailable1 != null) {
                    responseDto.setStatus(true);
                    responseDto.setMessage("Checked in successfully");
                } else {
                    responseDto.setStatus(false);
                    responseDto.setMessage("Unable to checked");
                }
            } else {
                responseDto.setStatus(false);
                responseDto.setMessage("Unknown hospital");
            }
        } else {
            responseDto.setStatus(false);
            responseDto.setMessage("You already checked in");
        }

        return responseDto;
    }
}