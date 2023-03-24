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
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        // Check if user is a doctor
        Doctor doctor = doctorRepository.findDoctorByPhoneNumber(phoneNumber);
        if (doctor != null) {
            return new User(doctor.getPhoneNumber(), doctor.getPhoneNumber(),
                    AuthorityUtils.createAuthorityList("ROLE_DOCTOR"));
        }

        // Check if user is a patient
        List<Patient> patients = patientRepository.findPatientByPhoneNumber(phoneNumber);
        if (patient != null) {
            return new User(patient.getPhoneNumber(), patient.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_PATIENT"));
        }



        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
