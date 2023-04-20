package com.teleconsultation.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionModel {
    private Long prescriptionId;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String medicalFinding;
    private String medicineName;
    private String dosage;
    private String duration;
    private String doctorName;
}
