package com.oda.repo.doctor;

import com.oda.dto.doctor.DoctorDto;
import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import com.oda.projection.DoctorProjection;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.print.Doc;
import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    @Query(value = "select * from oda_doctor d where d.email = ?1",nativeQuery = true)
    Doctor findDoctorByEmail(String email);

    @Query(value = "select * from oda_doctor where name like ?1%",nativeQuery = true)
    List<Doctor> findDoctorByName(String name);

    @Query(value = "select * from oda_doctor d where d.email = ?1 or d.mobile_number = ?1",nativeQuery = true)
    Doctor findByUserName(String username);


    @Query(value = "select *\n" +
            "from oda_doctor d\n" +
            "where lower(d.doctor_name) like ?1\n" +
            "   or lower(d.address) like ?1\n" +
            "   or lower(d.specialization) like ?1",nativeQuery = true)
    List<Doctor> findDoctorByNameAndAddressAndSpecialization(String userInput);

    @Query(value = "select *\n" +
            "from oda_doctor d\n" +
            "where d.address = ?1\n" +
            "  and d.id in\n" +
            "      (select distinct doctor_id from oda_hospital_apply where apply_status = 1)\n" +
            "order by d.id",nativeQuery = true)
    List<Doctor> findDoctorByAddress(String address);

    @Query(value = "select * from  oda_doctor d where d.mobile_number =?1 or d.email = ?1",nativeQuery = true)
    public Doctor findIdByContactOrEmail(String contactDetails);

    @Query(value = "select d.id,d.doctor_name,d.email ,d.address,\n" +
            "       d.experience,d.gender_status,d.mobile_number,d.number_of_feedback,\n" +
            "       d.specialization,d.rating,d.profile_photo_path\n" +
            "from oda_doctor d left join oda_appointment oa on d.id = oa.doctor_id where patient_id = ?1",nativeQuery = true)
    List<DoctorDto>findOneTimeVisitedDoctor(Integer patientId);


    @Query(value = "select * from oda_doctor where id is not null order by basic_charge",nativeQuery = true)
    List<Doctor> shortDoctorByLowCharge();

    @Query(value = "select * from oda_doctor where id is not null order by basic_charge DESC",nativeQuery = true)
    List<Doctor> shortDoctorByHighCharge();

    @Query(value = "select count(id) from oda_doctor where email = ?1",nativeQuery = true)
    Integer getEmailCount(String email);

    @Query(value = "select count(id) from oda_doctor where mobile_number = ?1",nativeQuery = true)
    Integer getMobileNumberDuplicate(String mobileNumber);

    @Modifying
    @Transactional
    @Query(value = "update oda_update set password = ?1 where id = ?2", nativeQuery = true)
    void updatePassword(String password, Integer adminId);


    @Query(value = "select oh.id, oh.name\n" +
            "from oda_hospital_apply oa\n" +
            "         inner join oda_hospital oh on oa.hospital_id = oh.id\n" +
            "         inner join oda_doctor od on oa.doctor_id = od.id\n" +
            "where od.id = ?1\n" +
            "  and apply_status = 1",nativeQuery = true)
    List<DoctorProjection> getHospitalByDoctorId(Integer doctorId);
}
