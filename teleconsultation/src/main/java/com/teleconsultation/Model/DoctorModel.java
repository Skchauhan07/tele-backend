package com.teleconsultation.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorModel {
    private String doctorName;
    private String contact;
    private String password;
    private String emailId;
    private boolean statusQueue;
}
