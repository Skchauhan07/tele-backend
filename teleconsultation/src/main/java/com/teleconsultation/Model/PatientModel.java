package com.teleconsultation.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientModel {
    private Long patientId;
    private String phoneNumber;
    private String patientName;
    private int age;
    private String gender;
    private String medicalHistory;
    private boolean statusQueue;
}
