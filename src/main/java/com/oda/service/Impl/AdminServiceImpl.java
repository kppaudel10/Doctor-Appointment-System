package com.oda.service.Impl;

import com.oda.dto.admin.AdminDto;
import com.oda.dto.admin.ReportUploadDto;
import com.oda.model.admin.Admin;
import com.oda.model.admin.Report;
import com.oda.model.patient.ApplyAppointment;
import com.oda.repo.admin.AdminRepo;
import com.oda.repo.admin.ReportUploadRepo;
import com.oda.repo.patient.ApplyAppointmentRepo;
import com.oda.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepo adminRepo;

    private final ReportUploadRepo reportUploadRepo;

    private final ApplyAppointmentRepo applyAppointmentRepo;


    @Override
    public AdminDto save(AdminDto adminDto) {
        Admin admin = Admin.builder()
                .id(adminDto.getId())
                .name(adminDto.getName())
                .address(adminDto.getAddress())
                .hospital(adminDto.getHospital())
                .mobileNumber(adminDto.getMobileNumber())
                .email(adminDto.getEmail())
                .password(adminDto.getPassword()).build();

        //save into database
        Admin admin1 = adminRepo.save(admin);

        //also save into userTable
//        User user = new User();
//        user.setEmail(admin.getEmail());
//        user.setPassword(passwordEncoder.encode(adminDto.getPassword()));
//        user.setUserStatus(UserStatus.ADMIN);
//        userService.save(user);

        return AdminDto.builder().id(admin1.getId()).build();
    }

    @Override
    public AdminDto findById(Integer integer) {
        Admin admin = adminRepo.findById(integer).get();
        return AdminDto.builder()
                .id(admin.getId())
                .name(admin.getName())
                .address(admin.getAddress())
                .mobileNumber(admin.getMobileNumber())
                .email(admin.getEmail()).build();
    }

    @Override
    public List<AdminDto> findALl() {
        return adminRepo.findAll().stream().map(admin -> {
            return AdminDto.builder()
                    .id(admin.getId())
                    .name(admin.getName())
                    .address(admin.getAddress())
                    .mobileNumber(admin.getMobileNumber())
                    .email(admin.getEmail()).build();
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer integer) {

    }

    public Admin findAdminByEmail(String email) {
        return adminRepo.findPatientByEmail(email);
    }

    public Admin findByUserName(String userName) {
        Admin admin = adminRepo.findByUserName(userName);
        if (admin == null){
            return null;
        }else {
            return admin;
        }
    }

    public Integer checkEmailDuplicate(String email){
        return adminRepo.getEmailCount(email);
    }

    @Override
    public ReportUploadDto uploadedReport(ReportUploadDto reportUploadDto) {
       Optional<ApplyAppointment> applyAppointment= applyAppointmentRepo.findById(reportUploadDto.getAppointmentId());
      ApplyAppointment appointment = applyAppointment.get();
        Report report = Report.builder()
                .id(reportUploadDto.getId())
                .reportUrl(reportUploadDto.getReportUrl())
                .patient(appointment.getPatient())
                .build();
      Report savedReport=  reportUploadRepo.save(report);
      reportUploadDto.setId(savedReport.getId());
        return reportUploadDto;
    }

    public Integer checkMobileNumberDuplicate(String mobileNumber){
        return adminRepo.getMobileNumberDuplicate(mobileNumber);
    }

}
