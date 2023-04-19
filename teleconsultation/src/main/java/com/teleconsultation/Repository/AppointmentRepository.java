package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment searchAppointmentByPatientAndDate(Patient patient, Date date);
}
