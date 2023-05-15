package fi.metropolia.wms.view;

import java.io.IOException;


import fi.metropolia.wms.App;
import fi.metropolia.wms.model.CustomerOrder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Class is controller for CustomerOrdersOverview.fxml file
 * @author Roman Prokhozhev
 *
 */

public class CustomerOrdersOverviewController {
	@FXML
	private TableView<CustomerOrder> customerOrderTable;

	@FXML
	private TableColumn<CustomerOrder, String> customerNameColumn;

	@FXML
	private TableColumn<CustomerOrder, Integer> salesOrderNumberColumn;

	private App app;
	/**
	 * Initializes elements
	 */
	@FXML
	private void initialize() {
		customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerNameProperty());
		salesOrderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getOrderNumberProperty().asObject());
	}
	/**
	 * Calls App.java class to show selected orders details.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void getSelectedOrder(MouseEvent event) throws IOException {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (event.getClickCount() == 2) {
				if (customerOrderTable.getSelectionModel().getSelectedItem() != null) {
					// Getting order number from table view after double click
					int orderNumber = customerOrderTable.getSelectionModel().getSelectedItem().getOrderNumberProperty()
							.getValue().intValue();
					app.showCollectorView(orderNumber);
				}
			}
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setMainApp(App app) {
		this.app = app;
		// Add observable list data to the table
		customerOrderTable.setItems(app.getCustomerOrderData());
	}
	
	/**
	 * Marks order as collected by order number.
	 * @param orderNumber
	 */
	public void markOrderAsCollected(int orderNumber) {
		
		ObservableList<CustomerOrder> allOrders = customerOrderTable.getItems();
		ObservableList<CustomerOrder> ordersToRemove = FXCollections.observableArrayList();
		for (CustomerOrder order : allOrders) {
			if (order.getOrderNumberProperty().getValue().intValue() == orderNumber) {
				ordersToRemove.add(order);
			}
		}
		if (!ordersToRemove.isEmpty()) {
			customerOrderTable.getItems().removeAll(ordersToRemove);
		}
		
	}
	/*
	 * public void showCollectorView(int orderNumber) throws IOException {
	 * FXMLLoader fxmlLoader = new FXMLLoader();
	 * fxmlLoader.setLocation(getClass().getResource("CollectorView.fxml"));
	 * CollectorViewController controller = new
	 * CollectorViewController(orderNumber); fxmlLoader.setController(controller);
	 * Stage stage = new Stage(); stage.setTitle("Orders collecting");
	 * stage.setScene(new Scene(fxmlLoader.load())); stage.show(); }
	 */
}



