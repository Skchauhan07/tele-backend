package com.teleconsultation.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long healthId;
    private String medicalRecords;
    private String attachment;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
