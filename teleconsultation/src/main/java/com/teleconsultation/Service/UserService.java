package com.teleconsultation.Service;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.teleconsultation.Repository.PatientRepository;
import com.teleconsultation.Repository.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber){
        // Check if user is a doctor
        Doctor doctor = doctorService.findDoctorByContact(phoneNumber);
        if (doctor != null) {
            return new User(doctor.getContact(), doctor.getContact(),
                    AuthorityUtils.createAuthorityList("ROLE_DOCTOR"));
        }

        // Check if user is a patient
        List<Patient> patients = patientService.getPatientListForPhoneNumber(phoneNumber);
        if (patients != null) {
            return new User(patients.get(0).getPhoneNumber(), patients.get(0).getPhoneNumber(),
                    AuthorityUtils.createAuthorityList("ROLE_PATIENT"));
        }
        throw new UsernameNotFoundException("User Not Found");
    }
}
