package com.isep.management_application;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;

public class Reporter {
    private String report;

    public Reporter() {
        this.report = "";
    }

    public String generateReport(Projet projet) {
        this.report = projet.toString();
        /*for (Tache tache : projet.getTaches()) {
            this.report += '\n' +tache.toString();
        }*/
        System.out.println(this.report);
        return this.report;
    }

    public void exportReport(String report) {
        // export report to a pdf file
        try (FileOutputStream fos = new FileOutputStream("report.pdf")) {
            PdfWriter writer = new PdfWriter(fos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            document.add(new Paragraph(report));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}