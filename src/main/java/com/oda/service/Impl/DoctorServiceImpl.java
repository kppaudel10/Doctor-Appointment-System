package com.oda.service.Impl;

import com.oda.dto.doctor.DoctorDto;
import com.oda.repo.doctor.DoctorRepo;
import com.oda.service.doctor.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;

    public DoctorServiceImpl(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public DoctorDto save(Integer integer) {
        return null;
    }

    @Override
    public DoctorDto findById(Integer integer) {
        return null;
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
}
