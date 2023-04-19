package com.teleconsultation.Service;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Entity.Patient;
import com.teleconsultation.Model.AppointmentModel;
import org.springframework.stereotype.Service;

import java.util.Date;


public interface AppointmentService {
    Long saveAppointment(Appointment appointment);
    Appointment getAppointmentOfPatient(Patient patient, Date date);
}
