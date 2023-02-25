package com.teleconsultation.Controller;

import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Model.PatientModel;
import com.teleconsultation.Service.Impl.FileService;
import com.teleconsultation.Service.Impl.PatientService;
import com.teleconsultation.Service.Impl.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    private FileService fileService;
    // Patient joins the Queue so set status = true.
    @PostMapping("/join-queue")
    public boolean joinQueue(Patient patient){
        //if patient already in queue
        patient.setStatusQueue(true);
        patientService.joinQueue(patient);
        return true;
    }

    // get patient Name
    @GetMapping("/get-name/{patientId}")
    public String getPatientName(@PathVariable("patientId") Long patientId){
        return patientService.getPatientById(patientId).getPatientName();
    }

    // add patient
    @PostMapping("/add")
    public Patient addPatient(@RequestBody Patient patient){
        return patientService.addPatient(patient);
    }

    // existing phone number ka patients list
    @GetMapping("/patient-list/phone-number")
    public ResponseEntity<List<PatientModel>> phoneNumber(@RequestParam @Valid Map<String, String> obj){
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
                    .statusQueue(patient.isStatusQueue())
                    .build();
            patientModelList.add(patientModel);
        }
        return ResponseEntity.ok(patientModelList);
    }

    //Get Prescription
    @GetMapping("/prescription/{patientId}/{doctorId}")
    public ResponseEntity<byte[]> downloadPrescription(@PathVariable Long patientId, @PathVariable Long doctorId) {
        Prescription prescription = prescriptionService.searchByPatientAndDoctor(patientId, doctorId);
        byte[] pdfFile = fileService.generatePdfFile(prescription);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfFile.length);
        headers.setContentDispositionFormData("attachment", "prescription.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfFile);
    }

}
