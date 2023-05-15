package fi.metropolia.wms.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Class sets button to CheckCollectedListWindow.fxml file
 * @author Roman Prokhozhev
 *
 */
public class OrderCollectedWindowController {

    @FXML
    private Button okButton;

    @FXML
    void okButtonAction(ActionEvent event) {
    	 Stage stage = (Stage) okButton.getScene().getWindow();
         stage.close();
    }

}
