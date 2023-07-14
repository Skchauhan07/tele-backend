package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Model.AppointmentModel;
import com.teleconsultation.Repository.AppointmentRepository;
import com.teleconsultation.Service.AppointmentService;
import com.teleconsultation.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Long saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment).getAppointmentId();
    }

    @Override
    public List<Appointment> getAppointmentOfPatient(Patient patient, Date date) {
        return appointmentRepository.searchAppointmentByPatientAndDate(patient, date);
    }
}
