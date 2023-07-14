package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Appointment;
import com.teleconsultation.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> searchAppointmentByPatientAndDate(Patient patient, Date date);
}
