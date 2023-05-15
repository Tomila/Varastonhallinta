package fi.metropolia.wms.view;

import java.net.URL;
import java.util.ResourceBundle;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Rack;
import fi.metropolia.wms.model.Section;
import fi.metropolia.wms.model.Warehouse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Handles warehouse layout management operations interface. Warehouse,
 * warehouse section, warehouse racking modification.
 * 
 * @author Mihail Karvanen, Roman Prokhozhev
 *
 */

public class WarehouseEditorController implements Initializable {

	@FXML
	private Label title;

	// Warehouse TableView
	@FXML
	private TableView<Warehouse> warehouseTable;
	@FXML
	private TableColumn<Warehouse, Integer> warehouseIdColumn;
	@FXML
	private TableColumn<Warehouse, String> warehouseNameColumn;

	// Section TableView
	@FXML
	private TableView<Section> sectionTable;
	@FXML
	private TableColumn<Section, Integer> sectionIdColumn;
	@FXML
	private TableColumn<Section, Integer> sectionWarehouseIdColumn;
	@FXML
	private TableColumn<Section, String> sectionNameColumn;
	@FXML
	private TableColumn<Section, String> sectionWarehouseNameColumn;

	// Rack TableView
	@FXML
	private TableView<Rack> rackTable;
	@FXML
	private TableColumn<Rack, Integer> rackIdColumn;
	@FXML
	private TableColumn<Rack, Integer> rackWarehouseIdColumn;
	@FXML
	private TableColumn<Rack, Integer> rackSectionIdColumn;

	// Details GridPane
	@FXML
	private GridPane gridPane;
	@FXML
	private HBox buttonBar;
	@FXML
	private Button addNewButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;

	// Warehouse labels
	private Label warehouseIdCaption;
	private Label warehouseNameCaption;
	private Label warehouseIdValue;
	private Label warehouseNameValue;

	// Section labels
	private Label sectionIdCaption;
	private Label sectionIdValue;
	private Label sectionNameCaption;
	private Label sectionNameValue;
	private Label sectionWarehouseIdCaption;
	private Label sectionWarehouseIdValue;
	private Label sectionWarehouseNameCaption;
	private Label sectionWarehouseNameValue;

	// Rack labels
	private Label rackIdCaption;
	private Label rackIdValue;
	private Label rackWarehouseCaption;
	private Label rackWarehouseValue;
	private Label rackSectionCaption;
	private Label rackSectionValue;

	// References to the objects
	private Warehouse warehouse;
	private Section section;
	private Rack rack;

	private App app;

	private ResourceBundle bundle;

	/**
	 * The constructor The constructor is called before the initialize() method.
	 */

	public WarehouseEditorController() {
	}

	// ---- INITIALIZE ELEMENTS
	// ------------------------------------------------------------------- //

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */

	// First initialize resources
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		// Then fxml elements
		initialize();
	}

	@FXML
	private void initialize() {
		// Initialize the warehouse table.
		warehouseIdColumn.setCellValueFactory(cellData -> cellData.getValue().getWarehouseIdProperty().asObject());
		warehouseNameColumn.setCellValueFactory(cellData -> cellData.getValue().getWarehouseNameProperty());
		// Listen for selection changes and show the person details when changed.
		warehouseTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showWarehouseDetails(newValue));

		// Initialize warehouse section table
		sectionIdColumn.setCellValueFactory(cellData -> cellData.getValue().getSectionIdProperty().asObject());
		sectionNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSectionTypeProperty());
		sectionWarehouseIdColumn
				.setCellValueFactory(cellData -> cellData.getValue().getSectionWarehouseIdProperty().asObject());
		sectionWarehouseNameColumn
				.setCellValueFactory(cellData -> cellData.getValue().getSectionWarehouseNameProperty());
		// Listen for selection changes and show the section details when changed.
		sectionTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showWarehouseSectionsDetails(newValue));

		// Initialize the rack table.
		rackIdColumn.setCellValueFactory(cellData -> cellData.getValue().getRackIdProperty().asObject());
		rackWarehouseIdColumn.setCellValueFactory(cellData -> cellData.getValue().getWarehouseIdProperty().asObject());
		rackSectionIdColumn.setCellValueFactory(cellData -> cellData.getValue().getSectionIdProperty().asObject());
		// Listen for selection changes and show the rack details when changed.
		rackTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showRackDetails(newValue));

		// Clear objects details.
		gridPane.getChildren().clear();

//	        warehouseTable.getSelectionModel().select(0);
//	        sectionTable.getSelectionModel().select(0);

	}

	/**
	 * Method initializes warehouse detail edit view
	 * 
	 * @return GridPane with warehouse details
	 */
	@FXML
	public void initEditWarehouse() {
		if (bundle != null) {
			gridPane.getChildren().clear();
			buttonBar.getChildren().clear();
			warehouseTable.getSelectionModel().clearSelection();
			title.setText("");

			// Initialize warehouse details elements
			warehouseIdCaption = new Label();
			warehouseIdValue = new Label();
			warehouseNameCaption = new Label();
			warehouseNameValue = new Label();

			gridPane.add(warehouseIdCaption, 0, 0, 1, 1);
			gridPane.add(warehouseNameCaption, 0, 1, 1, 1);
			gridPane.add(warehouseIdValue, 1, 0, 1, 1);
			gridPane.add(warehouseNameValue, 1, 1, 1, 1);

			// -- Warehouse toolbar initialization -------------------- //

			// Add new warehouse button
			addNewButton = new Button(bundle.getString("newBtn"));
			addNewButton.getStyleClass().add("addNewWarehouseButton");
			addNewButton.setOnAction(event -> handleNewWarehouse());

			// Edit warehouse button
			editButton = new Button(bundle.getString("editBtn"));
			editButton.getStyleClass().add("editWarehouseButton");
			editButton.setOnAction(event -> handleEditWarehouse());

			// Delete warehouse button
			deleteButton = new Button(bundle.getString("deleteBtn"));
			deleteButton.getStyleClass().add("deleteWarehouseButton");
			deleteButton.setOnAction(event -> handleDeleteWarehouse());

			buttonBar.getChildren().addAll(addNewButton, editButton, deleteButton);
			editButton.setDisable(true);
			deleteButton.setDisable(true);
		}
	}

	/**
	 * Method initializes warehouse sections detail edit view
	 * 
	 */
	@FXML
	public void initEditSections() {
		gridPane.getChildren().clear();
		buttonBar.getChildren().clear();
		sectionTable.getSelectionModel().clearSelection();
		title.setText("");

		sectionIdCaption = new Label();
		sectionIdValue = new Label();
		sectionNameCaption = new Label();
		sectionNameValue = new Label();
		sectionWarehouseIdCaption = new Label();
		sectionWarehouseIdValue = new Label();
		sectionWarehouseNameCaption = new Label();
		sectionWarehouseNameValue = new Label();
		gridPane.add(sectionIdCaption, 0, 0, 1, 1);
		gridPane.add(sectionNameCaption, 0, 1, 1, 1);
		gridPane.add(sectionWarehouseIdCaption, 0, 2, 1, 1);
		gridPane.add(sectionWarehouseNameCaption, 0, 3, 1, 1);
		gridPane.add(sectionIdValue, 1, 0, 1, 1);
		gridPane.add(sectionNameValue, 1, 1, 1, 1);
		gridPane.add(sectionWarehouseIdValue, 1, 2, 1, 1);
		gridPane.add(sectionWarehouseNameValue, 1, 3, 1, 1);

		// -- Section toolbar initialization -------------------- //

		// Add new section button
		addNewButton = new Button(bundle.getString("newBtn"));
		addNewButton.getStyleClass().add("addNewWarehouseButton");
		addNewButton.setOnAction(event -> handleNewSection());

		// Edit section button
		editButton = new Button(bundle.getString("editBtn"));
		editButton.getStyleClass().add("editWarehouseButton");
		editButton.setOnAction(event -> handleEditSection());

		// Delete section button
		deleteButton = new Button(bundle.getString("deleteBtn"));
		deleteButton.getStyleClass().add("deleteWarehouseButton");
		deleteButton.setOnAction(event -> handleDeleteSection());

		buttonBar.getChildren().addAll(addNewButton, editButton, deleteButton);
		editButton.setDisable(true);
		deleteButton.setDisable(true);

	}

	/**
	 * Method initializes warehouse detail edit view
	 * 
	 * @return GridPane with warehouse details
	 */
	@FXML
	public void initEditRack() {
		gridPane.getChildren().clear();
		buttonBar.getChildren().clear();
		rackTable.getSelectionModel().clearSelection();
		title.setText("");

		// Initialize warehouse details elements
		rackIdCaption = new Label();
		rackIdValue = new Label();
		rackWarehouseCaption = new Label();
		rackWarehouseValue = new Label();
		rackSectionCaption = new Label();
		rackSectionValue = new Label();

		gridPane.add(rackIdCaption, 0, 0, 1, 1);
		gridPane.add(rackSectionCaption, 0, 1, 1, 1);
		gridPane.add(rackWarehouseCaption, 0, 2, 1, 1);
		gridPane.add(rackIdValue, 1, 0, 1, 1);
		gridPane.add(rackSectionValue, 1, 1, 1, 1);
		gridPane.add(rackWarehouseValue, 1, 2, 1, 1);

		// -- Rack toolbar initialization -------------------- //

		// Add new rack button
		addNewButton = new Button(bundle.getString("newBtn"));
		addNewButton.getStyleClass().add("addNewWarehouseButton");
		addNewButton.setOnAction(event -> handleNewRack());

		// Edit rack button
		editButton = new Button(bundle.getString("editBtn"));
		editButton.getStyleClass().add("editWarehouseButton");
		editButton.setOnAction(event -> handleEditRack());

		// Delete rack button
		deleteButton = new Button(bundle.getString("deleteBtn"));
		deleteButton.getStyleClass().add("deleteWarehouseButton");
		deleteButton.setOnAction(event -> handleDeleteRack());

		buttonBar.getChildren().addAll(addNewButton, editButton, deleteButton);
		editButton.setDisable(true);
		deleteButton.setDisable(true);

	}

	// ---- SHOW ELEMENTS
	// ------------------------------------------------------------------- //

	/**
	 * Fills all text fields to show details about the warehouse. If the specified
	 * warehouse is null, all text fields are cleared.
	 * 
	 * @param
	 * 
	 */
	private void showWarehouseDetails(Warehouse warehouse) {
		initEditWarehouse();
		warehouseTable.getSelectionModel().select(warehouseTable.getSelectionModel().getSelectedIndex());

		// Enable toolbar edit&delete buttons
		editButton.setDisable(false);
		deleteButton.setDisable(false);

		if (warehouse != null) {
			title.setText(bundle.getString("warehouseDetailsTxt"));
			warehouseIdCaption.setText(bundle.getString("warehouseIdColumn"));
			warehouseNameCaption.setText(bundle.getString("warehouseNameColumn"));
			warehouseNameValue.setText(warehouse.getWarehouseName());
			warehouseIdValue.setText(Integer.toString(warehouse.getWarehouseId()));

		} else {
			title.setText("");
			warehouseNameCaption.setText("");
			warehouseIdCaption.setText("");
			warehouseNameValue.setText("");
			warehouseIdValue.setText("");
			buttonBar.getChildren().clear();

		}
	}

	/**
	 * Fills all text fields to show details about the warehouses racks. If the
	 * specified section is null, all text fields are cleared.
	 * 
	 * @param
	 * 
	 */
	private void showWarehouseSectionsDetails(Section section) {
		// sectionTable.getSelectionModel().select(0);
		initEditSections();
		sectionTable.getSelectionModel().select(sectionTable.getSelectionModel().getSelectedIndex());
		title.setText(bundle.getString("sectionDetailsTxt"));

		// Enable toolbar edit&delete buttons
		editButton.setDisable(false);
		deleteButton.setDisable(false);

		if (section != null) {

			sectionIdCaption.setText(bundle.getString("sectionIdColumn"));
			sectionNameCaption.setText(bundle.getString("sectionNameColumn"));
			sectionWarehouseIdCaption.setText(bundle.getString("warehouseIdColumn"));
			sectionWarehouseNameCaption.setText(bundle.getString("warehouseNameColumn"));
			sectionNameValue.setText(section.getSectionType());
			sectionIdValue.setText(Integer.toString(section.getSectionId()));
			sectionWarehouseIdValue.setText(Integer.toString(section.getSectionWarehouseId()));
			sectionWarehouseNameValue
					.setText(new Warehouse().getWarehouseNameFromDBById(section.getSectionWarehouseId()));

		} else {
			sectionNameCaption.setText("");
			sectionIdCaption.setText("");
			sectionNameValue.setText("");
			sectionIdValue.setText("");
			buttonBar.getChildren().clear();

		}
	}

	/**
	 * Fills all text fields to show details about the warehouses rack If the
	 * specified rack is null, all text fields are cleared.
	 * 
	 * @param
	 * 
	 */
	private void showRackDetails(Rack rack) {
		initEditRack();
		rackTable.getSelectionModel().select(rackTable.getSelectionModel().getSelectedIndex());
		title.setText(bundle.getString("rackDetailsTxt"));

		// Enable toolbar edit&delete buttons
		editButton.setDisable(false);
		deleteButton.setDisable(false);

		if (rack != null) {

			rackIdCaption.setText(bundle.getString("rackIdColumn"));
			rackSectionCaption.setText(bundle.getString("rackSectionTxt"));
			rackWarehouseCaption.setText("rackWarehouseTxt");

			rackIdValue.setText(Integer.toString(rack.getRackId()));
			rackWarehouseValue.setText(Integer.toString(rack.getWarehouseId()) + "-"
					+ new Warehouse().getWarehouseNameFromDBById(rack.getWarehouseId()));
			rackSectionValue.setText(Integer.toString(rack.getSectionId()) + "-"
					+ new Section().getSectionNameFromDBById(rack.getSectionId()));

		} else {

			rackSectionCaption.setText("");
			rackWarehouseCaption.setText("");
			rackSectionValue.setText("");
			rackWarehouseValue.setText("");
			buttonBar.getChildren().clear();
		}
	}

	// ---- EDIT ELEMENTS
	// ------------------------------------------------------------------- //

	/**
	 * Called when the user clicks on the delete rack button.
	 */
	@FXML
	private void handleDeleteWarehouse() {
		int selectedIndex = warehouseTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			warehouse = new Warehouse();
			warehouse.removeWarehouseFromDBById(warehouseTable.getSelectionModel().getSelectedItem().getWarehouseId());
			warehouseTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle(bundle.getString("noChoiceTxt"));
			alert.setHeaderText(bundle.getString("warehouseNotSelectedTxt"));
			alert.setContentText(bundle.getString("chooseWarehouseTxt"));

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the delete section button.
	 */
	@FXML
	private void handleDeleteSection() {
		int selectedIndex = sectionTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			section = new Section();
			section.removeSectionFromDBById(sectionTable.getSelectionModel().getSelectedItem().getSectionId());
			sectionTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle(bundle.getString("noChoiceTxt"));
			alert.setHeaderText(bundle.getString("warehouseNotSelectedTxt"));
			alert.setContentText(bundle.getString("chooseWarehouseTxt"));

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the delete rack button.
	 */
	@FXML
	private void handleDeleteRack() {
		int selectedIndex = rackTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			rack = new Rack();
			rack.removeRackFromDBById(rackTable.getSelectionModel().getSelectedItem().getRackId());
			rackTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle(bundle.getString("noChoiceTxt"));
			alert.setHeaderText(bundle.getString("warehouseNotSelectedTxt"));
			alert.setContentText(bundle.getString("chooseWarehouseTxt"));

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new warehouse.
	 */
	@FXML
	private void handleNewWarehouse() {
		Warehouse tempWarehouse = new Warehouse(-1, "");
		boolean okClicked = app.showWarehouseEditDialog(tempWarehouse);
		if (okClicked) {

			app.getWarehouseData().add(tempWarehouse);

			showWarehouseDetails(tempWarehouse);
		}
	}

	/**
	 * Called when the user clicks the new section button. Opens a dialog to edit
	 * details for a new section.
	 */
	@FXML
	private void handleNewSection() {
		Section tempSection = new Section(-1, "", 0, "");
		boolean okClicked = app.showWarehouseEditDialog(tempSection);
		if (okClicked) {

			app.getSectionData().add(tempSection);

			showWarehouseSectionsDetails(tempSection);
			sectionTable.setItems(app.refreshSectionData());
		}
	}

	/**
	 * Called when the user clicks the new rack button. Opens a dialog to edit
	 * details for a new rack.
	 */
	@FXML
	private void handleNewRack() {
		Rack tempRack = new Rack(-1, 0, 0, "", "");
		boolean okClicked = app.showWarehouseEditDialog(tempRack);
		if (okClicked) {

			app.getRackData().add(tempRack);

			showRackDetails(tempRack);
			rackTable.setItems(app.refreshRackData());
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected warehouse.
	 */
	@FXML
	private void handleEditWarehouse() {
		Warehouse selectedWarehouse = warehouseTable.getSelectionModel().getSelectedItem();

		if (selectedWarehouse != null) {
			boolean okClicked = app.showWarehouseEditDialog(selectedWarehouse);
			if (okClicked) {
				warehouseTable.getSelectionModel().select(0);
				showWarehouseDetails(selectedWarehouse);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle(bundle.getString("noChoiceTxt"));
			alert.setHeaderText(bundle.getString("warehouseNotSelectedTxt"));
			alert.setContentText(bundle.getString("chooseWarehouseTxt"));

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected section.
	 */
	@FXML
	private void handleEditSection() {
		Section selectedSection = sectionTable.getSelectionModel().getSelectedItem();

		if (selectedSection != null) {
			boolean okClicked = app.showWarehouseEditDialog(selectedSection);
			if (okClicked) {
				sectionTable.setItems(app.refreshSectionData());
				showWarehouseSectionsDetails(selectedSection);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle(bundle.getString("noChoiceTxt"));
			alert.setHeaderText(bundle.getString("warehouseNotSelectedTxt"));
			alert.setContentText(bundle.getString("chooseWarehouseTxt"));

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected rack.
	 */
	@FXML
	private void handleEditRack() {
		Rack selectedRack = rackTable.getSelectionModel().getSelectedItem();

		if (selectedRack != null) {
			boolean okClicked = app.showWarehouseEditDialog(selectedRack);
			if (okClicked) {
				showRackDetails(selectedRack);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(app.getPrimaryStage());
			alert.setTitle(bundle.getString("noChoiceTxt"));
			alert.setHeaderText(bundle.getString("warehouseNotSelectedTxt"));
			alert.setContentText(bundle.getString("chooseWarehouseTxt"));

			alert.showAndWait();
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setMainApp(String activeTab, App app) {

		this.app = app;

		// Add observable lists data to the tables
		warehouseTable.setItems(app.getWarehouseData());
		sectionTable.setItems(app.getSectionData());
		rackTable.setItems(app.getRackData());

	}

}
