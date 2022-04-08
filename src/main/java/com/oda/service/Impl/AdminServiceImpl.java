package com.oda.service.Impl;

import com.oda.dto.admin.AdminDto;
import com.oda.enums.UserStatus;
import com.oda.model.User;
import com.oda.model.admin.Admin;
import com.oda.repo.admin.AdminRepo;
import com.oda.service.admin.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepo adminRepo;
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepo adminRepo, UserServiceImpl userService, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AdminDto save(AdminDto adminDto) {
        Admin admin = Admin.builder()
                .id(adminDto.getId())
                .name(adminDto.getName())
                .address(adminDto.getAddress())
                .hospital(adminDto.getHospital())
                .mobileNumber(adminDto.getMobileNumber())
                .email(adminDto.getEmail()).build();

        //save into database
       Admin admin1 = adminRepo.save(admin);

       //also save into userTable
        User user = new User();
        user.setEmail(admin.getEmail());
        user.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        user.setUserStatus(UserStatus.ADMIN);
        userService.save(user);

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

    public Admin findAdminByEmail(String email){
        return adminRepo.findPatientByEmail(email);
    }
}
