package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Prescription;
import org.springframework.stereotype.Service;


public interface PrescriptionService {

    public Prescription add(Prescription prescription);

    Prescription searchByPatientAndDoctor(Long patientId, Long doctorId);
}
