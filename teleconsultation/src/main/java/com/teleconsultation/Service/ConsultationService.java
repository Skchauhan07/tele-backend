package com.teleconsultation.Service;

import com.teleconsultation.Entity.Consultation;
import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Model.ConsultationModel;
import org.springframework.data.util.Pair;

public interface ConsultationService {
    public void startConsultation(Long doctorId, Pair<Patient, Integer> pair);
}
