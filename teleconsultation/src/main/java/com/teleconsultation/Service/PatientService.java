package com.teleconsultation.Service;

import com.teleconsultation.Entity.Patient;

import java.util.List;


public interface PatientService {
    public boolean patientLogin(Patient patient);
    public void updateStatusQueue(String status, Long patientId);
    void joinQueue(Patient patient, Integer roomId);

    Patient getPatientById(Long patientId);
    Patient addPatient(Patient patient);
    List<Patient> getPatientListForPhoneNumber(String phoneNumber);
}
