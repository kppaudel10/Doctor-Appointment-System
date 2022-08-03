package com.oda.service.patient;

import com.oda.dto.patient.PatientAppointmentRequestDto;
import com.oda.model.patient.ApplyAppointment;
import com.oda.service.GenericCrudService;

import java.io.IOException;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
public interface ApplyAppointmentService {

    List<PatientAppointmentRequestDto> getPatientAppointmentDetails(Integer hospitalId) throws IOException;


}
