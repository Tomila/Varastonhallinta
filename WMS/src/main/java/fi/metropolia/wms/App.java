package fi.metropolia.wms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import fi.metropolia.wms.model.Item;
import fi.metropolia.wms.model.Rack;
import fi.metropolia.wms.model.Section;
import fi.metropolia.wms.model.Supplier;
import fi.metropolia.wms.model.SupplyOrder;
import fi.metropolia.wms.model.Warehouse;
import fi.metropolia.wms.model.Language;
import fi.metropolia.wms.view.LoginLayoutController;
import fi.metropolia.wms.view.ManagerLayoutController;
import fi.metropolia.wms.view.RootLayoutController;
import fi.metropolia.wms.model.CustomerOrder;
import fi.metropolia.wms.view.CollectorViewController;
import fi.metropolia.wms.view.CustomerOrdersOverviewController;
import fi.metropolia.wms.view.GoodsReceptionController;
import fi.metropolia.wms.view.ItemDetailsController;
import fi.metropolia.wms.view.ItemReceptionDialogController;
import fi.metropolia.wms.view.StockOverviewController;
import fi.metropolia.wms.view.SupplyOrderController;
import fi.metropolia.wms.view.SupplyOrderEditDialogController;
import fi.metropolia.wms.view.WarehouseEditorController;
import fi.metropolia.wms.view.WhEditDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Class initializes all views in this application. 
 * 
 * @author Mihail Karvanen, Roman Prokhozhev
 */
public class App extends Application {
	

	private Stage primaryStage;
	private BorderPane rootLayout;
	private StackPane loginLayout;
	// Default locale
	private Locale locale = new Locale("en_UK");
	// For testing other locales
	// private Locale locale = new Locale("ru_RU");
	private ResourceBundle bundle = ResourceBundle.getBundle("localisation/local", locale);
	private static int sessionID;

	/**
	 * Item data as an observable list to display on table view
	 */
	private ObservableList<Item> itemData = FXCollections.observableArrayList();

	/**
	 * Objects observable lists to display on table view
	 */
	private ObservableList<Warehouse> warehouseData = FXCollections.observableArrayList();
	private ObservableList<Section> sectionData = FXCollections.observableArrayList();
	private ObservableList<Rack> rackData = FXCollections.observableArrayList();
	private ObservableList<SupplyOrder> supplyOrderData = FXCollections.observableArrayList();
	private ObservableList<Supplier> supplierData = FXCollections.observableArrayList();

	private ObservableList<CustomerOrder> customerOrderData = FXCollections.observableArrayList();
	private ObservableMap<Item, Integer> customerOrderedItemData = FXCollections.observableHashMap();

	/**
	 * Language data
	 */
	private ArrayList<Language> languageData;

	/**
	 * Constructor
	 */
	public App() {
		itemData = new Item().getItemListFromDB();
		// This method returns data of all orders
		customerOrderData = new CustomerOrder().getOrderListFromDB();
		warehouseData = new Warehouse().getWarehouseObservableListFromDB();
		sectionData = new Section().getSectionObservableListFromDB();
		rackData = new Rack().getRackObservableListFromDB();
		supplyOrderData = new SupplyOrder().getSupplyOrderObservableListFromDB();
	}

	/**
	 * Returns the data as an observable list of Items.
	 * 
	 * @return
	 */

	public ObservableList<Item> getItemData() {
		return itemData;
	}

	public ObservableList<Item> getItemByID(int id) throws SQLException {
		Item item = new Item().getItemFromDBById(id);
		ObservableList<Item> itemList = FXCollections.observableArrayList();
		itemList.add(new Item(item.getItemId(), item.getItemName(), item.getItemSaldo(), item.getItemPrice()));
		return itemList;
	}

	// Get suppliers data from the DB
	public ObservableList<Supplier> getSuppliersData() {
		return new Supplier().getSupplierObservableListFromDB();
	}

	public ObservableList<Warehouse> refreshWarehouseData() {
		return new Warehouse().getWarehouseObservableListFromDB();
	}

	public ObservableList<Section> refreshSectionData() {
		return new Section().getSectionObservableListFromDB();
	}

	public ObservableList<Rack> refreshRackData() {
		return new Rack().getRackObservableListFromDB();
	}

	public ObservableList<Warehouse> getWarehouseData() {
		return warehouseData;
	}

	public ObservableList<Section> getSectionData() {
		return sectionData;
	}

	public ObservableList<Rack> getRackData() {
		return rackData;
	}

	public ObservableList<SupplyOrder> getSupplyOrderData() {
		return supplyOrderData;
	}

	public ObservableList<CustomerOrder> getCustomerOrderData() {
		return customerOrderData;
	}

	public ObservableMap<Item, Integer> getCustomerOrderedItemData() {
		return customerOrderedItemData;
	}

	public ArrayList<Language> getLanguageData() {
		return new Language().getLanguageListFromDB();
	}

	// User group id getter & setter
	public int getSessionID() {
		return App.sessionID;
	}

	public void setSessionID(int sessionID) {
		App.sessionID = sessionID;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Wms");
		
		initRootLayout();
		showLoginLayout();

	}

	/**
	 * Method starting UI components after user has been logged in depending on the
	 * sessionID
	 * 
	 * @param sessionID
	 */
	public void authenticated(int sessionID) {
		setSessionID(sessionID);
		if (sessionID == 1) {
			showManagerLayout();
			showStockOverview();
		} else {
			showManagerLayout();
			showStockOverview();
		}
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		initRootLayout();
		showLoginLayout();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
			loader.setResources(bundle);
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitHint("");
			
			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initializes user login layout
	 * 
	 */
	public void showLoginLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/LoginLayout.fxml"));
			loader.setResources(bundle);
			loginLayout = (StackPane) loader.load();
			rootLayout.setCenter(loginLayout);

			// Give the controller access to the main app.
			LoginLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the tree menu inside the root layout.
	 */
	public void showManagerLayout() {
		try {
			// Load manager layout.

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/ManagerLayout.fxml"));
			loader.setResources(bundle);
			AnchorPane ManagerLayout = (AnchorPane) loader.load();

			ManagerLayoutController controller = loader.getController();

			// Set person overview into the center of root layout.
			rootLayout.setLeft(ManagerLayout);

			// Give the controller access to the main app.
			controller.setMainApp(this);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Shows the stock overview inside the root layout.
	 */
	public void showStockOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/StockOverview.fxml"));
			loader.setResources(bundle);
			AnchorPane stockOverview = (AnchorPane) loader.load();
			// Set person overview into the center of root layout.
			rootLayout.setCenter(stockOverview);

			// Give the controller access to the main app.
			StockOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Shows warehouse editor view inside the root layout
	 * 
	 */
	public void showWarehouseEditor() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/WarehouseEditor.fxml"));
			loader.setResources(bundle);
			AnchorPane warehouseEditor = (AnchorPane) loader.load();
			// Give the controller access to the main app.
			WarehouseEditorController controller = loader.getController();
			controller.initialize(App.class.getResource("view/WarehouseEditor.fxml"), bundle);
			// Set warehouse editor into the center of root layout.
			rootLayout.setCenter(warehouseEditor);
			controller.setMainApp("warehouse", this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Shows the goods reception view inside the root layout.
	 */
	public void showGoodsReception() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/GoodsReceptionView.fxml"));
			loader.setResources(bundle);
			AnchorPane goodsReception = (AnchorPane) loader.load();
			// Set person overview into the center of root layout.
			rootLayout.setCenter(goodsReception);

			// Give the controller access to the main app.
			GoodsReceptionController controller = loader.getController();
			controller.setResourceBundle(bundle);
			controller.setMainApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Opens an item reception dialog. If the user clicks OK, the changes are saved
	 * into the provided item object and true is returned.
	 * 
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */

	public boolean showItemReceptionDialog(Object supplyOrder, Object item, String task) {
		try {

			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/ItemReceptionDialog.fxml"));
			loader.setResources(bundle);
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();

			if (task.equals("receive")) {
				dialogStage.setTitle(bundle.getString("itemReceivingTxt"));
				dialogStage.setHeight(300);
				dialogStage.setWidth(300);
			} else if (task.equals("shelve")) {
				dialogStage.setTitle(bundle.getString("itemShelvingTxt"));
				dialogStage.setHeight(300);
				dialogStage.setWidth(300);
			}

			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ItemReceptionDialogController controller;
			controller = loader.getController();
			controller.setResources(bundle);
			controller.setDialogStage(dialogStage);

			if (task.equals("receive")) {
				controller.setItem((SupplyOrder) supplyOrder, (Item) item, "receive");
			} else if (task.equals("shelve")) {
				controller.setItem((SupplyOrder) supplyOrder, (Item) item, "shelve");
			}

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to edit details for the specified object. If the user clicks
	 * OK, the changes are saved into the provided person object and true is
	 * returned.
	 * 
	 * @param object the object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */

	public boolean showWarehouseEditDialog(Object object) {
		try {

			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/WarehouseEditDialog.fxml"));
			loader.setResources(bundle);
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();

			if (object.getClass().getName().equals("fi.metropolia.wms.model.Warehouse")) {
				dialogStage.setTitle(bundle.getString("warehouseColumn"));
			} else if (object.getClass().getName().equals("fi.metropolia.wms.model.Section")) {
				dialogStage.setTitle(bundle.getString("sectionColumn"));
			} else if (object.getClass().getName().equals("fi.metropolia.wms.model.Rack")) {
				dialogStage.setTitle(bundle.getString("rackColumn"));
			}
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			WhEditDialogController controller;
			controller = loader.getController();
			controller.setDialogStage(dialogStage);

			if (object.getClass().getName().equals("fi.metropolia.wms.model.Warehouse")) {
				// Set the warehouse into the controller.
				controller.setWarehouse((Warehouse) object);
			} else if (object.getClass().getName().equals("fi.metropolia.wms.model.Section")) {
				// Set the section into the controller.
				controller.setSection((Section) object);
			} else if (object.getClass().getName().equals("fi.metropolia.wms.model.Rack")) {
				// Set the section into the controller.
				dialogStage.setHeight(300);
				dialogStage.setWidth(300);
				controller.setRack((Rack) object);
			}

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Opens a dialog to edit details for the specified object. If the user clicks
	 * OK, the changes are saved into the provided person object and true is
	 * returned.
	 * 
	 * @param object the object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showSupplyOrderEditDialog(SupplyOrder supplyOrder) {
		try {

			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/SupplyOrderEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();

			dialogStage.setTitle(bundle.getString("newDeliveryOrderTreeItem"));

			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupplyOrderEditDialogController controller;
			controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setResources(bundle);

			// Set the section into the controller.
			dialogStage.setHeight(300);
			dialogStage.setWidth(300);
			controller.setSupplyOrder(supplyOrder);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 *Shows view with all customer orders from DB
	 * 
	 */
	// Show all customer orders -view
	public void showCustomerOrdersOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/CustomerOrdersOverview.fxml"));
			loader.setResources(bundle);
			// Anchor Pane?
			BorderPane ordersOverview = (BorderPane) loader.load();
			// Set person overview into the center of root layout.
			rootLayout.setCenter(ordersOverview);

			// Give the controller access to the main app.
			CustomerOrdersOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *Shows view that allows to make new supply order
	 * 
	 */
	// Shows new supply order -view
	public void showNewSupplyOrder() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/SupplyOrder.fxml"));
			loader.setResources(bundle);
			AnchorPane supplyOrder = (AnchorPane) loader.load();
			// Set person overview into the center of root layout.
			rootLayout.setCenter(supplyOrder);

			// Give the controller access to the main app.
			SupplyOrderController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *Shows view with ordered items to collect. 
	 * 
	 *@param Specific number of order that need to be collected
	 */
	public void showCollectorView(int orderNumber) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("view/CollectorView.fxml"));
		fxmlLoader.setResources(bundle);
		// fxmlLoader.setController(controller);
		BorderPane ordersOverview = (BorderPane) fxmlLoader.load();
		rootLayout.setCenter(ordersOverview);
		CollectorViewController controller = fxmlLoader.getController();
		controller.setResources(bundle);
		controller.setOrderNumber(orderNumber);
		controller.setItemsByOrderedNumber(this);
	}
	
	/**
	 *Marks order as collected and again shows view with other orders to collect. 
	 * 
	 *@param Specific number of order that need to be marked as collected
	 */
	public void markOrderAsCollected(int orderNumber) {
		try {
			// Saves changes to DB
			new CustomerOrder().markOrderAsCollected(orderNumber);

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/CustomerOrdersOverview.fxml"));
			loader.setResources(bundle);
			
			BorderPane ordersOverview = (BorderPane) loader.load();

			rootLayout.setCenter(ordersOverview);

			// Give the controller access to the main app.
			CustomerOrdersOverviewController controller = loader.getController();
			controller.setMainApp(this);
			controller.markOrderAsCollected(orderNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *Shows view with details of specific item.  
	 * 
	 *@param Specific id of item that wanted to be shown with details
	 */
	public void showItemDetails(int itemID) throws IOException, SQLException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("view/ItemDetails.fxml"));
		fxmlLoader.setResources(bundle);
		AnchorPane page = (AnchorPane) fxmlLoader.load();

		Stage dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.setTitle(bundle.getString("itemDetailsTxt"));
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		ItemDetailsController controller = fxmlLoader.getController();
		controller.setMainApp(itemID, this);
		dialogStage.showAndWait();
		/*
		 * ItemDetailsController controller = fxmlLoader.getController();
		 * fxmlLoader.setController(controller); BorderPane ordersOverview =
		 * (BorderPane) fxmlLoader.load(); rootLayout.setCenter(ordersOverview);
		 * controller.setItemsByOrderedNumber();
		 */
	}
	
	/**
	 *Shows dialog that tells if file is printed  
	 * 
	 */
	public void showfilePrintedWindow() throws IOException, SQLException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("view/FilePrintedWindow.fxml"));
		fxmlLoader.setResources(bundle);
		AnchorPane page = (AnchorPane) fxmlLoader.load();

		Stage dialogStage = new Stage();
		dialogStage.setTitle(bundle.getString("orderPrintedTxt"));
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();
	}
	
	/**
	 *Shows dialog which asks user to check collected items.
	 * 
	 */
	public void showCheckCollectedListWindow() throws IOException, SQLException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("view/CheckCollectedListWindow.fxml"));
		fxmlLoader.setResources(bundle);
		AnchorPane page = (AnchorPane) fxmlLoader.load();

		Stage dialogStage = new Stage();
		dialogStage.setTitle(bundle.getString("checkProductList"));
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();
	}
	
	/**
	 *Shows dialog which tells that order is successfully collected.
	 * 
	 */
	public void showOrderCollectedWindow() throws IOException, SQLException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("view/OrderCollectedWindow.fxml"));
		fxmlLoader.setResources(bundle);
		AnchorPane page = (AnchorPane) fxmlLoader.load();
		Stage dialogStage = new Stage();
		dialogStage.setTitle(bundle.getString("orderCollectedLabel"));
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();
	}
	
	/**
	 *Changes interface language(bundles locale) by selected language id.
	 * 
	 * @param Selected language id.
	 */
	public void ChangeLanguage(int i) {
		switch (i) {
		case 1:
			bundle = ResourceBundle.getBundle("localisation/local", new Locale("fi_FI"));
			break;
		case 2:
			bundle = ResourceBundle.getBundle("localisation/local", new Locale("en_UK"));
			break;
		case 3:
			bundle = ResourceBundle.getBundle("localisation/local", new Locale("ru_RU"));
			break;
		}
		initRootLayout();
		showLoginLayout();
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ResourceBundle getResources() {
		return bundle;
	}

}