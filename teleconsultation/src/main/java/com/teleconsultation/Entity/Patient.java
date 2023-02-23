package com.teleconsultation.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long patientId;
    private String phoneNumber;
    private String patientName;
    private int age;
    private String gender;
    private String medicalHistory;
    private boolean statusQueue;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "patient")
    private List<Prescription> prescription;

    @OneToOne(cascade = CascadeType.REMOVE)
    private HealthRecord healthRecord;

}
