package fi.metropolia.wms.view;

import java.net.URL;
import java.util.ResourceBundle;

import fi.metropolia.wms.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
/**
 * Class initializes and shows ManagerLayout.fxml file
 * @author Mihail Karvanen, Roman Prokhozhev
 *
 */
public class ManagerLayoutController implements Initializable {

	@FXML
	private TreeView<String> rootTreeMenu;

	// Reference to the main application
	private App app;

	// private int sessionID = app.getSessionID();

	// Resource bundle where localisation comes from
	private ResourceBundle bundle;

	@FXML
	private TreeItem<String> stockButton;
	@FXML
	private TreeItem<String> warehouseButton;
	@FXML
	private TreeItem<String> ordersButton;
	@FXML
	private TreeItem<String> supplyOrderButton;
	@FXML
	private TreeItem<String> rootButton;

	/**
	 * The constructor The constructor is called before the initialize() method.
	 */

	public ManagerLayoutController() {

	}

	public void initSessionID(int sessionID) {
		setSessionID(sessionID);
	}

	// First initializing resource bundle, then fxml elements 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		initialize();
	}

	// initializing fxml elements
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {
		// Initialize root tree menu
		rootButton = new TreeItem<>(bundle.getString("mainMenuTreeItem"));

		stockButton = new TreeItem<>(bundle.getString("goodsHandlingTreeItem"));
		TreeItem<String> stockOverviewButton = new TreeItem<>(bundle.getString("warehouseOverviewTreeItem"));
		TreeItem<String> goodsReceptionButton = new TreeItem<>(bundle.getString("itemReceptionTreeItem"));
		stockButton.getChildren().addAll(stockOverviewButton, goodsReceptionButton);
		stockButton.setExpanded(true);

		warehouseButton = new TreeItem<>(bundle.getString("warehouseTreeItem"));
		TreeItem<String> editWarehouseButton = new TreeItem<>(bundle.getString("warehouseStructureTreeItem"));
		warehouseButton.getChildren().addAll(editWarehouseButton);
		warehouseButton.setExpanded(true);

		ordersButton = new TreeItem<>(bundle.getString("customerOrdersTreeItem"));
		TreeItem<String> ordersOverviewButton = new TreeItem<>(bundle.getString("customerOrderCollectingTreeItem"));
		ordersButton.getChildren().addAll(ordersOverviewButton);
		ordersButton.setExpanded(true);

		supplyOrderButton = new TreeItem<>(bundle.getString("goodsDeliveryTreeItem"));
		TreeItem<String> newSupplyOrderButton = new TreeItem<>(bundle.getString("newDeliveryOrderTreeItem"));
		supplyOrderButton.getChildren().addAll(newSupplyOrderButton);
		supplyOrderButton.setExpanded(true);

		rootTreeMenu.setRoot(rootButton);

		rootTreeMenu.setCellFactory(tree -> {

			TreeCell<String> cell = new TreeCell<String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(item);
					}
				}
			};
			cell.setOnMouseClicked(event -> {
				if (!cell.isEmpty()) {
					TreeItem<String> treeItem = cell.getTreeItem();
					if (treeItem.getValue().equals(bundle.getString("warehouseOverviewTreeItem"))) {
						app.showStockOverview();
					} else if (treeItem.getValue().equals(bundle.getString("itemReceptionTreeItem"))) {
						app.showGoodsReception();
					} else if (treeItem.getValue().equals(bundle.getString("warehouseStructureTreeItem"))) {
						app.showWarehouseEditor();
					} else if (treeItem.getValue().equals(bundle.getString("customerOrderCollectingTreeItem"))) {
						app.showCustomerOrdersOverview();
					} else if (treeItem.getValue().equals(bundle.getString("newDeliveryOrderTreeItem"))) {
						app.showNewSupplyOrder();
					}
				}
			});
			return cell;
		});

	}
	
	public void handleClickMenuItem(MouseEvent event) {
		System.out.println("Mouse clicked!" + rootTreeMenu.getSelectionModel().getSelectedItem().getValue());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	@SuppressWarnings("unchecked")
	public void setMainApp(App app) {
		this.app = app;

		// Initializing the Main Menu depending on the user group
		if (app.getSessionID() == 1) {
			rootButton.getChildren().addAll(stockButton, ordersButton);
		} else if (app.getSessionID() == 2) {
			rootButton.getChildren().addAll(stockButton, warehouseButton, ordersButton, supplyOrderButton);
		}

		rootButton.setExpanded(true);
		rootTreeMenu.showRootProperty().set(false);
		// System.out.println(app.getSessionID());
	}

	public void setSessionID(int sessionID) {
		// this.sessionID = sessionID;

	}

	public void setResourceBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}
