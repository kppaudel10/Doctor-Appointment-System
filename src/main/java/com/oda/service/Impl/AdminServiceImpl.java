package com.oda.service.Impl;

import com.oda.dto.admin.AdminDto;
import com.oda.repo.admin.AdminRepo;
import com.oda.service.admin.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepo adminRepo;

    public AdminServiceImpl(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @Override
    public AdminDto save(Integer integer) {
        return null;
    }

    @Override
    public AdminDto findById(Integer integer) {
        return null;
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
}
