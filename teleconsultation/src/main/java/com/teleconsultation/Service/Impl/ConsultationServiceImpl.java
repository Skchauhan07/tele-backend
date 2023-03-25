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
    public void startConsultation(Long doctorId, Pair<Patient, Integer> pair) throws Exception {
        Doctor doctor1 = doctorService.getDoctorById(doctorId);
        Doctor doctor = Doctor.builder()
                .doctorName(doctor1.getDoctorName())
                .doctorId(doctor1.getDoctorId())
                .emailId(doctor1.getEmailId())
                .contact(doctor1.getContact())
                .isAvailable(doctor1.getIsAvailable())
                .password(doctor1.getPassword())
                .prescriptions(doctor1.getPrescriptions())
                .build();
        Patient patient1 = pair.getFirst();
        Date date = new Date();
        Consultation consultation = Consultation.builder()
                .date(date)
                .time(date)
                .doctor(doctor)
                .patient(patient1)
                .build();
        try {
            consultationRepository.save(consultation);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
