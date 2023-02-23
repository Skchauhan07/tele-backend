package com.teleconsultation.Service;

import com.teleconsultation.Entity.Prescription;
import com.teleconsultation.Service.Impl.FileService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
@Service
public class FileServiceImpl implements FileService {

    public byte[] generatePdfFile(Prescription prescription) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            PDFont font = PDType1Font.TIMES_ROMAN;
            float fontSize = 18;
            float leading = 2f * fontSize;

            PDFont titleFont = PDType1Font.HELVETICA_BOLD;
            float titleFontSize = 25;
            String title = "Prescription";

            content.beginText();


            // Draw title at the center of the page
            content.setFont(titleFont, titleFontSize);
            content.newLineAtOffset((page.getMediaBox().getWidth() - font.getStringWidth(title) * fontSize / 1000f) / 2, page.getMediaBox().getHeight() - 50);
            content.showText(title);
            content.setFont(font, fontSize);
            content.newLineAtOffset(-100, -leading*2);

            content.showText("Doctor Name: " + prescription.getDoctor().getDoctorName());
            content.newLineAtOffset(0, -leading);
            content.showText("Patient Name: " + prescription.getPatient().getPatientName());
            content.newLineAtOffset(0, -leading);
            content.showText("Medical Findings " + prescription.getMedicalFinding());
            content.newLineAtOffset(0, -leading);
            content.showText("Medicine Name " + prescription.getMedicineName());
            content.newLineAtOffset(0, -leading);
            content.showText("Dosage " + prescription.getDosage());
            content.newLineAtOffset(0, -leading);
            content.showText("Duration " + prescription.getDuration());
            content.endText();
            content.close();
            document.save(stream);
            return stream.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to generate PDF file: " + e.getMessage());
        }
    }
}
