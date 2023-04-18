package com.teleconsultation.Service;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Queue;


public interface QueueService
{
    public void addPatient(Patient patient, Integer roomId, String specialization);
    public void leavePatientQueue(Patient patient);
    public Pair<Patient, Integer> getNext(String specialization);
}
