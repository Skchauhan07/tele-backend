package com.teleconsultation.Service;

import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Repository.DoctorRepository;
import com.teleconsultation.Repository.PatientRepository;
import com.teleconsultation.Service.Impl.ConsultationService;
import com.teleconsultation.Service.Impl.QueueService;
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
        patient.setStatusQueue(true);
        Pair<Patient, Integer> pair = Pair.of(patient, roomId);
        pairQueue.offer(pair);
    }

    @Override
    public void leavePatientQueue(Patient patient) {
        patient.setStatusQueue(false);
    }

    @Override
    public Pair<Patient, Integer> getNextInPairQueue() {
        Pair<Patient, Integer> patientIntegerPair = pairQueue.poll();
        if(patientIntegerPair != null && patientIntegerPair.getFirst().isStatusQueue()){
            patientIntegerPair.getFirst().setStatusQueue(false);
            return patientIntegerPair;
        } else if (patientIntegerPair == null) {
            return null;
        }
        return getNextInPairQueue();
    }
}
