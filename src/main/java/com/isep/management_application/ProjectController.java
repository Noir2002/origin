package com.isep.management_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProjectController {
    @FXML
    private ListView<Projet> projectListView;
    private ObservableList<Projet> observableProjects;

    @FXML
    public void initialize() {
        observableProjects = FXCollections.observableArrayList(Projet.getProjets());
        projectListView.setItems(observableProjects);
    }

    @FXML
    private void handleCreateButton() {
        openProjectDetailsWindow(null);
    }

    @FXML
    private void handleEditButton() {
        Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            openProjectDetailsWindow(selectedProject);
        }
    }

    @FXML
    private void handleDeleteButton() {
        Projet selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            Projet.deleteProjet(selectedProject);
            refreshProjectList();
        }
    }

    private void openProjectDetailsWindow(Projet project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/isep/management_application/projectDetails.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(project == null ? "Create Project" : "Edit Project");

            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);

            ProjectDetailsController controller = loader.getController();
            controller.setProject(project);
            controller.setMainController(this);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshProjectList() {
        observableProjects.setAll(Projet.getProjets());
    }
}