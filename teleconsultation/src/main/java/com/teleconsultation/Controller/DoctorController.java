package com.teleconsultation.Controller;

import com.teleconsultation.Entity.*;
import com.teleconsultation.Model.ConsultationModel;
import com.teleconsultation.Model.HealthRecordModel;
import com.teleconsultation.Model.PrescriptionModel;
import com.teleconsultation.Repository.DoctorRepository;
import com.teleconsultation.Service.*;
import com.teleconsultation.Service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.teleconsultation.Model.DoctorModel;

import javax.validation.Valid;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
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
    @Autowired
    private ConsultationService consultationService;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    //after adding doctor initially he is not in queue. so statusQueue = false
    @PostMapping("/add")
    public Doctor addDoctor(@Valid @RequestBody Doctor doctor){
        Doctor doctor1 = Doctor.builder()
                .doctorName(doctor.getDoctorName())
                .contact(doctor.getContact())
                .emailId(doctor.getEmailId())
                .gender(doctor.getGender())
                .specialization(doctor.getSpecialization())
                .age(doctor.getAge())
                .isAvailable("YES")
                .role("ROLE_DOCTOR")
                .build();
        return doctorService.addDoctor(doctor1);
    }

    @GetMapping("/role")
    public String getRole(@RequestParam String phoneNumber){
        String role = "";
        try{
            role = doctorService.getDoctorByContact(phoneNumber).getRole();
        }
        catch (Exception e){
            return "Not Found";
        }
        return role;
    }
    @PatchMapping("/update-doctor/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable("id") Long id, @RequestBody DoctorModel doctorModel) {
        Doctor doctor = doctorService.getDoctorById(id);
        if(doctor == null){
            return ResponseEntity.notFound().build();
        }
        doctorService.updateDoctorAttributes(doctor, doctorModel);
        return ResponseEntity.ok("Updated Doctor");
    }
    @PostMapping("/consultation/{doctorId}")
    public int startConsultation(@PathVariable("doctorId") Long doctorId) throws Exception {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        String specialization = doctor.getSpecialization();
        Pair<Patient, Integer> pair = queueService.getNext(specialization);
        if(pair == null){
            System.out.println("Problem in Popping Patient in startConsultation(Doctor Controller)");
            return -1;
        }
//        if(doctor.getIsAvailable().equals("NO")){
//            return -1;
//        }
//        set doctor availability
//        doctor.setIsAvailable("NO");
//        doctorService.updateIsAvailable("NO", doctorId);
        Patient patient = patientService.getPatientById(pair.getFirst().getPatientId());
        consultationService.startConsultation(doctor, patient);
        return pair.getSecond();
    }

    @GetMapping("/get-history/{phoneNumber}")
    public ResponseEntity<List<ConsultationModel>> getHistory(@PathVariable("phoneNumber") String phoneNumber){
        Doctor doctor = doctorService.getDoctorByContact(phoneNumber);
        List<Consultation> consultations = consultationService.getHistory(doctor.getDoctorId());
        List<ConsultationModel> consultationModels = new ArrayList<>();
        if(consultations == null){
            return ResponseEntity.notFound().build();
        }
        for(Consultation consultation : consultations){
            ConsultationModel consultationModel = ConsultationModel.builder()
                    .date(consultation.getDate())
                    .time(consultation.getTime())
                    .doctorId(consultation.getDoctor().getDoctorId())
                    .patientId(consultation.getPatient().getPatientId())
                    .patientName(patientService.getPatientById(consultation.getPatient().getPatientId()).getPatientName())
                    .build();
            consultationModels.add(consultationModel);
        }
        return ResponseEntity.ok(consultationModels);
    }


    // Use Doctor Model Here
    @GetMapping("/view")
    public List<Doctor> viewDoctor(){
        return doctorService.viewDoctor();
    }

    @GetMapping("/get-name/{doctorId}")
    public String getDoctorName(@PathVariable("doctorId") Long doctorId){
        return doctorService.getDoctorById(doctorId).getDoctorName();
    }
    @GetMapping("/doctor-by-contact/{phoneNumber}")
    public ResponseEntity<DoctorModel> getDoctorName(@PathVariable("phoneNumber") String phoneNumber){
        Doctor doctor = doctorService.getDoctorByContact(phoneNumber);
        if(doctor == null){
            return ResponseEntity.notFound().build();
        }
        DoctorModel doctorModel = DoctorModel.builder()
                .doctorId(doctor.getDoctorId())
                .doctorName(doctor.getDoctorName())
                .contact(doctor.getContact())
                .emailId(doctor.getEmailId())
                .age(doctor.getAge())
                .gender(doctor.getGender())
                .specialization(doctor.getSpecialization())
                .build();
        return ResponseEntity.ok(doctorModel);
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
//        Date todayDate = new Date();
        Prescription prescription = Prescription.builder()
                .date(prescriptionModel.getDate())
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
    @GetMapping("/prescription-list/{doctorId}")
    public ResponseEntity<List<PrescriptionModel>> getPrescriptionsList(@PathVariable("doctorId") Long doctorId){
        List<Prescription> prescriptions = prescriptionService.searchByDoctor(doctorId);
        if(prescriptions == null){
            return ResponseEntity.notFound().build();
        }
        List<PrescriptionModel> prescriptionModels = new ArrayList<>();
        for(Prescription prescription : prescriptions){
            PrescriptionModel prescriptionModel = PrescriptionModel.builder()
                    .date(prescription.getDate())
                    .dosage(prescription.getDosage())
                    .duration(prescription.getDuration())
                    .medicineName(prescription.getMedicineName())
                    .medicalFinding(prescription.getMedicalFinding())
                    .build();
            prescriptionModels.add(prescriptionModel);
        }
        return ResponseEntity.ok(prescriptionModels);
    }
}
