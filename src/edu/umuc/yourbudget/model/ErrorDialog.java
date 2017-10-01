package edu.umuc.yourbudget.model;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Christian on 9/30/17.
 */
public class ErrorDialog {

    public static void display(String errorMessage) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Oops");
        window.setWidth(350);
        window.setHeight(250);

        Label errorLabel = new Label(errorMessage);
        errorLabel.setTextAlignment(TextAlignment.CENTER);
        errorLabel.setWrapText(true);
        errorLabel.setMinHeight(150);

        Button okButton = new Button("OK");

        okButton.setOnAction(event -> {
            window.close();
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(errorLabel, okButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

    }

}
