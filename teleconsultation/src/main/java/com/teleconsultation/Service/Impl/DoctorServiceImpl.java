package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Repository.DoctorRepository;
import com.teleconsultation.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public Long doctorLogin(String emailId, String password) {
        Doctor doctor = doctorRepository.findDoctorByEmailIdAndPassword(emailId, password);
        if(doctor != null){
            return doctor.getDoctorId();
        }
        return -1L;
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void scheduleFollowUp(Patient patient) {
    }

    @Override
    public Prescription issuePrescription(Patient patient) {
        return null;
    }

    @Override
    public List<Patient> viewFollowUp() {
        return null;
    }

    @Override
    public void cancelFollowUp(Patient patient) {

    }

    @Override
    public List<Doctor> viewDoctor() {
        List<Doctor> list =  doctorRepository.findAll();
        return list;
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findDoctorByDoctorId(doctorId);
    }

    @Override
    public void updateIsAvailable(String no, Long doctorId) {
        doctorRepository.updateStatusQueue(no, doctorId);
    }

}
