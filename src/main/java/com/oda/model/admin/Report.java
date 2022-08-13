package com.oda.model.admin;

import com.oda.model.patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "oda_report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Report_SEQ_GEN")
    @SequenceGenerator(name = "Report_SEQ_GEN", sequenceName = "Report_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "report_url")
    private String reportUrl;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


}
