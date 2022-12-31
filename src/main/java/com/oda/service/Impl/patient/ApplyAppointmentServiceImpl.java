package com.oda.service.Impl.patient;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.component.FileStorageComponent;
import com.oda.dto.doctor.ApplyDto;
import com.oda.dto.patient.AppointmentDto;
import com.oda.dto.patient.PatientAppointmentRequestDto;
import com.oda.enums.ApplyStatus;
import com.oda.model.patient.ApplyAppointment;
import com.oda.model.patient.Patient;
import com.oda.repo.patient.ApplyAppointmentRepo;
import com.oda.service.Impl.HospitalServiceImpl;
import com.oda.service.patient.ApplyAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Service
@RequiredArgsConstructor
public class ApplyAppointmentServiceImpl implements ApplyAppointmentService {
    private final ApplyAppointmentRepo applyAppointmentRepo;

    private final HospitalServiceImpl hospitalService;

    private final FileStorageComponent fileStorageComponent;

    private final PatientServiceImpl patientService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String dateStr =  simpleDateFormat.format(date);

    public ApplyAppointment save(ApplyDto apply, AppointmentDto appointmentDto) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
       String dateStr =  simpleDateFormat.format(date);


//       Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

        //check this patient is already apply in this hospital
        if(applyAppointmentRepo.findApplyAppointmentByDateAndPatient(dateStr,
                AuthorizedUser.getPatient().getId(),apply.getDoctor().getId()) == null){
        ApplyAppointment applyAppointment = ApplyAppointment.builder()
                .patient(AuthorizedUser.getPatient())
                .doctor(apply.getDoctor())
                .applyStatus(ApplyStatus.PENDING)
                .applyDate(dateStr)
                .hospital(apply.getHospital())
                .appointmentDate(appointmentDto.getAppointmentDate())
                .reasonOrProblem(appointmentDto.getDescription()).build();
        //save into database
        ApplyAppointment applyAppointment1 = applyAppointmentRepo.save(applyAppointment);
        return applyAppointment1;
    }else{
    return null;
         }
    }

    public List<ApplyAppointment> findAppointmentOfPatient(Integer patientId){
        return applyAppointmentRepo.findApplyAppointmentByPatientId(patientId,dateStr);
    }

    public List<ApplyAppointment> findAppointmentForHospitalOfPending(Integer hospitalId){
        return applyAppointmentRepo.findApplyAppointmentByHospitalOfPending(hospitalId);
    }

    public List<ApplyAppointment> findAppointmentForHospitalOfBooked(Integer hospitalId){
        return applyAppointmentRepo.findApplyAppointmentByHospitalOfBooked(hospitalId);
    }

    public Integer countApplyAppointmentBookedOfDoctor(Integer doctorId){
        Integer count = applyAppointmentRepo.countApplyAppointmentBookedOfDoctor(doctorId);
        if (count == null){
            return 0;
        }else {
            return count;
        }
    }

    public ApplyAppointment findById(Integer id){
        return applyAppointmentRepo.findById(id).get();
    }

    public ApplyAppointment updateByAdmin(ApplyAppointment applyAppointment){
        //update status
        applyAppointment.setApplyStatus(ApplyStatus.PROCESSING);
        applyAppointmentRepo.save(applyAppointment);
        return applyAppointment;
    }

    public ApplyAppointment updateByDoctor(ApplyAppointment applyAppointment){
        //update status
//        applyAppointment.setVisitTime(ApplyAppointment.getTimeWithAmPm(applyAppointment.getVisitTime()));
        applyAppointmentRepo.save(applyAppointment);
        return applyAppointment;
    }

    /*
    this is method return list of appointment that is for him/her
    only booked appointment will display
     */
    public List<ApplyAppointment> findAppointmentThatIsBooked(){
        return applyAppointmentRepo.findApplyAppointmentBookedOfDoctor(AuthorizedUser.getDoctor().getId());
    }

    public List<ApplyAppointment> findAppointMentByPatientId(String deatils){
        //find patient details
        Patient patient= patientService.findPatientByContactDetails(deatils);
        if(patient !=null){
            return applyAppointmentRepo.findApplyAppointmentByPatientId(patient.getId(),dateStr);
        }else {
            return null;
        }
    }

    public void deleteBYId(Integer integer){
      ApplyAppointment applyAppointment = applyAppointmentRepo.findById(integer).get();
      applyAppointment.setApplyStatus(ApplyStatus.REJECTED);
      applyAppointmentRepo.save(applyAppointment);
    }

    public Integer getTotalPendingAppointmentOfPatient(){
        List<ApplyAppointment>applyAppointments =
                applyAppointmentRepo.countPendingAppointment(AuthorizedUser.getDoctor().getId());
        if(applyAppointments !=null){
            return applyAppointments.size();
        }else {
            return null;
        }
    }

    public Integer getTotalBookedAppointmentOfPatient(){
        List<ApplyAppointment>applyAppointments =
                applyAppointmentRepo.countBookedAppointment(AuthorizedUser.getDoctor().getId());
        if(applyAppointments !=null){
            return applyAppointments.size();
        }else {
            return null;
        }
    }

    public List<ApplyAppointment> getListOfToDayPatient(){

        return applyAppointmentRepo.getApplyAppointmentForToday(AuthorizedUser.getDoctor().getId(),dateStr);
    }

    public Integer getTodayAppointmentSize(Integer hospitalId){
        List<ApplyAppointment> applyAppointments = applyAppointmentRepo.getTodayAppointment(dateStr,hospitalId);
        if(applyAppointments !=null){
            return applyAppointments.size();
        }else {
            return 0;
        }
    }

    public Boolean isExitAppointment(Integer doctorId,Integer patientId){
        List<ApplyAppointment> applyAppointment = applyAppointmentRepo.
                findApplyAppointmentByDoctorIdAndPatientId(doctorId,patientId);
        if (applyAppointment.size() >=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<PatientAppointmentRequestDto> getPatientAppointmentDetails(Integer hospitalId) throws IOException {
        List<Map<String,Object>>  appointmentDetails =applyAppointmentRepo.getPatientAppointmentDetails(hospitalId);
        List<PatientAppointmentRequestDto> patientAppointmentRequestDtoList = new ArrayList<>();
        PatientAppointmentRequestDto patientAppointMentRequestDto ;
        for (Map<String,Object> map : appointmentDetails){
            patientAppointMentRequestDto = PatientAppointmentRequestDto.builder()
                    .id((Integer) map.get("id"))
                    .address((String) map.get("address"))
                    .doctorName((String) map.get("doctor_name"))
                    .mobileNumber((String) map.get("mobile_number"))
                    .patientName((String) map.get("patient_name"))
                    .ppPath(fileStorageComponent.base64Encoded((String) map.get("profile_photo_path")))
                    .date((String) map.get("appointment_date"))
                    .fromTime((String) map.get("from_time")).build();
            //add to list
            patientAppointmentRequestDtoList.add(patientAppointMentRequestDto);
        }
        return patientAppointmentRequestDtoList;
    }
}
