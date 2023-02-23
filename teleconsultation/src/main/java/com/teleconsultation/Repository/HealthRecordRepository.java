package com.teleconsultation.Repository;

import com.teleconsultation.Entity.HealthRecord;
import com.teleconsultation.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    HealthRecord findByPatientPatientId(Long patientId);
}
