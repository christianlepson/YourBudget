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

    //TODO(2) Complete the isNameValid method
    // Method should ensure that the name contains no special characters, numbers, or spaces.
    // If the name is valid (contains no special characters, etc.) return true, otherwise
    // return false
    private boolean isNameValid(String name)
    {

        if(name.matches("[a-zA-Z]+$"))
            return true;
        else
        return false;
    }

    //TODO(3) Complete the isUsernameValid method
    // Method should ensure that the username contains only letters and numbers
    // and is 15 characters or less. If the username is not valid, return false
    private boolean isUsernameValid(String username)
    {
        if(username.matches("[a-zA-Z]+$") && username.length() <= 15)
            return true;

        else
            return false;

    }

}
