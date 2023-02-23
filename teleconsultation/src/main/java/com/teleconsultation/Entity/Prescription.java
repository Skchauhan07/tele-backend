package com.teleconsultation.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long prescriptionId;
    private String medicalFinding;
    private String medicineName;
    private String dosage;
    private String duration;
    @ManyToOne(

    )
    @JoinColumn(
            name = "patient_id"
    )
    private Patient patient;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "doctor_id"
    )
    private Doctor doctor;


}
