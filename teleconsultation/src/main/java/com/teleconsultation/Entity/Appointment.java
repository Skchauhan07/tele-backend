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
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appointmentId;
    private String date;
    private String symptoms;
    private String description;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
