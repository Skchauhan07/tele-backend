package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Model.DoctorModel;
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
    public Doctor getDoctorByContact(String contact) {
        return doctorRepository.findDoctorByContact(contact);
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

    @Override
    public Doctor findDoctorByContact(String phoneNumber) {
        return doctorRepository.findDoctorByContact(phoneNumber);
    }

    @Override
    public void updateDoctorAttributes(Doctor doctor, DoctorModel doctorModel) {
        if (doctorModel.getDoctorName() != null) {
            doctor.setDoctorName(doctorModel.getDoctorName());
        }
        if (doctorModel.getContact() != null) {
            doctor.setContact(doctorModel.getContact());
        }
        if (doctorModel.getSpecialization() != null) {
            doctor.setSpecialization(doctorModel.getSpecialization());
        }
        if (doctorModel.getGender() != null) {
            doctor.setGender(doctorModel.getGender());
        }
        if (doctorModel.getAge() != null) {
            doctor.setAge(doctorModel.getAge());
        }
        if (doctorModel.getEmailId() != null) {
            doctor.setEmailId(doctorModel.getEmailId());
        }
        if (doctorModel.getIsAvailable() != null) {
            doctor.setIsAvailable(doctorModel.getIsAvailable());
        }
        doctorRepository.updateDoctor(doctor.getDoctorName(), doctor.getContact(), doctor.getSpecialization(), doctor.getGender(), doctor.getAge(), doctor.getEmailId(), doctor.getIsAvailable(), doctor.getDoctorId());
    }

}
