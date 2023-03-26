package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Repository.DoctorRepository;
import com.teleconsultation.Repository.PatientRepository;
import com.teleconsultation.Service.ConsultationService;
import com.teleconsultation.Service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class QueueServiceImpl implements QueueService {
    private Queue<Pair<Patient, Integer>> pairQueue = new LinkedList<>();
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultationService consultationService;
    public void addPatientToQueue(Patient patient, Integer roomId) {
        patientRepository.updateStatusQueue("YES", patient.getPatientId());
        System.out.println(patient.getPatientName() + " Added to Queue");
        Pair<Patient, Integer> pair = Pair.of(patient, roomId);
        pairQueue.offer(pair);
    }

    @Override
    public void leavePatientQueue(Patient patient) {
        patient.setStatusQueue("NO");
    }

    @Override
    public Pair<Patient, Integer> getNextInPairQueue() {
        Pair<Patient, Integer> patientIntegerPair = pairQueue.poll();
        if(patientIntegerPair != null && patientIntegerPair.getFirst().getStatusQueue() == "YES"){
            patientRepository.updateStatusQueue("NO", patientIntegerPair.getFirst().getPatientId());
            System.out.println(patientIntegerPair.getFirst().getPatientName() + " Popped from Queue");
            return patientIntegerPair;
        } else if (patientIntegerPair == null) {
            return null;
        }
        return getNextInPairQueue();
    }
}
