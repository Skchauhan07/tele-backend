package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Consultation;
import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Repository.ConsultationRepository;
import com.teleconsultation.Service.ConsultationService;
import com.teleconsultation.Service.DoctorService;
import com.teleconsultation.Service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private DoctorService doctorService;
    @Override
    public Integer startConsultation(Long doctorId, Pair<Patient, Integer> pair) {
        Doctor doctor1 = doctorService.getDoctorById(doctorId);
        Patient patient1 = pair.getFirst();
        Integer roomId = pair.getSecond();
        Date date = new Date();
        Consultation consultation = Consultation.builder()
                .date(date)
                .time(date)
                .doctor(doctor1)
                .patient(patient1)
                .build();
        consultationRepository.save(consultation);
        return roomId;
    }
}
