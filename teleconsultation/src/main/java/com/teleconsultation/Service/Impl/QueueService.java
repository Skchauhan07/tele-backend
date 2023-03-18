package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Queue;


public interface QueueService {
    public void addPatientToQueue(Patient patient, Integer roomId);
    public void leavePatientQueue(Patient patient);
    public Pair<Patient, Integer> getNextInPairQueue();

}
