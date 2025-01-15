package com.isep.management_application;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.function.Consumer;

public class Sidebar {
    private VBox sidebar;
    private Consumer<Parent> navigationHandler;

    public Sidebar() {
        sidebar = new VBox();
        Button employeButton = createButton("Employe");
        Button projetButton = createButton("Projet");
        Button calendarButton = createButton("Calendar");
        Button reportButton = createButton("Report");

        employeButton.setOnAction(e -> navigateTo("/com/isep/management_application/main.fxml"));
        projetButton.setOnAction(e -> navigateTo("/com/isep/management_application/project.fxml"));
        calendarButton.setOnAction(e -> navigateTo("/com/isep/management_application/calendar.fxml"));
        reportButton.setOnAction(e -> navigateTo("/com/isep/management_application/report.fxml"));

        sidebar.getChildren().addAll(employeButton, projetButton, calendarButton, reportButton);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMinWidth(150);
        button.setMinHeight(50); // 设置按钮的最小高度
        return button;
    }

    public VBox getSidebar() {
        return sidebar;
    }

    public void setNavigationHandler(Consumer<Parent> handler) {
        this.navigationHandler = handler;
    }

    private void navigateTo(String fxmlFile) {
        if (navigationHandler != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                navigationHandler.accept(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}