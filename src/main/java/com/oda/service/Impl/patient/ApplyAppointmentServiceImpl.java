package com.oda.service.Impl.patient;

import com.oda.model.patient.ApplyAppointment;
import com.oda.repo.patient.ApplyAppointmentRepo;
import com.oda.service.patient.ApplyAppointmentService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Service
public class ApplyAppointmentServiceImpl implements ApplyAppointmentService {
    private final ApplyAppointmentRepo applyAppointmentRepo;

    public ApplyAppointmentServiceImpl(ApplyAppointmentRepo applyAppointmentRepo) {
        this.applyAppointmentRepo = applyAppointmentRepo;
    }

    @Override
    public ApplyAppointment save(ApplyAppointment applyAppointment) throws ParseException {
        return null ;
    }

    @Override
    public ApplyAppointment findById(Integer integer) {
        return null;
    }

    @Override
    public List<ApplyAppointment> findALl() {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
