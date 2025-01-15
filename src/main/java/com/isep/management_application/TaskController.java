package com.isep.management_application;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TaskController {
    @FXML
    private TableView<?> taskTable;
    @FXML
    private TextField taskNameField;
    @FXML
    private TextField taskDescriptionField;

    @FXML
    private void handleAddTask() {
        // 添加任务的逻辑
    }

    @FXML
    private void handleEditTask() {
        // 编辑任务的逻辑
    }

    @FXML
    private void handleDeleteTask() {
        // 删除任务的逻辑
    }
}
