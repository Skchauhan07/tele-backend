package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Repository.PatientRepository;
import com.teleconsultation.Repository.PrescriptionRepository;
import com.teleconsultation.Service.PatientService;
import com.teleconsultation.Service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QueueService queueService;
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public boolean patientLogin(Patient patient) {
        return false;
    }

    @Override
    public void updateStatusQueue(String status, Long patientId) {
        patientRepository.updateStatusQueue(status, patientId);
    }

    @Override
    public void joinQueue(Patient patient, Integer roomId, String specialization) {
        queueService.addPatient(patient, roomId, specialization);
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findPatientByPatientId(patientId);
    }

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getPatientListForPhoneNumber(String phoneNumber) {

        List<Patient> patients = patientRepository.findPatientByPhoneNumber(phoneNumber);
        if(patients == null){
            return null;
        }
        for(Patient patient : patients){
            System.out.println(patient.getPatientName());
        }
        return patients;
    }
}
