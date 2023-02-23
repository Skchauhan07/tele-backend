package com.teleconsultation.Service;

import com.teleconsultation.Entity.Doctor;
import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Repository.DoctorRepository;
import com.teleconsultation.Service.Impl.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoctorServiceTest {
    @Autowired
    private DoctorService doctorService;
    @MockBean
    private DoctorRepository doctorRepository;
    private
    List<Doctor> doctorList;
    @BeforeEach
    void setUp() {
        List<Prescription> prescriptions = new ArrayList<>();
        Prescription prescription = Prescription.builder().build();
        prescriptions.add(prescription);
        doctorList = new ArrayList<>();
        doctorList.add(new Doctor(1L, "Akash", "9898989898", "pass", "a@gamil.com", false, prescriptions));
        doctorList.add(new Doctor(2L, "Anand", "9898989898", "pass", "a@gamil.com", false, prescriptions));
        Mockito.when(doctorRepository.findAll())
                .thenReturn(doctorList);
    }

    @Test
    public void getDoctorList(){
        List<Doctor> actualDoctors = doctorService.viewDoctor();
        assertEquals(doctorList, actualDoctors);
    }
}