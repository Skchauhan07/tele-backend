package com.teleconsultation.Service;

import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Repository.PatientRepository;
import com.teleconsultation.Repository.PrescriptionRepository;
import com.teleconsultation.Service.Impl.PatientService;
import com.teleconsultation.Service.Impl.QueueService;
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
    public void joinQueue(Patient patient) {
        queueService.addPatientToQueue(patient);
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
        for(Patient patient : patients){
            System.out.println(patient.getPatientName());
        }
        return patients;
    }
}
