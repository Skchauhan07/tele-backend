package com.teleconsultation.Service.Impl;


import com.teleconsultation.Entity.HealthRecord;

public interface HealthRecordService {
    HealthRecord viewHealthRecord(Long patientId);
    HealthRecord addHealthRecord(HealthRecord healthRecord);
}
