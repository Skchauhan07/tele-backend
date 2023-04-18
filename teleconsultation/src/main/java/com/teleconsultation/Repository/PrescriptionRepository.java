package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Prescription searchPrescriptionByDoctorAndPatient(Doctor doctor, Patient patient);
    List<Prescription> searchPrescriptionByPatient(Patient patient);
    List<Prescription> searchPrescriptionByDoctorOrderByDateDesc(Doctor doctor);
    List<Prescription> searchPrescriptionByPatientOrderByDateDesc(Patient patient);
    Prescription searchPrescriptionByPrescriptionId(Long prescriptionId);
}
