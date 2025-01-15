package com.isep.management_application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox sidebarContainer;
    @FXML
    private ListView<Employe> employeeListView;
    private ObservableList<Employe> observableEmployees;

    private Sidebar sidebar;

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
        sidebar.setNavigationHandler(this::navigateTo);
        sidebarContainer.getChildren().add(sidebar.getSidebar());
    }

    @FXML
    public void initialize() {
        observableEmployees = FXCollections.observableArrayList(Employe.getEmployes());
        employeeListView.setItems(observableEmployees);
    }

    private void navigateTo(Parent window) {
        mainPane.setCenter(window);
    }

    @FXML
    private void handleCreateButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/isep/management_application/employeeDetails.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Employee Details");

            Scene scene = new Scene(root, 400, 300);
            stage.setScene(scene);

            EmployeController controller = loader.getController();
            controller.setMainController(this);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButton() {
        Employe selectedEmployee = employeeListView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            Employe.deleteEmploye(selectedEmployee);
            refreshEmployeeList();
        }
    }

    public void refreshEmployeeList() {
        observableEmployees.setAll(Employe.getEmployes());
    }
}