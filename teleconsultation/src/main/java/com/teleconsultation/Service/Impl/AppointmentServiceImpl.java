package com.teleconsultation.Service.Impl;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Model.AppointmentModel;
import com.teleconsultation.Repository.AppointmentRepository;
import com.teleconsultation.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Long saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment).getAppointmentId();
    }

    @Override
    public Appointment getAppointmentDetailsOfPatient(Long patientId) {
        return null;
    }
}
