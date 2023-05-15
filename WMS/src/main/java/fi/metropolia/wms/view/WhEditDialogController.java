package fi.metropolia.wms.view;

import java.net.URL;
import java.util.ResourceBundle;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Rack;
import fi.metropolia.wms.model.Section;
import fi.metropolia.wms.model.Warehouse;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class WhEditDialogController implements Initializable { 

	// Details GridPane
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	// Warehouse labels
	private Label warehouseIdCaption;
	private Label warehouseIdValue;
	private Label warehouseNameCaption;
	private TextField warehouseNameTextField;

	// Section labels
	private Label sectionIdCaption;
	private Label sectionIdValue;
	private Label sectionNameCaption;
	private TextField sectionNameTextField;
	private Label sectionWarehouseCaption;
	private int selectedSectionWarehouseId;
	private ChoiceBox<Warehouse> sectionWarehouseChoiceBox;

	// Rack labels
	private Label rackIdCaption;
	private Label rackIdValue;
	private Label rackSectionCaption;
	private Label rackWarehouseCaption;
	private Label rackLevelsCaption;
	private Label rackColumnsCaption;
	private Label rackLevelsValue;
	private Label rackColumnsValue;
	private ChoiceBox<Section> rackSectionChoiceBox;
	private ChoiceBox<Warehouse> rackWarehouseChoiceBox;
	private int selectedRackSectionId;
	private int selectedRackWarehouseId;
	private TextField rackLevelsTextField;
	private TextField rackColumnsTextField;

	private Stage dialogStage;

	// Referencies to the objects
	private Warehouse warehouse;
	private Section section;
	private Rack rack;
	private boolean okClicked = false;

	@SuppressWarnings("unused")
	private App app;

	private ResourceBundle bundle;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */

	// Initializing resources first
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		initialize();
	}

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

	// -- INITIALIZE ELEMENTS ------------------------------------------ //
	/**
	 * Method initializes warehouse detail edit view
	 * 
	 * @return
	 */
	@FXML
	public void initEditWarehouse() {

		gridPane.getChildren().clear();

		// Initialize warehouse details elements
		warehouseIdCaption = new Label(bundle.getString("warehouseIdColumn"));
		warehouseIdValue = new Label();
		warehouseNameCaption = new Label(bundle.getString("warehouseNameColumn"));
		warehouseNameTextField = new TextField();

		gridPane.add(warehouseIdCaption, 0, 0, 1, 1);
		gridPane.add(warehouseNameCaption, 0, 1, 1, 1);
		gridPane.add(warehouseIdValue, 1, 0, 1, 1);
		gridPane.add(warehouseNameTextField, 1, 1, 1, 1);

		// Warehouse dialog "Ok"-button
		okButton.setText(bundle.getString("okBtn"));
		okButton.setOnAction(event -> handleWarehouseOk());
		// Warehouse dialog "Cancel"-button
		cancelButton.setText(bundle.getString("cancelBtn"));
		cancelButton.setOnAction(event -> handleCancel());

	}

	/**
	 * Method initializes warehouse sections detail edit view
	 * 
	 * @return
	 */

	@FXML
	public void initEditSection() {

		Warehouse wh = new Warehouse();
		ObservableList<Warehouse> warehouseList = wh.getWarehouseObservableListFromDB();

		gridPane.getChildren().clear();

		// Initialize warehouse section details elements
		sectionIdCaption = new Label(bundle.getString("sectionIdColumn"));
		sectionIdValue = new Label();
		sectionNameCaption = new Label(bundle.getString("sectionNameColumn"));
		sectionNameTextField = new TextField();
		sectionWarehouseCaption = new Label(bundle.getString("warehouseColumn"));
		sectionWarehouseChoiceBox = new ChoiceBox<Warehouse>();

		// Initializing warehose choice box for the sections
		sectionWarehouseChoiceBox.setItems(warehouseList);
		sectionWarehouseChoiceBox.setOnAction((event) -> {
			Warehouse selectedItem = (Warehouse) sectionWarehouseChoiceBox.getSelectionModel().getSelectedItem();
			selectedSectionWarehouseId = selectedItem.getWarehouseId();

		});

		gridPane.add(sectionIdCaption, 0, 0, 1, 1);
		gridPane.add(sectionNameCaption, 0, 1, 1, 1);
		gridPane.add(sectionWarehouseCaption, 0, 2, 1, 1);
		gridPane.add(sectionIdValue, 1, 0, 1, 1);
		gridPane.add(sectionNameTextField, 1, 1, 1, 1);
		gridPane.add(sectionWarehouseChoiceBox, 1, 2, 1, 1);

		// Section dialog "Ok"-button
		okButton.setText(bundle.getString("okBtn"));
		okButton.setOnAction(event -> handleSectionOk());
		// Section dialog "Cancel"-button
		cancelButton.setText(bundle.getString("cancelBtn"));
		cancelButton.setOnAction(event -> handleCancel());

	}

	/**
	 * Method initializes warehouse sections detail edit view
	 * 
	 * @return
	 */

	/**
	 * 
	 */
	@FXML
	public void initEditRack() {

		Warehouse w = new Warehouse();
		Section s = new Section();

		ObservableList<Section> sectionList = selectedRackWarehouseId == 0 ? s.getSectionObservableListFromDB()
				: s.getSectionObservableListFromDBByWarehouseId(selectedRackWarehouseId);
		ObservableList<Warehouse> warehouseList = w.getWarehouseObservableListFromDB();

		gridPane.getChildren().clear();

		// Initialize warehouse section details elements
		rackIdCaption = new Label(bundle.getString("rackIdColumn"));
		rackIdValue = new Label();
		rackSectionCaption = new Label(bundle.getString("rackSectionTxt"));
		rackSectionChoiceBox = new ChoiceBox<Section>();
		rackWarehouseCaption = new Label(bundle.getString("rackWarehouseTxt"));
		rackWarehouseChoiceBox = new ChoiceBox<Warehouse>();
		rackWarehouseChoiceBox.setOnAction((event) -> {
			selectedRackWarehouseId = rackWarehouseChoiceBox.getSelectionModel().getSelectedItem().getWarehouseId();
			rackSectionChoiceBox.getSelectionModel().clearSelection();
			rackSectionChoiceBox.setItems(s.getSectionObservableListFromDBByWarehouseId(selectedRackWarehouseId));
			rackSectionChoiceBox.getSelectionModel().selectFirst();
			selectedRackSectionId = rackSectionChoiceBox.getSelectionModel().getSelectedItem().getSectionId();
		});
		rackSectionChoiceBox.setOnAction((event) -> {
			// selectedRackSectionId =
			// rackSectionChoiceBox.getSelectionModel().getSelectedItem().getSectionId();
		});

		rackLevelsCaption = new Label(bundle.getString("amountOfRackLevelsLabel"));
		rackLevelsTextField = new TextField();
		rackLevelsValue = new Label();

		rackColumnsCaption = new Label(bundle.getString("amountOfRackRowsLabel"));
		rackColumnsTextField = new TextField();
		rackColumnsValue = new Label();

		// Initializing choice boxes for the sections
		rackWarehouseChoiceBox.setItems(warehouseList);
		rackSectionChoiceBox.setItems(sectionList);

		gridPane.add(rackIdCaption, 0, 0, 1, 1);
		gridPane.add(rackWarehouseCaption, 0, 1, 1, 1);
		gridPane.add(rackSectionCaption, 0, 2, 1, 1);
		gridPane.add(rackLevelsCaption, 0, 3, 1, 1);
		gridPane.add(rackColumnsCaption, 0, 4, 1, 1);

		gridPane.add(rackIdValue, 1, 0, 1, 1);
		gridPane.add(rackWarehouseChoiceBox, 1, 1, 1, 1);
		gridPane.add(rackSectionChoiceBox, 1, 2, 1, 1);
//		if(rackLevelsValue.getText().equals("")) {
//
//		}else {
//
//		}

		// Section dialog "Ok"-button
		okButton.setText(bundle.getString("okBtn"));
		okButton.setOnAction(event -> handleRackOk());
		// Section dialog "Cancel"-button
		cancelButton.setText(bundle.getString("cancelBtn"));
		cancelButton.setOnAction(event -> handleCancel());

	}

	/**
	 * Sets the warehouse to be edited in the dialog.
	 * 
	 * @param warehouse
	 */
	public void setWarehouse(Warehouse warehouse) {

		this.warehouse = warehouse;
		initEditWarehouse();
		
		warehouseNameTextField.setText(warehouse.getWarehouseName());
		String id = Integer.toString(warehouse.getWarehouseId());
		if(id.equals("-1")) {
			warehouseIdValue.setText(bundle.getString("notCreatedYetTxt"));
		} else {
			warehouseIdValue.setText(id);
		}
		
	}

	/**
	 * Sets the section to be edited in the dialog.
	 * 
	 * @param section
	 */
	public void setSection(Section section) {

		this.section = section;

		initEditSection();
		if (section.getSectionId() != -1) {

			sectionWarehouseChoiceBox.getSelectionModel()
					.select(new Warehouse().getWarehouseFromDBById(section.getSectionWarehouseId()));

		} else {
			selectedSectionWarehouseId = section.getSectionWarehouseId();

		}

		sectionNameTextField.setText(section.getSectionType());
		String id = Integer.toString(section.getSectionId());
		if(id.equals("-1")) {
			sectionIdValue.setText(bundle.getString("notCreatedYetTxt"));
		} else {
			sectionIdValue.setText(id);
		}

	}

	/**
	 * Sets the rack to be edited in the dialog.
	 * 
	 * @param rack
	 */
	public void setRack(Rack rack) {

		this.rack = rack;

		initEditRack();
		if (rack.getRackId() != -1) {

			rackSectionChoiceBox.getSelectionModel().select(new Section().getSectionFromDBById(rack.getSectionId()));
			rackWarehouseChoiceBox.getSelectionModel()
					.select(new Warehouse().getWarehouseFromDBById(rack.getWarehouseId()));
			gridPane.add(rackLevelsValue, 1, 3, 1, 1);
			gridPane.add(rackColumnsValue, 1, 4, 1, 1);

		} else {
			gridPane.add(rackLevelsTextField, 1, 3, 1, 1);
			gridPane.add(rackColumnsTextField, 1, 4, 1, 1);
			selectedRackSectionId = rack.getSectionId();
			selectedRackWarehouseId = rack.getWarehouseId();
		}
		selectedRackWarehouseId = rack.getWarehouseId();
		selectedRackSectionId = rack.getSectionId();
		String id = Integer.toString(rack.getRackId());
		if(id.equals("-1")) {
			rackIdValue.setText(bundle.getString("notCreatedYetTxt"));
		} else {
			rackIdValue.setText(id);
		}
		
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
	 * Called when the user clicks ok on warehouse dialog.
	 */
	@FXML
	private void handleWarehouseOk() {
		if (isWarehouseInputValid()) {

			if (warehouseIdValue.getText().equals(bundle.getString("notCreatedYetTxt"))) {
				warehouse.setWarehouseId(warehouse.addWarehouseToDB(warehouseNameTextField.getText()));
			} else {
				warehouse.updateWarehouseToDB(warehouse.getWarehouseId(), warehouseNameTextField.getText());
				warehouse.setWarehouseId(Integer.parseInt(warehouseIdValue.getText()));
			}

			warehouse.setWarehouseName(warehouseNameTextField.getText());

			okClicked = true;
			dialogStage.close();

		}
	}

	/**
	 * Called when the user clicks ok on section dialog.
	 */
	@FXML
	private void handleSectionOk() {
		if (isSectionInputValid()) {
			Warehouse w = new Warehouse();

			if (sectionIdValue.getText().equals(bundle.getString("notCreatedYetTxt"))) {

				section.setSectionType(sectionNameTextField.getText());
				section.setSectionId(
						section.addSectionToDB(sectionNameTextField.getText(), selectedSectionWarehouseId));
				section.setSectionWarehouseId(selectedSectionWarehouseId);

				section.setSectionWarehouseName(w.getWarehouseNameFromDBById(selectedSectionWarehouseId));

			} else {
				section.updateSectionToDB(section.getSectionId(), sectionNameTextField.getText(),
						selectedSectionWarehouseId);
				section.setSectionId(Integer.parseInt(sectionIdValue.getText()));
				section.setSectionType(sectionNameTextField.getText());
				section.setSectionWarehouseId(selectedSectionWarehouseId);
				section.setSectionWarehouseName(w.getWarehouseNameFromDBById(selectedSectionWarehouseId));
			}

			okClicked = true;
			dialogStage.close();

		}
	}

	/**
	 * Called when the user clicks ok on section dialog.
	 */
	@FXML
	private void handleRackOk() {
		if (isRackInputValid()) {

			// Warehouse w = new Warehouse();
			// Section s = new Section();
			rack.setWarehouseId(selectedRackWarehouseId);
			rack.setSectionId(selectedRackSectionId);
			// rack.setWarehouseName(w.getWarehouseNameFromDBById(selectedRackWarehouseId));
			// rack.setSectionName(s.getSectionNameFromDBById(selectedRackSectionId));
			if (rackIdValue.getText().equals(bundle.getString("notCreatedYetTxt"))) {

				rack.setRackId(rack.addRackToDB(selectedRackWarehouseId, selectedRackSectionId));
				rack.addRackBinsToDB(rack.getRackId(), Integer.valueOf(rackLevelsTextField.getText()),
						Integer.valueOf(rackColumnsTextField.getText()));
				// rack.addRackLevelsToDB(rack.getRackId(),
				// Integer.valueOf(rackLevelsTextField.getText()));
				// rack.addRackColumnsToDB(rack.getRackId(),
				// Integer.valueOf(rackColumnsTextField.getText()));
			} else {
				rack.updateRackToDB(rack.getRackId(), selectedRackWarehouseId, selectedRackSectionId);
				rack.setRackId(Integer.parseInt(rackIdValue.getText()));
			}

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

	private boolean isWarehouseInputValid() {
		String errorMessage = "";

		if (warehouseNameTextField.getText() == null || warehouseNameTextField.getText().length() == 0) {
			errorMessage += bundle.getString("warehouseNameEmptyErr") + "\n";
		}
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
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */

	private boolean isSectionInputValid() {
		String errorMessage = "";

		if (sectionNameTextField.getText() == null || sectionNameTextField.getText().length() == 0) {
			errorMessage +=  bundle.getString("sectionNameEmptyErr") + "\n";
		}

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
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */

	private boolean isRackInputValid() {
//		String errorMessage = "";
//
//		if (sectionNameTextField.getText() == null || sectionNameTextField.getText().length() == 0) {
//			errorMessage += "Osaston nimi ei voi olla tyhjä!\n";
//		}
//
//		if (errorMessage.length() == 0) {
//			return true;
//		} else {
//			// Show the error message.
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.initOwner(dialogStage);
//			alert.setTitle("Virhellinen syöttö");
//			alert.setHeaderText("Ole hyvä ja korja virhelliset kenttät");
//			alert.setContentText(errorMessage);
//
//			alert.showAndWait();
//
//			return false;
//		}
		return true;
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
