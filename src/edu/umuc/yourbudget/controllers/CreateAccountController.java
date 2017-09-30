package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.model.UserCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Christian on 9/22/17.
 */
public class CreateAccountController {
    @FXML private TextField firstName;
    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML
    private void createAccount(ActionEvent event) {
        // Code for Phase 2
//        String name = firstName.getText();
//        String user = username.getText();
//        String pass = password.getText();
//        UserCreator creator = new UserCreator();
//        creator.createUser(name, user, pass);
    }

    @FXML
    private void showWelcomeScene(ActionEvent event) throws IOException {
        Parent welcomeParent = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
        Scene welcomeScene = new Scene(welcomeParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(welcomeScene);
        appStage.show();
    }

}
