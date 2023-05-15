package fi.metropolia.wms.view;

import java.net.URL;
import java.util.ResourceBundle;
import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Item;
import fi.metropolia.wms.model.SupplyOrder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
/**
 * 
 * @author Mihail Karvanen, Roman Prokhozhev
 *
 */
public class GoodsReceptionController implements Initializable {

	// Supply orders TableView
	@FXML
	private TableView<SupplyOrder> supplyOrderTable;

	@FXML
	private TableColumn<SupplyOrder, Integer> supplyOrderIdColumn;
	@FXML
	private TableColumn<SupplyOrder, String> supplyOrderNumberColumn;
	@FXML
	private TableColumn<SupplyOrder, Integer> supplierIdColumn;
	@FXML
	private TableColumn<SupplyOrder, Integer> itemIdColumn;
	@FXML
	private TableColumn<SupplyOrder, String> itemNameColumn;
	@FXML
	private TableColumn<SupplyOrder, Double> orderAmountColumn;
	@FXML
	private TableColumn<SupplyOrder, String> dateCreateColumn;
	@FXML
	private TableColumn<SupplyOrder, String> dateModifyColumn;
	@FXML
	private TableColumn<SupplyOrder, String> contactPersonColumn;
	@FXML
	private TableColumn<SupplyOrder, Integer> quantityColumn;

	// Supply orders item TableView
	@FXML
	private TableView<Item> supplyOrderItemTable;
	@FXML
	private TableColumn<Item, String> supplyOrderItemNameColumn;
	@FXML
	private TableColumn<Item, Double> supplyOrderItemPriceColumn;
	@FXML
	private TableColumn<Item, Integer> supplyOrderItemQuantityColumn;
	@FXML
	private TableColumn<Item, String> supplyOrderSupplierColumn;
	@FXML
	private TableColumn<Item, Integer> supplyOrderItemReceivedColumn;
	@FXML
	private TableColumn<Item, Integer> supplyOrderItemShelvedColumn;

	// Item detail window elements
	@FXML
	private GridPane itemDetailsGridPane;
	@FXML
	private Label itemNameCaption;
	@FXML
	private Label itemNameValue;
	private Label itemSupplierName;
	private Label itemQuantity;
	private Label itemQuantityReceived;
	private Label itemQuantityShelved;

	// Buttons
	@FXML
	private Button receiveButton;
	@FXML
	private Button shelveButton;

	// Supply order toolbar
	@FXML
	private HBox buttonBar;
	@FXML
	private Button addNewButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;

	// Reference to the main App
	private App app;
	// For localisation
	private ResourceBundle bundle;

	// First initializing resource bundle, then fxml elements
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		initialize();
	}

	@FXML
	private void initialize() {

		// Initialize the supply order table.
		supplyOrderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplyOrderNumberProperty());
		orderAmountColumn.setCellValueFactory(cellData -> cellData.getValue().getOrderAmountProperty().asObject());
		dateCreateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateCreateProperty());
//		dateModifyColumn.setCellValueFactory(cellData -> cellData.getValue().getDateModifyProperty());
		contactPersonColumn.setCellValueFactory(cellData -> cellData.getValue().getContactPersonProperty());

		// Listen for selection changes and show the suply order items details when
		// changed.
		supplyOrderTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showSupplyOrderDetails(newValue));

		// -- Supply order toolbar initialization -------------------- //
		// -- Will be added later --//
		/*
		// Add new section button
		addNewButton = new Button(bundle.getString("newBtn"));
		addNewButton.getStyleClass().add("addNewWarehouseButton");
		// addNewButton.setOnAction(event -> handleNewSection());

		// Edit section button
		editButton = new Button(bundle.getString("editBtn"));
		editButton.getStyleClass().add("editWarehouseButton");
		// editButton.setOnAction(event -> handleEditSection());

		// Delete section button
		deleteButton = new Button(bundle.getString("deleteBtn"));
		deleteButton.getStyleClass().add("deleteWarehouseButton");
		// deleteButton.setOnAction(event -> handleDeleteSection());

		buttonBar.getChildren().addAll(addNewButton, editButton, deleteButton);
		editButton.setDisable(true);
		deleteButton.setDisable(true);
		*/

	}

	// ---- SHOW ELEMENTS
	// ------------------------------------------------------------------- //

	/**
	 * Shows supply orders table.
	 * 
	 * @param
	 * 
	 */
	private void showSupplyOrderDetails(SupplyOrder supplyOrder) {

		// Initialize the supply order items table.
		supplyOrderItemTable.setItems(new SupplyOrder().getSupplyOrderItemsObservableListFromDB(
				supplyOrderTable.getSelectionModel().getSelectedItem().getSupplyOrderId()));

		supplyOrderItemNameColumn.setCellValueFactory(cellData -> cellData.getValue().getItemNameProperty());
//		supplyOrderItemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getItemPriceProperty().asObject());
		supplyOrderItemQuantityColumn
				.setCellValueFactory(cellData -> cellData.getValue().getItemSaldoProperty().asObject());
		supplyOrderItemReceivedColumn
				.setCellValueFactory(cellData -> cellData.getValue().getItemReceivedProperty().asObject());
		supplyOrderItemShelvedColumn
				.setCellValueFactory(cellData -> cellData.getValue().getItemShelvedProperty().asObject());
//		supplyOrderSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getItemNameProperty());

		// Listen for selection changes and show the item details when changed.
		supplyOrderItemTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showSupplyOrderItemDetails(newValue));

	}

	/**
	 * Fills all text fields to show details about the warehouse. If the specified
	 * warehouse is null, all text fields are cleared.
	 * 
	 * @param
	 * 
	 */
	private void showSupplyOrderItemDetails(Item item) {

		// Initialize the supply order items table.

		if (item != null) {

			itemDetailsGridPane.getChildren().clear();

			// Initialize the supply order item details elements

			itemNameCaption = new Label(bundle.getString("itemNameTxt"));
			itemNameValue = new Label(item.getItemName());

			// Receive item button
			receiveButton = new Button(bundle.getString("receiveTxt"));
			// receiveButton.setText("Ota vastaan");
			receiveButton.setOnAction(event -> handleReceiveItem());
			// shelve item button
			shelveButton = new Button(bundle.getString("shelveTxt"));
			// shelveButton.setText("Hyllyttää");
			shelveButton.setOnAction(event -> handleShelveItem());

			itemDetailsGridPane.add(itemNameCaption, 0, 0, 1, 1);

			if (item.getItemSaldo() > item.getItemReceived()) {
				itemDetailsGridPane.add(receiveButton, 0, 3, 1, 1);
			}
			if (item.getItemReceived() > item.getItemShelved()) {
				itemDetailsGridPane.add(shelveButton, 0, 5, 1, 1);
			}

//		gridPane.add(rackSectionCaption, 0, 2, 1, 1);
//		gridPane.add(rackLevelsCaption, 0, 3, 1, 1 );
//		gridPane.add(rackColumnsCaption, 0, 4, 1, 1 );
//		
			itemDetailsGridPane.add(itemNameValue, 1, 0, 1, 1);
//		gridPane.add(rackWarehouseChoiceBox, 1, 1, 1, 1);
//		gridPane.add(rackSectionChoiceBox, 1, 2, 1, 1);
		} else {
			itemDetailsGridPane.getChildren().clear();
		}
	}

	/**
	 * Called when the user clicks the "Receive item" button. Opens a dialog to
	 * receive item
	 * 
	 */
	@FXML
	private void handleReceiveItem() {
		boolean okClicked = app.showItemReceptionDialog(supplyOrderTable.getSelectionModel().getSelectedItem(),
				supplyOrderItemTable.getSelectionModel().getSelectedItem(),"receive");
		if (okClicked) {
			supplyOrderItemTable.setItems(new SupplyOrder().getSupplyOrderItemsObservableListFromDB(
					supplyOrderTable.getSelectionModel().getSelectedItem().getSupplyOrderId()));
		}
	}

	/**
	 * Called when the user clicks "Shelve item" button. Opens a dialog to shelve
	 * item
	 * 
	 */
	@FXML
	private void handleShelveItem() {
		boolean okClicked = app.showItemReceptionDialog(supplyOrderTable.getSelectionModel().getSelectedItem(),
				supplyOrderItemTable.getSelectionModel().getSelectedItem(), "shelve");
		if (okClicked) {
			supplyOrderItemTable.setItems(new SupplyOrder().getSupplyOrderItemsObservableListFromDB(
					supplyOrderTable.getSelectionModel().getSelectedItem().getSupplyOrderId()));
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setMainApp(App app) {

		this.app = app;

		// Add observable lists data to the tables
		supplyOrderTable.setItems(app.getSupplyOrderData());
		supplyOrderTable.getSelectionModel().select(0);
	}

	public void setResourceBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}
