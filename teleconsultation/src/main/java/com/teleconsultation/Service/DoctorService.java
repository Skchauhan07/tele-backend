package com.teleconsultation.Service;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;

import java.util.List;
import java.util.Optional;


public interface DoctorService {
    public Long doctorLogin(String emailId, String password);
    public Doctor addDoctor(Doctor doctor);
    public void scheduleFollowUp(Patient patient);
    public Prescription issuePrescription(Patient patient);
    public List<Patient> viewFollowUp();
    public void cancelFollowUp(Patient patient);

    List<Doctor> viewDoctor();

    Doctor getDoctorById(Long doctorId);

    void updateIsAvailable(String no, Long doctorId);
}
