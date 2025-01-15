package com.isep.management_application;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportController {
    @FXML
    private ListView<Projet> projectListView;
    @FXML
    private VBox detailLayout;

    private Projet selectedProject;

    @FXML
    public void initialize() {
        projectListView.getItems().addAll(Projet.getProjets());
        projectListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Projet project, boolean empty) {
                super.updateItem(project, empty);
                if (empty || project == null) {
                    setText(null);
                } else {
                    setText("ID: " + project.getId() + " - " + project.getNom());
                }
            }
        });

        projectListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectedProject = projectListView.getSelectionModel().getSelectedItem();
                if (selectedProject != null) {
                    Reporter reporter = new Reporter();
                    String report = reporter.generateReport(selectedProject);
                    showProjectDetails(report);
                }
            }
        });
    }

    private void showProjectDetails(String report) {
        detailLayout.getChildren().clear();
        Label reportLabel = new Label("Report: " + report);
        detailLayout.getChildren().addAll(reportLabel);
    }

    @FXML
    private void exportToPDF() {
        if (selectedProject == null) {
            showAlert("No Project Selected", "Please select a project to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        String defaultFileName = "Project_" + selectedProject.getNom().replaceAll("\\s+", "_") + ".pdf";
        fileChooser.setInitialFileName(defaultFileName);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                PdfWriter writer = new PdfWriter(fos);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);
                document.add(new Paragraph("Project Details"));
                document.add(new Paragraph("ID: " + selectedProject.getId()));
                document.add(new Paragraph("Name: " + selectedProject.getNom()));
                document.add(new Paragraph("Date Limit: " + selectedProject.getDateLimit()));
                document.add(new Paragraph("Budget: " + selectedProject.getBudget()));
                document.add(new Paragraph("Real Cost: " + selectedProject.getRealCost()));
                document.close();
                showAlert("Export Successful", "Project details have been exported to PDF.");
            } catch (IOException e) {
                showAlert("Export Failed", "An error occurred while exporting the project details.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}