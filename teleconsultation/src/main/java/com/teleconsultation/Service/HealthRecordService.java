package com.teleconsultation.Service;


import com.teleconsultation.Entity.HealthRecord;

public interface HealthRecordService {
    HealthRecord viewHealthRecord(Long patientId);
    HealthRecord addHealthRecord(HealthRecord healthRecord);
}
