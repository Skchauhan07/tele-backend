package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Patient;

import java.util.List;


public interface PatientService {
    public boolean patientLogin(Patient patient);

    void joinQueue(Patient patient);

    Patient getPatientById(Long patientId);

    Patient addPatient(Patient patient);
    List<Patient> getPatientListForPhoneNumber(String phoneNumber);
}
