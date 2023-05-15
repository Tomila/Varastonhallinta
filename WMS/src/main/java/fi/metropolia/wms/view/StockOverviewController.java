package fi.metropolia.wms.view;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Item;
import fi.metropolia.wms.model.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Class initializes ManagerLayout.fxml file 
 * @author Mihail Karvanen, Roman Prokhozhev
 *
 */
public class StockOverviewController {

	// Item TableView
	@FXML
	private TableView<Item> itemTable;
	@FXML
	private TableColumn<Item, Integer> itemIdColumn;
	@FXML
	private TableColumn<Item, String> itemNameColumn;
	@FXML
	private TableColumn<Item, Integer> itemSaldoColumn;
	@FXML
	private TableColumn<Item, Double> itemPriceColumn;

	@FXML
	private Label itemIdLabel;
	@FXML
	private Label itemNameLabel;
	@FXML
	private Label itemSaldoLabel;
	@FXML
	private Label itemPriceLabel;

	// Warehouse TableView
	@FXML
	private TableView<Warehouse> warehouseTable;
	@FXML
	private TableColumn<Warehouse, Integer> warehouseIdColumn;
	@FXML
	private TableColumn<Warehouse, String> warehouseNameColumn;

	// Reference to the main application
	@SuppressWarnings("unused")
	private App app;

	/**
	 * The constructor The constructor is called before the initialize() method.
	 */

	public StockOverviewController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */

	@FXML
	private void initialize() {
		// Initialize the item table with the two columns.
		itemIdColumn.setCellValueFactory(cellData -> cellData.getValue().getItemIdProperty().asObject());
		itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().getItemNameProperty());
		itemSaldoColumn.setCellValueFactory(cellData -> cellData.getValue().getItemSaldoProperty().asObject());
		itemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getItemPriceProperty().asObject());

		// Initialize the item table with the two columns.
		warehouseIdColumn.setCellValueFactory(cellData -> cellData.getValue().getWarehouseIdProperty().asObject());
		warehouseNameColumn.setCellValueFactory(cellData -> cellData.getValue().getWarehouseNameProperty());

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setMainApp(App app) {
		this.app = app;

		// Add observable list data to the table
		itemTable.setItems(app.getItemData());
		warehouseTable.setItems(app.getWarehouseData());
	}

}

