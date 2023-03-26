package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Transactional
    @Modifying
    @Query("update Patient p set p.statusQueue = ?1 where p.patientId = ?2")
    int updateStatusQueue(@NonNull String statusQueue, @NonNull Long patientIds);
    Patient findPatientByPatientId(Long id);
    List<Patient> findPatientByPhoneNumber(String phoneNumber);
}
