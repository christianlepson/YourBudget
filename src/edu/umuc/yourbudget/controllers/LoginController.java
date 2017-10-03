package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.UserRetriever;
import edu.umuc.yourbudget.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Christian on 9/22/17.
 */
public class LoginController {
    private User user;

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label error;

    @FXML
    private void login(ActionEvent event) {
        UserRetriever retriever = new UserRetriever();
        String usernameText = username.getText();
        String passwordText = password.getText();
        User result = retriever.retrieveByCredentials(usernameText, passwordText);
        if (result != null) {
            user = result;
            showHomeScene(event);
        } else {
            error.setOpacity(1.0);
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

    private void showHomeScene(ActionEvent event) {
        Parent setupParent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/home.fxml"));
            setupParent = loader.load();
            Scene homeScene = new Scene(setupParent);

            HomeController controller = loader.getController();
            controller.initialize(user);

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(homeScene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Unable to start Home scene from Login.");
            e.printStackTrace();
        }
    }

}