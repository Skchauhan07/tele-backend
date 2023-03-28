package com.teleconsultation.Service;

import com.teleconsultation.Entity.Prescription;
import org.springframework.stereotype.Service;


public interface FileService {
    byte[] generatePdfFile(Prescription prescription);
}
