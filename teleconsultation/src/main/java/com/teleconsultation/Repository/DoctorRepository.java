package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmailId(Doctor doctor);
    Doctor findDoctorByDoctorId(Long id);
}
