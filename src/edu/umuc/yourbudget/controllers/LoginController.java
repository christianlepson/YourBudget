package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.model.LoginAuthenticator;
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
public class LoginController {
    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML
    private void login(ActionEvent event) {
        LoginAuthenticator authenticator = new LoginAuthenticator();
        String usernameText = username.getText();
        String passwordText = password.getText();
        boolean isValid = authenticator.isLoginValid(usernameText, passwordText);
        if (isValid) {
            System.out.println("Login is valid!");
        } else {
            System.out.println("Login is invalid!");
        }
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