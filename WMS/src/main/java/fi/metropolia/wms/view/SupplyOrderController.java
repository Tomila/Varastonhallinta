package fi.metropolia.wms.view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.net.URL;
import java.util.ResourceBundle;
import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Item;
import fi.metropolia.wms.model.Supplier;
import fi.metropolia.wms.model.SupplyOrder;
import fi.metropolia.wms.model.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
/**
 * Class initializes SupplyOrder.fxml file
 * @author Mihail Karvanen, Roman Prokhozhev
 *
 */

public class SupplyOrderController implements Initializable{
	
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
	private TableColumn<SupplyOrder, String> supplierNameColumn;
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
	
	
	// Supplier item TableView
	
	@FXML
	private TableView<Item> supplierItemTable;
	
	@FXML
	private TableColumn<Item, Integer> supplierItemIdColumn;
	@FXML
	private TableColumn<Item, String> supplierItemNameColumn;
	@FXML
	private TableColumn<Item, Double> supplierItemPriceColumn;
	
	// Supply order item list
	@FXML
	private GridPane supplyOrderItemsGridPane;
	private Label supplyOrderItemNumberCaption;
	private Label supplyOrderItemNameCaption;
	private Label supplyOrderItemAmountCaption;
	private Label supplyOrderItemReceivedCaption;
	private Label supplyOrderItemShelvedCaption;

	
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
	
	
	//Supply order details window elements
	
	@FXML
	private GridPane supplyOrderDetailsGridPane;
	@FXML
	private Label supplyOrderNumberCaption;
	@FXML
	private Label supplyOrderNumberValue;
	@FXML
	private Label supplyOrderSupplierCaption;
	@FXML
	private Label supplyOrderSupplierValue;
	@FXML
	private Label supplyOrderDateCreateCaption;
	@FXML
	private Label supplyOrderDateCreateValue;
	@FXML
	private Label supplyOrderDateModifyCaption;
	@FXML
	private Label supplyOrderDateModifyValue;
	@FXML
	private Label supplyOrderCreatorCaption;
	@FXML
	private Label supplyOrderCreatorValue;
	@FXML
	private Label supplyOrderAmountCaption;
	@FXML
	private Label supplyOrderAmountValue;
	
	
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
	
	private ResourceBundle bundle;
	
	//First initialize resources 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		// Then fxml elements
		initialize();
	}
	
	
	@FXML
	private void initialize() {
		
		// Initialize the supply order table.
		supplyOrderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplyOrderNumberProperty());
		supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplierNameProperty());
		orderAmountColumn.setCellValueFactory(cellData -> cellData.getValue().getOrderAmountProperty().asObject());
		dateCreateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateCreateProperty());
//		dateModifyColumn.setCellValueFactory(cellData -> cellData.getValue().getDateModifyProperty());
		contactPersonColumn.setCellValueFactory(cellData -> cellData.getValue().getContactPersonProperty());
		
		// Add new supply order button listener
		supplyOrderTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> editSupplyOrder(new Supplier().getSupplierFromDBById(newValue.getSupplierId()), 0.00));
		
		
		
		// -- Supply order toolbar initialization -------------------- //

		// Add new supply order button
		addNewButton = new Button(bundle.getString("newBtn"));
		addNewButton.getStyleClass().add("addNewWarehouseButton");
		addNewButton.setOnAction(event -> handleNewSupplyOrder());
		
		// -- Further functionality will be added later -- //
		/*
		// Edit supply order button
		editButton = new Button(bundle.getString("editBtn"));
		editButton.getStyleClass().add("editWarehouseButton");
		editButton.setOnAction(event -> addNewSupplyOrderItem());

		// Delete supply order button
		deleteButton = new Button(bundle.getString("deleteBtn"));
		deleteButton.getStyleClass().add("deleteWarehouseButton");
		//deleteButton.setOnAction(event -> handleDeleteSection());

		buttonBar.getChildren().addAll(addNewButton, editButton, deleteButton);
		editButton.setDisable(true);

		deleteButton.setDisable(true);
		*/
		buttonBar.getChildren().add(addNewButton);
	}
	
	/**
	 * Method initializes warehouse detail edit view
	 * 
	 * @return GridPane with warehouse details
	 */
	@FXML
	public void initEditSupplyOrder() {

		supplyOrderDetailsGridPane.getChildren().clear();
		
		//buttonBar.getChildren().clear();
		//warehouseTable.getSelectionModel().clearSelection();
		//title.setText("");
		
		// Initialize warehouse details elements
		supplyOrderNumberCaption = new Label();
		supplyOrderNumberValue = new Label();
		supplyOrderCreatorCaption = new Label();
		supplyOrderCreatorValue = new Label();
		
		supplyOrderSupplierCaption = new Label();
		supplyOrderSupplierValue = new Label();
		supplyOrderDateCreateCaption = new Label();
		supplyOrderDateCreateValue = new Label();
		supplyOrderDateModifyCaption = new Label();
		supplyOrderDateModifyValue = new Label();
		supplyOrderAmountCaption = new Label();;
		supplyOrderAmountValue = new Label();;

		supplyOrderDetailsGridPane.add(supplyOrderNumberCaption, 0, 0, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderCreatorCaption, 0, 1, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderDateCreateCaption, 0, 2, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderDateModifyCaption, 0, 3, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderSupplierCaption, 0, 4, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderAmountCaption, 0, 5, 1, 1);
		
		supplyOrderDetailsGridPane.add(supplyOrderNumberValue, 1, 0, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderCreatorValue, 1, 1, 1, 1);	
		supplyOrderDetailsGridPane.add(supplyOrderDateCreateValue, 1, 2, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderDateModifyValue, 1, 3, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderSupplierValue, 1, 4, 1, 1);
		supplyOrderDetailsGridPane.add(supplyOrderAmountValue, 1, 5, 1, 1);

		// -- Warehouse toolbar initialization -------------------- //

		// Add new warehouse button
//		addNewButton = new Button("Uusi..");
//		addNewButton.getStyleClass().add("addNewWarehouseButton");
//		addNewButton.setOnAction(event -> handleNewWarehouse());
//
//		// Edit warehouse button
//		editButton = new Button("Muokkaa...");
//		editButton.getStyleClass().add("editWarehouseButton");
//		editButton.setOnAction(event -> handleEditWarehouse());
//
//		// Delete warehouse button
//		deleteButton = new Button("Poista...");
//		deleteButton.getStyleClass().add("deleteWarehouseButton");
//		deleteButton.setOnAction(event -> handleDeleteWarehouse());
//
//		buttonBar.getChildren().addAll(addNewButton, editButton, deleteButton);
//		editButton.setDisable(true);
//		deleteButton.setDisable(true);
	}
	
	
	private void showSupplyOrderDetails(SupplyOrder supplyOrder) {

//		initEditWarehouse();
//		warehouseTable.getSelectionModel().select(warehouseTable.getSelectionModel().getSelectedIndex());
//
//		// Enable toolbar edit&delete buttons
//		editButton.setDisable(false);
//		deleteButton.setDisable(false);
//
//		if (warehouse != null) {
//			title.setText("Varaston tiedot");
//			warehouseIdCaption.setText("Varaston tunnus");
//			warehouseNameCaption.setText("Varaston nimi");
//			warehouseNameValue.setText(warehouse.getWarehouseName());
//			warehouseIdValue.setText(Integer.toString(warehouse.getWarehouseId()));
//
//		} else {
//			title.setText("");
//			warehouseNameCaption.setText("");
//			warehouseIdCaption.setText("");
//			warehouseNameValue.setText("");
//			warehouseIdValue.setText("");
//			buttonBar.getChildren().clear();

		}
	
	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new supply order.
	 */
	@FXML
	private void handleNewSupplyOrder() {
		//Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SupplyOrder tempSupplyOrder = new SupplyOrder(-1, 0, "","", 0.00, "", "", "");
		boolean okClicked = app.showSupplyOrderEditDialog(tempSupplyOrder);
		// User pushed "OK" button
		if (okClicked) {
			
			app.getSupplyOrderData().add(tempSupplyOrder); // Refreshing supply order data
			supplyOrderTable.getSelectionModel().select(tempSupplyOrder); // Refreshing supply order table selection
			
			showSupplierItems(new Supplier().getSupplierFromDBById(tempSupplyOrder.getSupplierId()));
			showSupplyOrderDetails(tempSupplyOrder);
		}
	}
	
	
	/**
	 * Initializes supplier items table.
	 * 
	 * @param
	 * 
	 */
	private void showSupplierItems(Supplier supplier) {
		
	
		
		// Initialize the supplier items table.
		supplierItemTable.setItems(new Supplier().getSupplierItemsObservableListFromDB(supplier.getSupplierId()));
		
		supplierItemIdColumn.setCellValueFactory(cellData -> cellData.getValue().getItemIdProperty().asObject());
		supplierItemNameColumn.setCellValueFactory(cellData -> cellData.getValue().getItemNameProperty());
		supplierItemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getItemPriceProperty().asObject());

		// Listen for selection changes and show the person details when changed.
		
		/* Button disabled for now
		supplierItemTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> editButton.setDisable(false));
				*/

	}
	
	/**
	 * Method adds new item to the supply order
	 * void addSupplyOrderItemToDB(int supplyOrderId, int itemId, String itemName, double itemPrice, int itemQuantity, String supplierName) 
	 * 
	 * void updateSupplyOrderToDB(int supplyOrderId, double orderAmount, Date dateModified)
	 * 
	 */
	private void addNewSupplyOrderItem() {
//		System.out.println(supplyOrderTable.getSelectionModel().getSelectedItem());
//		System.out.println(supplierItemTable.getSelectionModel().getSelectedItem());
		
		SupplyOrder selectedOrder = supplyOrderTable.getSelectionModel().getSelectedItem();
		Item selectedItem = supplierItemTable.getSelectionModel().getSelectedItem();
		
		// Add supply order item to the database
		selectedOrder.addSupplyOrderItemToDB(selectedOrder.getSupplyOrderId(), selectedItem.getItemId(), selectedItem.getItemName(), 
				selectedItem.getItemPrice(), 10, selectedOrder.getSupplierName());
		
		// Update supply order data
		Timestamp dateModified = new Timestamp(System.currentTimeMillis());
		selectedOrder.updateSupplyOrderToDB(selectedOrder.getSupplyOrderId(), selectedOrder.getOrderAmount()+(selectedItem.getItemPrice()*10), dateModified);
		selectedOrder.setOrderAmount(selectedOrder.getOrderAmount()+selectedItem.getItemPrice()*10);
		// Reset supply order details window
		supplyOrderTable.setItems(app.getSupplyOrderData());
		editSupplyOrder(new Supplier().getSupplierFromDBById(selectedOrder.getSupplierId()), selectedOrder.getOrderAmount());
		initSupplyOrderItems(selectedOrder.getSupplyOrderId());
		
	}
	
	/**
	 * Initializes supply order items table.
	 * 
	 * @param
	 * 
	 */
	private void initSupplyOrderItems(int supplyOrderId) {
		
		// Initialize the supply order items table.
		ArrayList<Item> supplyOrderItemList = new SupplyOrder().getSupplyOrderItemsArrayListFromDB(supplyOrderId);
			
		supplyOrderItemsGridPane.getChildren().clear();
		
		
//		if(supplierItemTable.getSelectionModel().isSelected(supplyOrderId)) {
//			editButton.setDisable(false);
//		}

		// Initialize warehouse details elements
		supplyOrderItemNameCaption = new Label(bundle.getString("itemColumn"));
		supplyOrderItemAmountCaption = new Label(bundle.getString("amountColumn"));
		supplyOrderItemReceivedCaption = new Label(bundle.getString("receivedColumn"));
		supplyOrderItemShelvedCaption = new Label(bundle.getString("storedColumn"));

		
		supplyOrderItemsGridPane.add(new Label(" # "), 0, 0, 1, 1);
		supplyOrderItemsGridPane.add(supplyOrderItemNameCaption, 1, 0, 1, 1);
		supplyOrderItemsGridPane.add(supplyOrderItemAmountCaption, 2, 0, 1, 1);	
		supplyOrderItemsGridPane.add(supplyOrderItemReceivedCaption, 3, 0, 1, 1);	
		supplyOrderItemsGridPane.add(supplyOrderItemShelvedCaption, 4, 0, 1, 1);	
		
		
		for (int i=0; i<supplyOrderItemList.size(); i++) {
			int cou = 0;
			supplyOrderItemsGridPane.add(new Label(Integer.toString(i+1)), cou, i+1, 1, 1);
			supplyOrderItemsGridPane.add(new Label(supplyOrderItemList.get(i).getItemName()), cou+1, i+1, 1, 1);
			supplyOrderItemsGridPane.add(new Label(Integer.toString(supplyOrderItemList.get(i).getItemSaldo())), cou+2, i+1, 1, 1);
			supplyOrderItemsGridPane.add(new Label(Integer.toString(supplyOrderItemList.get(i).getItemReceived())), cou+3, i+1, 1, 1);
			supplyOrderItemsGridPane.add(new Label(Integer.toString(supplyOrderItemList.get(i).getItemShelved())), cou+4, i+1, 1, 1);
		}

	}
	
	/**
	 * Edit supply order.
	 * void updateSupplyOrderItemToDB(int supplyOrderId, double orderAmount, Date dateModified)
	 * 
	 * 
	 * @param
	 * 
	 */
	private void editSupplyOrder(Supplier supplier, double amount) {
		initEditSupplyOrder();
		
		// Reset supplier items table items and selection and disable "edit"-button
		supplierItemTable.getSelectionModel().clearSelection();
		//editButton.setDisable(true);
		initSupplyOrderItems(supplier.getSupplierId());
		
		SupplyOrder selectedOrder = supplyOrderTable.getSelectionModel().getSelectedItem();
		
		// Setting order detail window table
		supplyOrderNumberCaption.setText(bundle.getString("orderNumberTxt"));
		supplyOrderCreatorCaption.setText(bundle.getString("ordererNameColumn"));
		supplyOrderDateCreateCaption.setText(bundle.getString("orderCreatedTxt"));
		supplyOrderDateModifyCaption.setText(bundle.getString("orderEditedTxt"));
		supplyOrderSupplierCaption.setText(bundle.getString("goodsSupplierTxt"));
		supplyOrderAmountCaption.setText(bundle.getString("sumColumn"));
		
		supplyOrderNumberValue.setText(selectedOrder.getSupplyOrderNumber());
		supplyOrderCreatorValue.setText(selectedOrder.getContactPerson());
		supplyOrderDateCreateValue.setText(selectedOrder.getDateCreate());
		supplyOrderDateModifyValue.setText(selectedOrder.getDateModify());
		supplyOrderSupplierValue.setText(selectedOrder.getSupplierName());
		supplyOrderAmountValue.setText(selectedOrder.getOrderAmount() + "€");
		showSupplierItems(supplier);
		
		

	}
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setMainApp(App app) {

		this.app = app;
		
		// Get the supplier table list
		//supplierTable.setItems(app.getSuppliersData());
		
		// Get the supply orders table list
		supplyOrderTable.setItems(app.getSupplyOrderData());
		supplyOrderTable.getSelectionModel().select(0);
	}
	
}
