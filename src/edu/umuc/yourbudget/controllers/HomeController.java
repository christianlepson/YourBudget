package edu.umuc.yourbudget.controllers;

import edu.umuc.yourbudget.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by Christian on 9/30/17.
 */
public class HomeController {
    @FXML private Label loginStatusLabel;

    private User user;

    public void initialize(User user) {
        this.user = user;
        loginStatusLabel.setText("Logged in as " + user.getFirstName() + ".");
    }

    @FXML
    private void signOut(ActionEvent event) {
        try {
            Parent welcomeParent = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
            Scene welcomeScene = new Scene(welcomeParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(welcomeScene);
            appStage.show();
        } catch (Exception e) {
            System.out.println("Unable to open Welcome scene from Home.");
            e.printStackTrace();
        }

    }

}
