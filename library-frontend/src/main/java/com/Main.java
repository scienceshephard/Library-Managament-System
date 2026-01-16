package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/book-view.fxml")
        );
        stage.setScene(new Scene(loader.load(), 600, 500));
        stage.setTitle("Library Management System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}