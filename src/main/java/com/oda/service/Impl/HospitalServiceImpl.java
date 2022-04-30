package com.oda.service.Impl;

import com.oda.model.hospital.Hospital;
import com.oda.repo.hospital.HospitalRepo;
import com.oda.service.hospital.HospitalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/4/22
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepo hospitalRepo;

    public HospitalServiceImpl(HospitalRepo hospitalRepo) {
        this.hospitalRepo = hospitalRepo;
    }

    @Override
    public Hospital save(Hospital hospital) {
        Hospital hospital1 = hospitalRepo.save(hospital);
        return hospital1 ;
    }

    @Override
    public Hospital findById(Integer integer) {
        return hospitalRepo.findById(integer).get();
    }

    @Override
    public List<Hospital> findALl() {
        return hospitalRepo.findAll();
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
