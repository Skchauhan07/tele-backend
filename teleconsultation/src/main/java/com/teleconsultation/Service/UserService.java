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

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if user is a patient
        Patient patient = patientRepository.findByPhoneNumber(username);
        if (patient != null) {
            return new User(patient.getPhoneNumber(), patient.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_PATIENT"));
        }

        // Check if user is a doctor
        Doctor doctor = doctorRepository.findByEmailId(username);
        if (doctor != null) {
            return new User(doctor.getEmailId(), doctor.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_DOCTOR"));
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
