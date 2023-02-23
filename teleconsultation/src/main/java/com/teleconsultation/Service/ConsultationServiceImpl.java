package com.teleconsultation.Service;

import com.teleconsultation.Service.Impl.ConsultationService;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Override
    public void startConsultation() {

    }
    //    @Override
    //    public void startConsultation() {
    //        Patient patient = queueService.getNextPatient();
    //        Doctor doctor = queueService.getNextAvailableDoctor();
    //
    //        if (patient == null || doctor == null) {
    //            // Handle the case when either the patient queue or the doctor queue is empty
    //            return;
    //        }
    //
    //        // Start the consultation between the patient and the doctor
    //        // ...
    //    }
}
