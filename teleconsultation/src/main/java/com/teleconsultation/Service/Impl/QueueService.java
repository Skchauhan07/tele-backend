package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import org.springframework.stereotype.Service;

import java.util.Queue;


public interface QueueService {
    public void addPatientToQueue(Patient patient);
    public void leavePatientQueue(Patient patient);
    public Patient getNextPatient();

}
