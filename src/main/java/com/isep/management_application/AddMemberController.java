package com.isep.management_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class AddMemberController {
    @FXML
    private ListView<Employe> allEmployeesListView;

    private ProjectDetailsController projectDetailsController;
    private ObservableList<Employe> allEmployees;

    @FXML
    public void initialize() {
        allEmployees = FXCollections.observableArrayList(Employe.getEmployes());
        allEmployeesListView.setItems(allEmployees);
        allEmployeesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setProjectDetailsController(ProjectDetailsController projectDetailsController) {
        this.projectDetailsController = projectDetailsController;
    }

    @FXML
    private void handleAddButton() {
        for (Employe selectedEmployee : allEmployeesListView.getSelectionModel().getSelectedItems()) {
            projectDetailsController.addMember(selectedEmployee);
        }
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) allEmployeesListView.getScene().getWindow();
        stage.close();
    }
}