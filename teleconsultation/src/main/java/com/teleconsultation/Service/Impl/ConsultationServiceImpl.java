package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Consultation;
import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Repository.ConsultationRepository;
import com.teleconsultation.Service.ConsultationService;
import com.teleconsultation.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private DoctorService doctorService;
    @Override
    public void startConsultation(Doctor doctor, Patient patient) throws Exception {
        Date date = new Date();
        Consultation consultation = Consultation.builder()
                .date(date)
                .time(date)
                .doctor(doctor)
                .patient(patient)
                .build();
        try {
            consultationRepository.save(consultation);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public List<Consultation> getHistory(Long doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        return consultationRepository.findByDoctor(doctor);
    }
}
