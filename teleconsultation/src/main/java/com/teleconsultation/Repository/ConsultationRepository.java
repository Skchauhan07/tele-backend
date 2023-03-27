package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Consultation;
import com.teleconsultation.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByDoctor(Doctor doctor);
}
