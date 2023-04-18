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

import java.util.*;

@Service
public class QueueServiceImpl implements QueueService {
    private Map<String, Queue<Pair<Patient, Integer>>> mapQueue = new HashMap<>();
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void addPatient(Patient patient, Integer roomId, String specialization) {
        specialization = specialization.toLowerCase();
        Pair<Patient, Integer> pair = Pair.of(patient, roomId);

        System.out.println(patient.getPatientName()+" Added to queue of "+specialization);

        if (mapQueue.containsKey(specialization)) {
            mapQueue.get(specialization).add(pair);
        } else {
            Queue<Pair<Patient, Integer>> queue = new LinkedList<>();
            queue.add(pair);
            mapQueue.put(specialization, queue);
        }
    }

    @Override
    public void leavePatientQueue(Patient patient) {
        patientRepository.updateStatusQueue("NO", patient.getPatientId());
    }
    @Override
    public Pair<Patient, Integer> getNext(String specialization){
        specialization = specialization.toLowerCase();
        if (mapQueue.containsKey(specialization)) {
            Pair<Patient, Integer> pair = mapQueue.get(specialization).poll();
            if(pair.getFirst().getStatusQueue().equals("NO")){
                return getNext(specialization);
            }
            System.out.println("Popped From Queue " + pair.getFirst().getPatientName() + "from " +specialization + " Queue");
            if (mapQueue.get(specialization).isEmpty()) {
                mapQueue.remove(specialization);
                System.out.println("Queue associated with "+specialization+ " is empty");
            }
            return pair;
        } else {
            return null;
        }
    }
}
