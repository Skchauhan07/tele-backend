package com.teleconsultation.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorModel {
    private Long doctorId;
    private String doctorName;
    private String contact;
    private String password;
    private String emailId;
    private boolean statusQueue;
}
