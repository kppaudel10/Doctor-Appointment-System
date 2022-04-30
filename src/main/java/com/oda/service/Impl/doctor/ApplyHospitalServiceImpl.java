package com.oda.service.Impl.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.model.doctor.Apply;
import com.oda.repo.doctor.ApplyRepo;
import com.oda.service.Impl.HospitalServiceImpl;
import com.oda.service.doctor.ApplyService;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/30/22
 */
@Service
public class ApplyHospitalServiceImpl implements ApplyService {
    private final ApplyRepo applyRepo;
    private final HospitalServiceImpl hospitalService;

    public ApplyHospitalServiceImpl(ApplyRepo applyRepo, HospitalServiceImpl hospitalService) {
        this.applyRepo = applyRepo;
        this.hospitalService = hospitalService;
    }

    @Override
    public ApplyDto save(ApplyDto applyDto) throws ParseException {
        Apply apply = Apply.builder()
                .id(applyDto.getId())
                .hospital(hospitalService.findById(applyDto.getHospitalId()))
                .doctor(AuthorizedUser.getDoctor())
                .fromTime(ApplyDto.getTimeWithAmPm(applyDto.getFormTime()))
                .toTime(ApplyDto.getTimeWithAmPm(applyDto.getToTime())).build();
        applyRepo.save(apply);
        return applyDto;
    }

    @Override
    public ApplyDto findById(Integer integer) {
        return null;
    }

    @Override
    public List<ApplyDto> findALl() {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
