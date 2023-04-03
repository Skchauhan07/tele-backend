package com.teleconsultation.Service;

import com.teleconsultation.Entity.Prescription;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PrescriptionService {

    public Prescription add(Prescription prescription);

    Prescription searchByPatientAndDoctor(Long patientId, Long doctorId);

    List<Prescription> searchByPatient(Long patientId);

    List<Prescription> searchByDoctor(Long doctorId);
}
