package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;

import java.util.List;
import java.util.Optional;


public interface DoctorService {
    public boolean doctorLogin(Doctor doctor);
    public Doctor addDoctor(Doctor doctor);
    public void scheduleFollowUp(Patient patient);
    public Prescription issuePrescription(Patient patient);
    public List<Patient> viewFollowUp();
    public void cancelFollowUp(Patient patient);

    List<Doctor> viewDoctor();

    Doctor getDoctorById(Long doctorId);

}
