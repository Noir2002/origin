package com.isep.management_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

import java.awt.*;

public class AddTaskController {
    @FXML
    private TextField taskIdField;
    @FXML
    private TextField taskNameField;
    @FXML
    private TextField taskBudgetField;
    @FXML
    private TextField taskRealCostField;
    @FXML
    private TextField taskDateLimitField;
    @FXML
    private TextField taskPriorityField;
    @FXML
    private ComboBox<String> taskCategoryField;
    @FXML
    private TextField taskDescriptionField;
    @FXML
    private TextField taskCommentsField;
    @FXML
    private ListView<Employe> taskMembersListView;

    private ProjectDetailsController projectDetailsController;
    private ObservableList<Employe> allEmployees;

    public void setProjectDetailsController(ProjectDetailsController projectDetailsController) {
        this.projectDetailsController = projectDetailsController;
    }

    @FXML
    public void initialize() {
        taskCategoryField.getItems().addAll("a faire", "en cours", "termine");
        allEmployees = FXCollections.observableArrayList(Employe.getEmployes());
        taskMembersListView.setItems(allEmployees);
        taskMembersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleAddButton() {
        Tache newTask = new Tache(
                Integer.parseInt(taskIdField.getText()),
                taskNameField.getText(),
                taskDateLimitField.getText(),
                Double.parseDouble(taskBudgetField.getText()),
                Double.parseDouble(taskRealCostField.getText()),
                Integer.parseInt(taskPriorityField.getText()),
                taskCategoryField.getValue(),
                taskDescriptionField.getText(),
                taskCommentsField.getText()
        );
        for (Employe selectedEmployee : taskMembersListView.getSelectionModel().getSelectedItems()) {
            newTask.addMembre(selectedEmployee);
        }
        projectDetailsController.addTask(newTask);
        closeWindow();
    }

    @FXML
    private void handleCancelButton() {
        closeWindow();
    }

    @FXML
    private void handleAddMemberButton() {
        Stage addMemberStage = new Stage();
        addMemberStage.setTitle("Add Members");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ListView<Employe> allEmployeesListView = new ListView<>();
        allEmployeesListView.setItems(allEmployees);
        allEmployeesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            for (Employe selectedEmployee : allEmployeesListView.getSelectionModel().getSelectedItems()) {
                if (!taskMembersListView.getItems().contains(selectedEmployee)) {
                    taskMembersListView.getItems().add(selectedEmployee);
                }
            }
            addMemberStage.close();
        });

        layout.getChildren().addAll(new Label("Select Members:"), allEmployeesListView, addButton);

        Scene scene = new Scene(layout, 300, 400);
        addMemberStage.setScene(scene);
        addMemberStage.initModality(Modality.APPLICATION_MODAL);
        addMemberStage.showAndWait();
    }

    @FXML
    private void handleDeleteMemberButton() {
        Employe selectedMember = taskMembersListView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            taskMembersListView.getItems().remove(selectedMember);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) taskIdField.getScene().getWindow();
        stage.close();
    }
}