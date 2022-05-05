package com.oda.dto.doctor;

import com.oda.enums.ApplyStatus;
import com.oda.model.doctor.Doctor;
import com.oda.model.hospital.Hospital;
import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.transform.sax.SAXResult;
import java.sql.Time;
import java.time.LocalTime;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/30/22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyDto {
    private int id;
    private Doctor doctor;

    private Hospital hospital;

    private String formTime;

    private String applyDate;

    private String toTime;

    private ApplyStatus applyStatus;

    public static String getTimeWithAmPm(String time){
        String hours = time.substring(0,2);
        Integer hoursInInt = Integer.valueOf(hours);
        if(hoursInInt<12){
            return time + " AM";
        }else if(hoursInInt == 12){
            return  time + " PM";
        }else {
            Integer hoursIN = hoursInInt - 12;
            //make str
            String modifiedHours = String.valueOf(hoursIN) + time.substring(2) + " PM";
            return modifiedHours;
        }
    }
}
