package com.teleconsultation.Controller;

import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Model.JwtRequest;
import com.teleconsultation.Model.JwtResponse;
import com.teleconsultation.Model.PatientModel;
import com.teleconsultation.Service.DoctorService;
import com.teleconsultation.Service.PatientService;
import com.teleconsultation.Service.UserService;
import com.teleconsultation.Utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class HomeController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String home() {
        return "Welcome to Daily Code Buffer!!";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);
        return  new JwtResponse(token);
    }

    @PostMapping("/authenticate/add")
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
