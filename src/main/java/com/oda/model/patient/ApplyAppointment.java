package com.oda.model.patient;

import com.oda.enums.ApplyStatus;
import com.oda.model.doctor.Doctor;
import com.oda.model.hospital.Hospital;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "oda_appointment")
public class ApplyAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String applyDate;

    private String fromTime;

    private String toTime;

    private ApplyStatus applyStatus;

    private String appointmentDate;

    private String reasonOrProblem;

    private String visitTime;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

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
