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
    Doctor findDoctorByEmailIdAndPassword(String emailId, String password);
    Doctor findDoctorByDoctorId(Long id);
    @Transactional
    @Modifying
    @Query("update Doctor p set p.isAvailable = ?1 where p.doctorId = ?2")
    int updateStatusQueue(@NonNull String isAvailable, @NonNull Long doctorId);

    Doctor findByContact(String contact);
}
