package com.teleconsultation.Service;

import com.teleconsultation.Entity.Consultation;
import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Model.ConsultationModel;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ConsultationService {
    public void startConsultation(Doctor doctor, Patient patient) throws Exception;

    List<Consultation> getHistory(Long doctorId);
    List<Consultation> getHistoryPatient(Patient patient);
}
