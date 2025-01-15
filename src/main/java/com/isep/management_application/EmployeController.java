package com.isep.management_application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class EmployeController {
    @FXML private TextField idField;
    @FXML private TextField nomField;
    @FXML private TextField roleField;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleSaveButton() {
        if (validateInput()) {
            try {
                int id = Integer.parseInt(idField.getText());
                String nom = nomField.getText();
                String role = roleField.getText();

                // 使用现有的Employe构造函数创建新员工
                new Employe(id, nom, role);

                mainController.refreshEmployeeList();
                Stage stage = (Stage) idField.getScene().getWindow();
                stage.close();
            } catch (NumberFormatException e) {
                showAlert("错误", "ID必须是数字");
            }
        } else {
            showAlert("错误", "请填写所有字段");
        }
    }

    private boolean validateInput() {
        return !idField.getText().isEmpty() &&
                !nomField.getText().isEmpty() &&
                !roleField.getText().isEmpty();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}