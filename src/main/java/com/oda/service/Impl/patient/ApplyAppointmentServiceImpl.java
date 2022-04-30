package com.oda.service.Impl.patient;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.enums.ApplyStatus;
import com.oda.model.patient.ApplyAppointment;
import com.oda.repo.patient.ApplyAppointmentRepo;
import com.oda.service.Impl.HospitalServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Service
public class ApplyAppointmentServiceImpl {
    private final ApplyAppointmentRepo applyAppointmentRepo;
    private final HospitalServiceImpl hospitalService;

    public ApplyAppointmentServiceImpl(ApplyAppointmentRepo applyAppointmentRepo, HospitalServiceImpl hospitalService) {
        this.applyAppointmentRepo = applyAppointmentRepo;
        this.hospitalService = hospitalService;
    }

    public ApplyAppointment save(ApplyDto apply)
    {
        ApplyAppointment applyAppointment = ApplyAppointment.builder()
                .applyStatus(ApplyStatus.PENDING)
                .patient(AuthorizedUser.getPatient())
                .doctor(apply.getDoctor())
                .fromTime(apply.getFormTime())
                .applyDate(new Date())
                .toTime(apply.getToTime())
                .hospital(apply.getHospital()).build();
        //save into database
       ApplyAppointment applyAppointment1 = applyAppointmentRepo.save(applyAppointment);
        return applyAppointment1;
    }

    public List<ApplyAppointment> findAppointmentOfPatient(Integer patientId){
        return applyAppointmentRepo.findApplyAppointmentByPatientId(patientId);
    }
}
