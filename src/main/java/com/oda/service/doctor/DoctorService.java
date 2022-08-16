package com.oda.service.doctor;

import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.response.ResponseDto;
import com.oda.model.doctor.DoctorAvailable;
import com.oda.service.GenericCrudService;

import java.text.ParseException;

public interface DoctorService extends GenericCrudService<DoctorDto,Integer> {

    ResponseDto saveDoctorAvailableDetails(Boolean isAvailable,Integer hospitalId) throws ParseException;

}
