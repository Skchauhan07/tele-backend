package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findPatientByPatientId(Long id);
    List<Patient> findPatientByPhoneNumber(String phoneNumber);
}
