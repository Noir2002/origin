package com.isep.management_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProjectDetailsController {
    @FXML
    private TextField idField;
    @FXML
    private TextField projectNameField;
    @FXML
    private TextField budgetField;
    @FXML
    private TextField realCostField;
    @FXML
    private TextField dateLimitField;
    @FXML
    private ListView<Employe> memberListView;
    @FXML
    private ListView<Tache> aFaireListView;
    @FXML
    private ListView<Tache> enCoursListView;
    @FXML
    private ListView<Tache> termineListView;

    private Projet project;
    private ProjectController mainController;
    private ObservableList<Employe> observableMembers;
    private ObservableList<Tache> observableAFaireTasks;
    private ObservableList<Tache> observableEnCoursTasks;
    private ObservableList<Tache> observableTermineTasks;

    public void setProject(Projet project) {
        this.project = project;
        if (project != null) {
            idField.setText(String.valueOf(project.getId()));
            projectNameField.setText(project.getNom());
            budgetField.setText(String.valueOf(project.getBudget()));
            realCostField.setText(String.valueOf(project.getRealCost()));
            dateLimitField.setText(project.getDateLimit());
            observableMembers = FXCollections.observableArrayList(project.getMembresProjet());
            observableAFaireTasks = FXCollections.observableArrayList(Kanban.getTachesAFaire());
            observableEnCoursTasks = FXCollections.observableArrayList(Kanban.getTachesEnCours());
            observableTermineTasks = FXCollections.observableArrayList(Kanban.getTachesTermine());
        } else {
            observableMembers = FXCollections.observableArrayList();
            observableAFaireTasks = FXCollections.observableArrayList();
            observableEnCoursTasks = FXCollections.observableArrayList();
            observableTermineTasks = FXCollections.observableArrayList();
        }
        memberListView.setItems(observableMembers);
        aFaireListView.setItems(observableAFaireTasks);
        enCoursListView.setItems(observableEnCoursTasks);
        termineListView.setItems(observableTermineTasks);
    }

    public void setMainController(ProjectController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleSaveButton() {
        if (project == null) {
            project = new Projet(Integer.parseInt(idField.getText()), projectNameField.getText(), dateLimitField.getText(), Double.parseDouble(budgetField.getText()), Double.parseDouble(realCostField.getText()));
        } else {
            project.setId(Integer.parseInt(idField.getText()));
            project.setNom(projectNameField.getText());
            project.setBudget(Double.parseDouble(budgetField.getText()));
            project.setRealCost(Double.parseDouble(realCostField.getText()));
            project.setDateLimit(dateLimitField.getText());
        }
        mainController.refreshProjectList();
        closeWindow();
    }

    @FXML
    private void handleCancelButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have unsaved changes. Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                closeWindow();
            }
        });
    }

    @FXML
    private void handleAddMemberButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/isep/management_application/addMember.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Members");

            Scene scene = new Scene(root, 300, 400);
            stage.setScene(scene);

            AddMemberController controller = loader.getController();
            controller.setProjectDetailsController(this);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteMemberButton() {
        Employe selectedMember = memberListView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            observableMembers.remove(selectedMember);
            project.deleteMembre(selectedMember);
        }
    }

    @FXML
    private void handleAddTaskButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/isep/management_application/addTask.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Task");

            Scene scene = new Scene(root, 400, 600);
            stage.setScene(scene);

            AddTaskController controller = loader.getController();
            controller.setProjectDetailsController(this);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteTaskButton() {
        Tache selectedTask = aFaireListView.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            selectedTask = enCoursListView.getSelectionModel().getSelectedItem();
        }
        if (selectedTask == null) {
            selectedTask = termineListView.getSelectionModel().getSelectedItem();
        }
        if (selectedTask != null) {
            observableAFaireTasks.remove(selectedTask);
            observableEnCoursTasks.remove(selectedTask);
            observableTermineTasks.remove(selectedTask);
            project.deleteTache(selectedTask);
        }
    }

    public void addMember(Employe member) {
        if (!observableMembers.contains(member)) {
            observableMembers.add(member);
            project.addMembre(member);
        }
    }

    public void addTask(Tache task) {
        switch (task.getCategory()) {
            case "a faire":
                observableAFaireTasks.add(task);
                break;
            case "en cours":
                observableEnCoursTasks.add(task);
                break;
            case "termine":
                observableTermineTasks.add(task);
                break;
        }
        project.addTache(task);
    }

    private void closeWindow() {
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }
}