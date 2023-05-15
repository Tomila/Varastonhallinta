
package fi.metropolia.wms.view;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.stage.Stage;
/**
 * Class sets button to CheckCollectedListWindow.fxml file
 * @author Roman Prokhozhev
 */
public class CheckCollectedListWindowController {
	


    @FXML

    private Button okButton;

    

    @FXML

    public void okButtonAction(ActionEvent event) {

        Stage stage = (Stage) okButton.getScene().getWindow();

        stage.close();

    }

}


