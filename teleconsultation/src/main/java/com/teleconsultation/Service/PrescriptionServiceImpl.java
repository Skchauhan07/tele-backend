package com.teleconsultation.Service;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Repository.PrescriptionRepository;
import com.teleconsultation.Service.Impl.DoctorService;
import com.teleconsultation.Service.Impl.PatientService;
import com.teleconsultation.Service.Impl.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Override
    public Prescription add(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    //  Handle the Exception no doctor or no patient
    @Override
    public Prescription searchByPatientAndDoctor(Long patientId, Long doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientById(patientId);
        return prescriptionRepository.searchPrescriptionByDoctorAndPatient(doctor, patient);
    }
}
