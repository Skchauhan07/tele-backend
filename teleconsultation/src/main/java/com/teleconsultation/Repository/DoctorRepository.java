package com.teleconsultation.Repository;

import com.teleconsultation.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findDoctorByDoctorId(Long id);
    @Transactional
    @Modifying
    @Query("update Doctor p set p.isAvailable = ?1 where p.doctorId = ?2")
    int updateStatusQueue(@NonNull String isAvailable, @NonNull Long doctorId);


    @Transactional
    @Modifying
    @Query("update Doctor p set p.doctorName = ?1, p.contact = ?2, p.specialization = ?3, p.gender = ?4, p.age = ?5, p.emailId = ?6, p.isAvailable = ?7 where p.doctorId = ?8")
    int updateDoctor(String doctorName,String contact, String specialization, String gender, String age, String emailId, String isAvailable, Long doctorId);
    Doctor findDoctorByContact(String contact);
}
