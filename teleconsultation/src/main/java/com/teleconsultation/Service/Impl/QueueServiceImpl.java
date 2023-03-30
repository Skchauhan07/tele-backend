package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Repository.DoctorRepository;
import com.teleconsultation.Repository.PatientRepository;
import com.teleconsultation.Service.ConsultationService;
import com.teleconsultation.Service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
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
        System.out.println(patient.getPatientName() + " Added to Queue " + patient.getStatusQueue());
        Pair<Patient, Integer> pair = Pair.of(patient, roomId);
        pairQueue.offer(pair);
        System.out.println("Queue Size = " + pairQueue.size());
    }

    @Override
    public void leavePatientQueue(Patient patient) {
        patientRepository.updateStatusQueue("NO", patient.getPatientId());
    }

    @Override
    public Pair<Patient, Integer> getNextInPairQueue() {
        Pair<Patient, Integer> patientIntegerPair = pairQueue.poll();
        if(patientIntegerPair != null && patientIntegerPair.getFirst().getStatusQueue().equals("YES")){
            patientRepository.updateStatusQueue("NO", patientIntegerPair.getFirst().getPatientId());
            patientIntegerPair.getFirst().setStatusQueue("NO");
            System.out.println(patientIntegerPair.getFirst().getPatientName() + " Popped from Queue");
            return patientIntegerPair;
        } else if (patientIntegerPair == null) {
            System.out.println("Patient Integer pair is NULL ");
            return null;
        }
        return getNextInPairQueue();
    }

    @Override
    public Integer getSize() {
        return pairQueue.size();
    }
}
