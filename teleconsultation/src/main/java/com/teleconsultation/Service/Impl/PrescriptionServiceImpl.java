package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Repository.PrescriptionRepository;
import com.teleconsultation.Service.DoctorService;
import com.teleconsultation.Service.PatientService;
import com.teleconsultation.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Override
    public Prescription add(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    //  Handle the Exception no doctor or no patient
    @Override
    public Prescription searchByPatientAndDoctor(Long patientId, Long doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientById(patientId);
        return prescriptionRepository.searchPrescriptionByDoctorAndPatient(doctor, patient);
    }

    @Override
    public List<Prescription> searchByPatient(Long patientId) {
        Patient patient = patientService.getPatientById(patientId);
        if(patient == null) {
            return null;
        }
        return prescriptionRepository.searchPrescriptionByPatientOrderByDateDesc(patient);
    }

    @Override
    public List<Prescription> searchByDoctor(Long doctorId) {
        return prescriptionRepository.searchPrescriptionByDoctorOrderByDateDesc(doctorService.getDoctorById(doctorId));
    }

    @Override
    public Prescription searchByPrescriptionId(Long prescriptionId) {
        return prescriptionRepository.searchPrescriptionByPrescriptionId(prescriptionId);
    }
}
