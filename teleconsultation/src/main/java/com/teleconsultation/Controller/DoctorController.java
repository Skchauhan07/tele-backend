package com.teleconsultation.Controller;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.HealthRecord;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Model.HealthRecordModel;
import com.teleconsultation.Model.PrescriptionModel;
import com.teleconsultation.Repository.DoctorRepository;
import com.teleconsultation.Service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/doctor")
public class DoctorController {
    //login
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HealthRecordService healthRecordService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private QueueService queueService;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public boolean login(Doctor doctor){
        if(doctorService.doctorLogin(doctor)){
            return true;
        }
        return false;
    }

    //after adding doctor initially he is not in queue. so statusQueue = false
    @PostMapping("/add")
    public Doctor addDoctor(@Valid @RequestBody Doctor doctor){
        Doctor doctor1 = Doctor.builder()
                .doctorName(doctor.getDoctorName())
                .contact(doctor.getContact())
                .emailId(doctor.getEmailId())
                .password(doctor.getPassword())
                .statusQueue(false)
                .build();
        return doctorService.addDoctor(doctor1);
    }

    @GetMapping("/view")
    public List<Doctor> viewDoctor(){
        return doctorService.viewDoctor();
    }

    @GetMapping("/get-name/{doctorId}")
    public String getDoctorName(@PathVariable Long doctorId){
        return doctorService.getDoctorById(doctorId).getDoctorName();
    }
    //view Health Record of a particular patient
    @GetMapping("/healthrecord/{patientId}")
    public HealthRecordModel viewHealthRecord(@PathVariable("patientId") Long patientId){
        HealthRecord healthRecord = healthRecordService.viewHealthRecord(patientId);
        HealthRecordModel healthRecordModel = HealthRecordModel.builder()
                .medicalRecords(healthRecord.getMedicalRecords())
                .attachment(healthRecord.getAttachment())
                .build();
        return healthRecordModel;
    }

    // add Health Record of a particular Patient by Doctor
    @PostMapping("/healthrecord/{patientId}")
    public HealthRecordModel addHealthRecord(@PathVariable Long patientId, @RequestBody HealthRecordModel healthRecordModel){
        HealthRecord healthRecord = HealthRecord.builder()
                .medicalRecords(healthRecordModel.getMedicalRecords())
                .attachment(healthRecordModel.getAttachment())
                .patient(patientService.getPatientById(patientId))
                .build();
        HealthRecord healthRecord1 = healthRecordService.addHealthRecord(healthRecord);
        HealthRecordModel healthRecordModel1 = HealthRecordModel.builder()
                .medicalRecords(healthRecordModel.getMedicalRecords())
                .attachment(healthRecordModel.getAttachment())
                .build();
        return healthRecordModel1;
    }

    @PostMapping("/add/prescription/{patientId}/{doctorId}")
    public ResponseEntity<Boolean> addPrescription(@PathVariable("patientId") Long patientId, @PathVariable("doctorId") Long doctorId, @RequestBody PrescriptionModel prescriptionModel){
        Prescription prescription = Prescription.builder()
                .medicalFinding(prescriptionModel.getMedicalFinding())
                .dosage(prescriptionModel.getDosage())
                .medicineName(prescriptionModel.getMedicineName())
                .duration(prescriptionModel.getDuration())
                .doctor(doctorService.getDoctorById(doctorId))
                .patient(patientService.getPatientById(patientId))
                .build();
        Prescription createdPrescription = prescriptionService.add(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

}
