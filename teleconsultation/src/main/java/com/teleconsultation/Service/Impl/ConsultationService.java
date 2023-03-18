package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Consultation;
import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Model.ConsultationModel;

public interface ConsultationService {
    public Integer startConsultation(Long doctorId);
}
