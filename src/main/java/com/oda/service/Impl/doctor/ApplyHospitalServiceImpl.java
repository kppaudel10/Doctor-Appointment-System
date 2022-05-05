package com.oda.service.Impl.doctor;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.ApplyDto;
import com.oda.enums.ApplyStatus;
import com.oda.model.doctor.ApplyHospital;
import com.oda.model.doctor.Doctor;
import com.oda.repo.doctor.ApplyHospitalRepo;
import com.oda.service.Impl.HospitalServiceImpl;
import com.oda.service.doctor.ApplyHospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/30/22
 */
@Service
@RequiredArgsConstructor
public class ApplyHospitalServiceImpl implements ApplyHospitalService {
    private final ApplyHospitalRepo applyHospitalRepo;
    private final DoctorServiceImpl doctorService;

    private final HospitalServiceImpl hospitalService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String dateStr =  simpleDateFormat.format(date);

    @Override
    public ApplyDto save(ApplyDto applyDto) throws ParseException {


        //check hospital that is already apply or not
      if(applyHospitalRepo.findApplyHospitalDetailsByDoctorId(AuthorizedUser.getDoctor().getId()) == null){
          ApplyHospital applyHospital = ApplyHospital.builder()
                  .id(applyDto.getId())
                  .hospital(applyDto.getHospital())
                  .doctor(AuthorizedUser.getDoctor())
                  .fromTime(ApplyDto.getTimeWithAmPm(applyDto.getFormTime()))
                  .toTime(ApplyDto.getTimeWithAmPm(applyDto.getToTime()))
                  .applyStatus(ApplyStatus.PENDING)
                  .applyDate(dateStr).build();
                  ApplyHospital applyHospital1 = applyHospitalRepo.save(applyHospital);
                  return ApplyDto.builder().id(applyHospital1.getId()).build();
      }else {
          return null;
      }
    }

    @Override
    public ApplyDto findById(Integer integer) {
        ApplyHospital applyHospital = applyHospitalRepo.findById(integer).get();
        return ApplyDto.builder()
                .id(applyHospital.getId())
                .hospital(applyHospital.getHospital())
                .doctor(applyHospital.getDoctor())
                .formTime(applyHospital.getFromTime())
                .applyDate(applyHospital.getApplyDate())
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
        applyHospitalRepo.deleteById(integer);
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
                .applyDate(applyDto.getApplyDate())
                .applyStatus(ApplyStatus.BOOKED).build();

       ApplyHospital applyHospital1 = applyHospitalRepo.save(applyHospital);
        return ApplyDto.builder().id(applyHospital1.getId()).build();
    }

   public List<ApplyHospital> findDoctorApplyDetailsByContact(String contactDetails){
        //first find doctor id
       Doctor doctor = doctorService.findIdByContactOrEmail(contactDetails);
       if(doctor !=null){
           return applyHospitalRepo.findApplyHospitalByDoctorId(doctor.getId());
       }
       return null;
   }

   public ApplyHospital applyHospitalByDoctorId(Integer doctorId){
        return applyHospitalRepo.findApplyHospitalDetailsByDoctorId(doctorId);
   }

   public Integer getTodayDoctorApply(){
        List<ApplyHospital> applyHospitalList = applyHospitalRepo.getTodayDoctorApply(dateStr);

        if(applyHospitalList !=null){
            return applyHospitalList.size();
        }else {
            return 0;
        }
   }
}
