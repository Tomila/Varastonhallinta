package fi.metropolia.wms.view;

import java.sql.SQLException;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Item;
import fi.metropolia.wms.model.ItemLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
/**
 * Class show details of specific item
 * @author Roman Prokhozhev
 *
 */
public class ItemDetailsController {
	@FXML
	private TableView<ItemLocation> itemLocationTableView;
	 
	@FXML
    private TableColumn<ItemLocation, Integer> bayNumberColumn;

    @FXML
    private TableColumn<ItemLocation, Integer> binNumberColumn;
    
    @FXML
    private TableColumn<ItemLocation, Integer> levelNumberColumn;

    @FXML
    private TableColumn<ItemLocation, String> sectionTypeColumn;

    @FXML
    private TableColumn<ItemLocation, String> warehouseNameColumn;
    
    @FXML
    private TableColumn<ItemLocation, Integer> rackIDColumn;
    
    @FXML
    private TableColumn<Item, Integer> itemIDColumn;

    @FXML
    private TableColumn<Item, String> itemNameColumn;

    @FXML
    private TableColumn<Item, Double> itemPriceColumn;

    @FXML
    private TableColumn<Item, Integer> itemSaldoColumn;

    @FXML
    private TableView<Item> itemTableView;
	
    @FXML
	private void initialize() {
		itemIDColumn.setCellValueFactory(cellData -> cellData.getValue().getItemIdProperty().asObject());
		itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().getItemNameProperty());
		itemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getItemPriceProperty().asObject());
		itemSaldoColumn.setCellValueFactory(cellData -> cellData.getValue().getItemSaldoProperty().asObject());
		bayNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getBayNumber().asObject());
		binNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getBinNumber().asObject());
		levelNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getLevelNumber().asObject());
		sectionTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getSectionType());
		warehouseNameColumn.setCellValueFactory(cellData -> cellData.getValue().getWarehouseName());
		rackIDColumn.setCellValueFactory(cellData -> cellData.getValue().getRackID().asObject());
	}
	

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 * @throws SQLException 
	 */
	public void setMainApp(int itemID, App app) throws SQLException {
		ObservableList<Item> item = app.getItemByID(itemID);
		itemTableView.setItems(item);
		ObservableList<ItemLocation> location = FXCollections.observableArrayList();
		location.add(item.get(0).getItemLocation());
		itemLocationTableView.setItems(location);
	}
}
