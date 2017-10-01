package edu.umuc.yourbudget.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Christian on 9/22/17.
 */
public class WelcomeController {

    @FXML
    private void showLoginScene(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML private void showCreateUserScene(ActionEvent event) throws IOException {
        Parent createUserParent = FXMLLoader.load(getClass().getResource("/fxml/create_user.fxml"));
        Scene createUserScene = new Scene(createUserParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(createUserScene);
        appStage.show();
    }

}
