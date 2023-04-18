package com.teleconsultation.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appointmentId;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String symptoms;
    private String description;
    private String specialization;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
