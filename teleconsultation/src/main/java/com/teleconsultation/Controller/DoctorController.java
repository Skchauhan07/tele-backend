package com.teleconsultation.Controller;

import com.teleconsultation.Entity.*;
import com.teleconsultation.Model.AppointmentModel;
import com.teleconsultation.Model.HealthRecordModel;
import com.teleconsultation.Model.PrescriptionModel;
import com.teleconsultation.Service.*;
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
    private AppointmentService appointmentService;
    @Autowired
    private QueueService queueService;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public Long login(@RequestParam String username, @RequestParam String password){
        return doctorService.doctorLogin(username, password);
    }

    //after adding doctor initially he is not in queue. so statusQueue = false
    @PostMapping("/add")
    public Doctor addDoctor(@Valid @RequestBody Doctor doctor){
        Doctor doctor1 = Doctor.builder()
                .doctorName(doctor.getDoctorName())
                .contact(doctor.getContact())
                .emailId(doctor.getEmailId())
                .password(doctor.getPassword())
                .isAvailable("NO")
                .build();
        return doctorService.addDoctor(doctor1);
    }

    @GetMapping("/view")
    public List<Doctor> viewDoctor(){
        return doctorService.viewDoctor();
    }

    @GetMapping("/get-name/{doctorId}")
    public String getDoctorName(@PathVariable("doctorId") Long doctorId){
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


    //Returning Prescription Id
    @PostMapping("/add/prescription/{patientId}/{doctorId}")
    public ResponseEntity<Long> addPrescription(@PathVariable("patientId") Long patientId, @PathVariable("doctorId") Long doctorId, @RequestBody PrescriptionModel prescriptionModel){
//        Date todayDate = new Date();
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor Not Found");
            return ResponseEntity.notFound().build();
        }

        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient Not Fount");
            return ResponseEntity.notFound().build();
        }

        Prescription prescription = Prescription.builder()
                .date(prescriptionModel.getDate())
                .medicalFinding(prescriptionModel.getMedicalFinding())
                .dosage(prescriptionModel.getDosage())
                .medicineName(prescriptionModel.getMedicineName())
                .duration(prescriptionModel.getDuration())
                .doctor(doctor)
                .patient(patient)
                .build();

        Prescription createdPrescription = prescriptionService.add(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrescription.getPrescriptionId());
    }

    @GetMapping("/appointment/get-patient-details")
    public ResponseEntity<AppointmentModel> getAppointmentDetailsOfPatient(@RequestParam("patientId") Long patientId){
        Appointment appointment = appointmentService.getAppointmentDetailsOfPatient(patientId);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }

        AppointmentModel appointmentModel = AppointmentModel.builder()
                .description(appointment.getDescription())
                .symptoms(appointment.getSymptoms())
                .date(appointment.getDate())
                .build();

        return ResponseEntity.ok().body(appointmentModel);
    }

}
