package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.database.UserCreator;
import edu.umuc.yourbudget.database.UserRetriever;
import edu.umuc.yourbudget.model.ErrorDialog;
import edu.umuc.yourbudget.model.InputValidator;
import edu.umuc.yourbudget.model.User;
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
public class CreateUserController {
    @FXML private TextField firstName;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField confirm;

    private User mUser;

    @FXML
    private void createAccount(ActionEvent event) {
        String name = firstName.getText();
        String user = username.getText();
        String pass = password.getText();
        String passConfirm = confirm.getText();


        if (InputValidator.isFirstNameValid(name)) {

            if (InputValidator.isUsernameValid(user)) {

                if (InputValidator.isPasswordValid(pass)) {

                    if (pass.equals(passConfirm)) {

                        UserRetriever retriever = new UserRetriever();

                        // If username doesn't already exist, add user
                        if (retriever.retrieveByUsername(user) == null) {
                            UserCreator creator = new UserCreator();
                            creator.createUser(name, user, pass);
                            mUser = retriever.retrieveByUsername(user);
                            showInitialSetupScene(event);
                        } else {
                            ErrorDialog.display("That username is unavailable." +
                                    "\nPlease choose a different username.");
                        }


                    } else {
                        ErrorDialog.display(
                                "The passwords you entered did not match.\nPlease try again."
                        );
                    }

                } else {
                    ErrorDialog.display("The password you entered was invalid." +
                            "\nPasswords must be between 10 to 120 characters in length.");
                }

            } else {
                ErrorDialog.display("The username you entered was invalid." +
                        "\nUsernames must be between 7 to 30 characters " +
                        "and cannot contain spaces or special characters.");
            }

        } else {
            ErrorDialog.display("The first name you entered was invalid." +
                    "\nFirst names cannot contain numbers, spaces, or special characters.");
        }

    }

    @FXML
    private void showWelcomeScene(ActionEvent event) {
        Parent welcomeParent = null;
        try {
            welcomeParent = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
            Scene welcomeScene = new Scene(welcomeParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(welcomeScene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Unable to start Welcome Scene from Create Account.");
            e.printStackTrace();
        }
    }

    private void showInitialSetupScene(ActionEvent event) {
        Parent setupParent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/initial_setup.fxml"));
            setupParent = loader.load();
            Scene setupScene = new Scene(setupParent);

            InitialSetupController controller = loader.getController();
            controller.initialize(mUser);

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(setupScene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Unable to start Initial Setup scene from Create Account.");
            e.printStackTrace();
        }
    }

}
