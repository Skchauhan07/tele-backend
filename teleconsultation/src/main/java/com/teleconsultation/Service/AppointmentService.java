package com.teleconsultation.Service;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Model.AppointmentModel;
import org.springframework.stereotype.Service;


public interface AppointmentService {
    Long saveAppointment(Appointment appointment);

    Appointment getAppointmentDetailsOfPatient(Long patientId);
}
