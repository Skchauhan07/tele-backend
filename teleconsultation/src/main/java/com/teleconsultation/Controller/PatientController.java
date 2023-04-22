package com.teleconsultation.Controller;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Entity.Consultation;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Model.AppointmentModel;
import com.teleconsultation.Model.ConsultationModel;
import com.teleconsultation.Model.PatientModel;
import com.teleconsultation.Model.PrescriptionModel;
import com.teleconsultation.Service.*;
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
@CrossOrigin(origins = "*",allowedHeaders = "*")
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
    @Autowired
    private ConsultationService consultationService;


    // Patient joins the Queue so set status = true.
    @PostMapping("/join-queue/{patientId}/{specialization}")
    public boolean joinQueue(@PathVariable("patientId") Long patientId, @RequestParam("roomId") Integer roomId,@PathVariable("specialization") String specialization){
        //if patient already in queue then its status is yes
        Patient patient = patientService.getPatientById(patientId);
        if(patient.getStatusQueue().equals("YES")){
            return false;
        }
        patientService.updateStatusQueue("YES", patient.getPatientId());
        patient.setStatusQueue("YES");
        patientService.joinQueue(patient, roomId, specialization);
        return true;
    }

    @PostMapping("/left-queue/{patientId}")
    public boolean leftQueue(@PathVariable("patientId") Long patientId){
        if(patientService.getPatientById((patientId)) == null){
            System.out.println("No such Patient");
            return false;
        }
        patientService.updateStatusQueue("NO", patientId);
        return true;
    }

    @GetMapping("/role")
    public String getRole(@RequestParam String phoneNumber){
        String role = "";
        try{
            role = patientService.getPatientListForPhoneNumber(phoneNumber).get(0).getRole();
        }
        catch (Exception e){
            return "Not Found";
        }
        return role;
    }


    // get patient Name
    @GetMapping("/get-name/{patientId}")
    public String getPatientName(@PathVariable("patientId") Long patientId){
        return patientService.getPatientById(patientId).getPatientName();
    }

    @GetMapping("/get-history-patient/{patientId}")
    public ResponseEntity<List<ConsultationModel>> getHistoryOfPatient(@PathVariable Long patientId){
        Patient patient = patientService.getPatientById(patientId);
        List<Consultation> consultations = consultationService.getHistoryPatient(patient);
        if(consultations == null){
            return ResponseEntity.badRequest().build();
        }
        List<ConsultationModel> consultationModels = new ArrayList<>();
        for(Consultation consultation : consultations){
            ConsultationModel consultationModel = ConsultationModel.builder()
                    .patientName(patient.getPatientName())
                    .time(consultation.getTime())
                    .doctorName(consultation.getDoctor().getDoctorName())
                    .date(consultation.getDate())
                    .build();
            consultationModels.add(consultationModel);
        }
        return ResponseEntity.ok(consultationModels);
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
//    @GetMapping("/prescription/{patientId}/{doctorId}")
//    public ResponseEntity<byte[]> downloadPrescription(@PathVariable Long patientId, @PathVariable Long doctorId) {
//        Prescription prescription = prescriptionService.searchByPatientAndDoctor(patientId, doctorId);
//        if(prescription == null){
//            return ResponseEntity.notFound().build();
//        }
//        byte[] pdfFile = fileService.generatePdfFile(prescription);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentLength(pdfFile.length);
//        headers.setContentDispositionFormData("attachment", "prescription.pdf");
//        return ResponseEntity.ok().headers(headers).body(pdfFile);
//    }

    @GetMapping("/prescription/{prescriptionId}")
    public ResponseEntity<byte[]> downloadPrescriptionOfPatient(@PathVariable Long prescriptionId) {
        System.out.println("Here in download");
        Prescription prescription = prescriptionService.searchByPrescriptionId(prescriptionId);
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

    //get an appointment
    @PostMapping("/appointment/{patientId}")
    public ResponseEntity<Long> getAppointment(@PathVariable("patientId") Long patientId, @RequestBody AppointmentModel appointmentModel){
//        Patient patient = patientService.getPatientById(patientId);
//        Appointment appointment1 = appointmentService.getAppointmentOfPatient(patient, appointmentModel.getDate());
//        if(appointment1 != null){
//            return ResponseEntity.badRequest().build();
//        }
        Appointment appointment = Appointment.builder()
                .date(appointmentModel.getDate())
                .symptoms(appointmentModel.getSymptoms())
                .description(appointmentModel.getDescription())
                .specialization(appointmentModel.getSpecialization())
                .patient(patientService.getPatientById(patientId))
                .build();
        //returning appointment id
        Long appId = appointmentService.saveAppointment(appointment);
        return ResponseEntity.ok(appId);
    }

    @GetMapping("/prescription-list/{patientId}")
    public ResponseEntity<List<PrescriptionModel>> getPrescriptionsOfPatient(@PathVariable("patientId") Long patientId){
        List<Prescription> prescriptions = prescriptionService.searchByPatient(patientId);
        if(prescriptions == null)
            return ResponseEntity.notFound().build();
        List<PrescriptionModel> prescriptionModels = new ArrayList<>();
        for (Prescription prescription : prescriptions){
            PrescriptionModel prescriptionModel = PrescriptionModel.builder()
                    .prescriptionId(prescription.getPrescriptionId())
                    .date(prescription.getDate())
                    .dosage(prescription.getDosage())
                    .duration(prescription.getDuration())
                    .medicalFinding(prescription.getMedicalFinding())
                    .medicineName(prescription.getMedicineName())
                    .doctorName(prescription.getDoctor().getDoctorName())
                    .build();
            prescriptionModels.add(prescriptionModel);
        }
        return ResponseEntity.ok(prescriptionModels);
    }

    @PostMapping("/add-patient")
    public ResponseEntity<PatientModel> addPatient(@RequestBody PatientModel patientModel){
        Patient patient = Patient.builder()
                .patientName(patientModel.getPatientName())
                .age(patientModel.getAge())
                .gender(patientModel.getGender())
                .medicalHistory(patientModel.getMedicalHistory())
                .phoneNumber(patientModel.getPhoneNumber())
                .role("ROLE_PATIENT")
                .statusQueue("NO")
                .build();
        patientService.addPatient(patient);
        return ResponseEntity.ok(patientModel);
    }


}
