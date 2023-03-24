package com.teleconsultation.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doctorId;
    @Column(name = "name")
    @NotBlank(message = "Specify Doctor Name")
    private String doctorName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String emailId;
    @Column(name = "status")
    private boolean statusQueue;
    @Column(name= "role")
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private List<Prescription> prescriptions;

}
