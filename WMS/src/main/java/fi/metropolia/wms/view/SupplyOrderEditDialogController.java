package fi.metropolia.wms.view;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Rack;
import fi.metropolia.wms.model.Section;
import fi.metropolia.wms.model.Supplier;
import fi.metropolia.wms.model.SupplyOrder;
import fi.metropolia.wms.model.Warehouse;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Handles warehouse, section and racks edit dialog popup window.
 * 
 * @author Mihail Karvanen, Roman Prokhozhev
 *
 */
public class SupplyOrderEditDialogController {

	// Details GridPane
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	// Supply order labels
	
	//private Label supplyOrderIdCaption;
	private Label supplyOrderIdValue;
	private Label supplyOrderNumberCaption;
	private TextField supplyOrderNumberTextField;
	private Label supplyOrderContactPersonCaption;
	private TextField supplyOrderContactPersonTextField;
	private Label supplyOrderSupplierCaption;
	private ChoiceBox<Supplier> supplierChoiceBox;


	private Stage dialogStage;

	// Referencies to the objects
	private SupplyOrder supplyOrder;
	private boolean okClicked = false;

	@SuppressWarnings("unused")
	private App app;
	private ResourceBundle bundle;
	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setResources(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	// -- INITIALIZE ELEMENTS ------------------------------------------ //
	/**
	 * Method initializes supply order details edit view
	 * 
	 * @return
	 */
	@FXML
	public void initEditSupplyOrder() {

//		gridPane.getChildren().clear();

		// Initialize warehouse details elements
		supplyOrderIdValue = new Label();
		
		supplyOrderNumberCaption = new Label(bundle.getString("orderIdColumn"));
		supplyOrderNumberTextField = new TextField();
		supplyOrderContactPersonCaption = new Label(bundle.getString("ordererNameColumn"));
		supplyOrderContactPersonTextField = new TextField();
		
		
		Supplier supplier = new Supplier();
		ObservableList<Supplier> supplierList = supplier.getSupplierObservableListFromDB();
		supplyOrderSupplierCaption = new Label(bundle.getString("goodsSupplierTxt"));
		supplierChoiceBox = new ChoiceBox<Supplier>();
		supplierChoiceBox.setItems(supplierList);

		gridPane.add(supplyOrderNumberCaption, 0, 0, 1, 1);
		gridPane.add(supplyOrderContactPersonCaption, 0, 1, 1, 1);
		gridPane.add(supplyOrderSupplierCaption, 0, 2, 1, 1);
		
		gridPane.add(supplyOrderNumberTextField, 1, 0, 1, 1);
		gridPane.add(supplyOrderContactPersonTextField, 1, 1, 1, 1);
		gridPane.add(supplierChoiceBox, 1, 2, 1, 1);

		// Warehouse dialog "Ok"-button
		okButton.setText(bundle.getString("okBtn"));
		okButton.setOnAction(event -> handleSupplyOrderOk());
		// Warehouse dialog "Cancel"-button
		cancelButton.setText(bundle.getString("cancelBtn"));
		cancelButton.setOnAction(event -> handleCancel());

	}



	/**
	 * Sets the warehouse to be edited in the dialog.
	 * 
	 * @param warehouse
	 */
	public void setSupplyOrder(SupplyOrder supplyOrder) {

		this.supplyOrder = supplyOrder;
		initEditSupplyOrder();

		supplyOrderNumberTextField.setText(supplyOrder.getSupplyOrderNumber());
		supplyOrderIdValue.setText(Integer.toString(supplyOrder.getSupplyOrderId()));

	}



	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok on supply order dialog.
	 * 
	 */
	@FXML
	private void handleSupplyOrderOk() {
		if (isSupplyOrderInputValid()) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			if (supplyOrderIdValue.getText().equals("-1")) {
				supplyOrder.setSupplyOrderId(supplyOrder.addSupplyOrderToDB(supplierChoiceBox.getSelectionModel().getSelectedItem().getSupplierId(), 
						supplyOrderNumberTextField.getText(), 0.00, timestamp, supplyOrderContactPersonTextField.getText()));
			} else {
				//warehouse.updateWarehouseToDB(warehouse.getWarehouseId(), warehouseNameTextField.getText());
				//warehouse.setWarehouseId(Integer.parseInt(warehouseIdValue.getText()));
			}

			supplyOrder.setSupplyOrderNumber(supplyOrderNumberTextField.getText());
			supplyOrder.setDateCreate(timestamp.toString());
			supplyOrder.setDateModify(timestamp.toString());
			supplyOrder.setContactPerson(supplyOrderContactPersonTextField.getText());
			supplyOrder.setSupplierId(supplierChoiceBox.getSelectionModel().getSelectedItem().getSupplierId());
			supplyOrder.setSupplierName(new Supplier().getSupplierFromDBById(supplierChoiceBox.getSelectionModel().getSelectedItem().getSupplierId()).getSupplierName());

			okClicked = true;
			dialogStage.close();

		}
	}

	

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields for the warehouse.
	 * 
	 * @return true if the input is valid
	 */

	private boolean isSupplyOrderInputValid() {
		String errorMessage = "";

//		if (warehouseNameTextField.getText() == null || warehouseNameTextField.getText().length() == 0) {
//			errorMessage += "Varaston nimi ei voi olla tyhjä!\n";
//		}
//        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
//            errorMessage += "No valid last name!\n"; 
//        }
//        if (streetField.getText() == null || streetField.getText().length() == 0) {
//            errorMessage += "No valid street!\n"; 
//        }
//
//        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
//            errorMessage += "No valid postal code!\n"; 
//        } else {
//            // try to parse the postal code into an int.
//            try {
//                Integer.parseInt(postalCodeField.getText());
//            } catch (NumberFormatException e) {
//                errorMessage += "No valid postal code (must be an integer)!\n"; 
//            }
//        }
//
//        if (cityField.getText() == null || cityField.getText().length() == 0) {
//            errorMessage += "No valid city!\n"; 
//        }
//
//        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
//            errorMessage += "No valid birthday!\n";
//        } else {
//            if (!DateUtil.validDate(birthdayField.getText())) {
//                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
//            }
//        }

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle(bundle.getString("incorrectInputErr"));
			alert.setHeaderText(bundle.getString("fixInputTxt"));
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setMainApp(App app) {
		this.app = app;

	}
}
