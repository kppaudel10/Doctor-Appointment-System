package com.oda.service.Impl.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.enums.ApplyStatus;
import com.oda.model.doctor.ApplyHospital;
import com.oda.repo.doctor.ApplyHospitalRepo;
import com.oda.service.Impl.HospitalServiceImpl;
import com.oda.service.doctor.ApplyHospitalService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/30/22
 */
@Service
public class ApplyHospitalServiceImpl implements ApplyHospitalService {
    private final ApplyHospitalRepo applyHospitalRepo;
    private final HospitalServiceImpl hospitalService;

    public ApplyHospitalServiceImpl(ApplyHospitalRepo applyHospitalRepo, HospitalServiceImpl hospitalService) {
        this.applyHospitalRepo = applyHospitalRepo;
        this.hospitalService = hospitalService;
    }

    @Override
    public ApplyDto save(ApplyDto applyDto) throws ParseException {
        ApplyHospital applyHospital = ApplyHospital.builder()
                .id(applyDto.getId())
                .hospital(applyDto.getHospital())
                .doctor(AuthorizedUser.getDoctor())
                .fromTime(ApplyDto.getTimeWithAmPm(applyDto.getFormTime()))
                .toTime(ApplyDto.getTimeWithAmPm(applyDto.getToTime()))
                .applyStatus(ApplyStatus.PENDING).build();

        applyHospitalRepo.save(applyHospital);
        return applyDto;
    }

    @Override
    public ApplyDto findById(Integer integer) {
        ApplyHospital applyHospital = applyHospitalRepo.findById(integer).get();
        return ApplyDto.builder()
                .id(applyHospital.getId())
                .hospital(applyHospital.getHospital())
                .doctor(applyHospital.getDoctor())
                .formTime(applyHospital.getFromTime())
                .applyStatus(applyHospital.getApplyStatus())
                .toTime(applyHospital.getToTime())
                .build();
    }

    @Override
    public List<ApplyDto> findALl() {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    public List<ApplyHospital> findApplyDetailsOfDoctor(Integer doctorId){
        return applyHospitalRepo.findApplyByDoctorId(doctorId);
    }

    public List<ApplyHospital> findApplyHospitalListByHospitalBooked(Integer hospitalId){
        return applyHospitalRepo.findApplyHospitalOfBooked(hospitalId);
    }

    public List<ApplyHospital> findApplyHospitalListOfPending(Integer hospitalId){
        return applyHospitalRepo.findApplyHospitalOfPending(hospitalId);
    }

    public ApplyDto update(ApplyDto applyDto){
        //update status
        ApplyHospital applyHospital = ApplyHospital.builder()
                .id(applyDto.getId())
                .hospital(applyDto.getHospital())
                .doctor(applyDto.getDoctor())
                .fromTime(applyDto.getFormTime())
                .toTime(applyDto.getToTime())
                .applyStatus(ApplyStatus.BOOKED).build();

       ApplyHospital applyHospital1 = applyHospitalRepo.save(applyHospital);
        return ApplyDto.builder().id(applyHospital1.getId()).build();
    }

}
