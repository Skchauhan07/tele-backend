package com.teleconsultation.Controller;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Model.AppointmentModel;
import com.teleconsultation.Model.PatientModel;
import com.teleconsultation.Model.PrescriptionModel;
import com.teleconsultation.Service.AppointmentService;
import com.teleconsultation.Service.FileService;
import com.teleconsultation.Service.PatientService;
import com.teleconsultation.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private FileService fileService;
    // Patient joins the Queue so set status = true.
    @PostMapping("/join-queue/{patientId}")
    public boolean joinQueue(@PathVariable Long patientId, @RequestParam("roomId") Integer roomId){
        //if patient already in queue then its status is yes
        Patient patient = patientService.getPatientById(patientId);
        if(patient.getStatusQueue().equals("YES")){
            return false;
        }
        patientService.joinQueue(patient, roomId);
        return true;
    }

    // get patient Name
    @GetMapping("/get-name/{patientId}")
    public String getPatientName(@PathVariable("patientId") Long patientId){
        return patientService.getPatientById(patientId).getPatientName();
    }
    @GetMapping("/get-patient/{patientId}")
    public ResponseEntity<PatientModel> getPatient(@PathVariable("patientId") Long patientId){
        Patient patient = patientService.getPatientById(patientId);
        if(patient == null) return ResponseEntity.notFound().build();
        PatientModel patientModel = PatientModel.builder()
                .patientId(patient.getPatientId())
                .patientName(patient.getPatientName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .medicalHistory(patient.getMedicalHistory())
                .phoneNumber(patient.getPhoneNumber())
                .statusQueue(patient.getStatusQueue())
                .build();
        return ResponseEntity.ok().body(patientModel);
    }

    // add patient
    @PostMapping("/add")
    public Patient addPatient(@RequestBody Patient patient){
        patient.setStatusQueue("NO");
        return patientService.addPatient(patient);
    }

    // existing phone number ka patients list
    @GetMapping("/patient-list/phone-number")
    public ResponseEntity<List<PatientModel>> phoneNumber(@RequestParam Map<String, String> obj){
        System.out.println(obj.get("phoneNumber"));
        List<Patient> patients = patientService.getPatientListForPhoneNumber(obj.get("phoneNumber"));
        List<PatientModel> patientModelList = new ArrayList<>();
        for(Patient patient : patients){
            PatientModel patientModel = PatientModel.builder()
                    .patientId(patient.getPatientId())
                    .age(patient.getAge())
                    .patientName(patient.getPatientName())
                    .phoneNumber(patient.getPhoneNumber())
                    .gender(patient.getGender())
                    .medicalHistory(patient.getMedicalHistory())
                    .statusQueue(patient.getStatusQueue())
                    .build();
            patientModelList.add(patientModel);
        }
        return ResponseEntity.ok(patientModelList);
    }

    //Get Prescription
    @GetMapping("/prescription/{patientId}/{doctorId}")
    public ResponseEntity<byte[]> downloadPrescription(@PathVariable Long patientId, @PathVariable Long doctorId) {
        Prescription prescription = prescriptionService.searchByPatientAndDoctor(patientId, doctorId);
        if(prescription == null){
            return ResponseEntity.notFound().build();
        }
        byte[] pdfFile = fileService.generatePdfFile(prescription);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfFile.length);
        headers.setContentDispositionFormData("attachment", "prescription.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfFile);
    }

    @PostMapping("/appointment")
    public Long getAppointment(@RequestParam("patientId") Long patientId, @RequestBody AppointmentModel appointmentModel){
        Appointment appointment = Appointment.builder()
                .date(appointmentModel.getDate())
                .symptoms(appointmentModel.getSymptoms())
                .description(appointmentModel.getDescription())
                .patient(patientService.getPatientById(patientId))
                .build();
        //returning appointment id
        return appointmentService.saveAppointment(appointment);
    }

    @GetMapping("/prescription-list/{patientId}")
    public ResponseEntity<List<PrescriptionModel>> getPrescriptionsOfPatient(@PathVariable("patientId") Long patientId){
        List<Prescription> prescriptions = prescriptionService.searchByPatient(patientId);
        if(prescriptions == null)
            return ResponseEntity.notFound().build();
        List<PrescriptionModel> prescriptionModels = new ArrayList<>();
        for (Prescription prescription : prescriptions){
            PrescriptionModel prescriptionModel = PrescriptionModel.builder()
                    .date(prescription.getDate())
                    .dosage(prescription.getDosage())
                    .duration(prescription.getDuration())
                    .medicalFinding(prescription.getMedicalFinding())
                    .medicineName(prescription.getMedicineName())
                    .build();
            prescriptionModels.add(prescriptionModel);
        }
        return ResponseEntity.ok(prescriptionModels);
    }

}
