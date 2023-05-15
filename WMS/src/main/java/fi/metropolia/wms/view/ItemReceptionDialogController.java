package fi.metropolia.wms.view;

import java.util.ResourceBundle;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Bin;
import fi.metropolia.wms.model.Item;
import fi.metropolia.wms.model.ItemLocation;
import fi.metropolia.wms.model.Rack;
import fi.metropolia.wms.model.Section;
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
 * Handles item reception dialog popup window.
 * 
 * @author Mihail Karvanen, Roman Prokhozhev
 *
 */
public class ItemReceptionDialogController {

	// Details GridPane
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	// Item labels
	private Label itemIdCaption;
	private Label itemIdValue;
	private Label itemNameCaption;
	private Label itemNameValue;
	private Label itemQuantityCaption;
	private TextField itemQuantityTextField;
	
	// Item location elements
	
	private Label itemWarehouseCaption;
	private Label itemSectionCaption;
	private Label itemRackCaption;
//	private Label itemBayCaption;
//	private Label itemLevelCaption;
	private Label itemBinCaption;
	
	private int selectedItemWarehouseId;
	private int selectedItemSectionId;
	private int selectedItemRackId;
	private int selectedItemBinId;
	private ChoiceBox<Warehouse> itemWarehouseChoiceBox;
	private ChoiceBox<Section> itemSectionChoiceBox;
	private ChoiceBox<Rack> itemRackChoiceBox;
	private ChoiceBox<Bin> itemBinChoiceBox;
	
	
	private Stage dialogStage;

	// Referencies to the objects
	private Item item;
	private SupplyOrder supplyOrder;
	private boolean okClicked = false;
	
	//For localisation
	private ResourceBundle bundle;
	
	@SuppressWarnings("unused")
	private App app;

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

	// -- INITIALIZE ELEMENTS ------------------------------------------ //
	/**
	 * Method initializes item reception dialog window elements
	 * 
	 * @return
	 */
	@FXML
	public void initReceiveItem() {

		gridPane.getChildren().clear();

		
		// Initialize warehouse details elements
		itemIdCaption = new Label(bundle.getString("itemIdTxt"));
		itemIdValue = new Label();
		itemNameCaption = new Label(bundle.getString("itemNameTxt"));
		itemNameValue = new Label();
		itemQuantityCaption = new Label(bundle.getString("receivedAmountLabel"));
		itemQuantityTextField = new TextField();
		itemQuantityTextField.setText(Integer.toString(item.getItemSaldo()-item.getItemReceived()));

		gridPane.add(itemIdCaption, 0, 0, 1, 1);
		gridPane.add(itemNameCaption, 0, 1, 1, 1);
		gridPane.add(itemQuantityCaption,0, 2, 1, 1);
		gridPane.add(itemIdValue, 1, 0, 1, 1);
		gridPane.add(itemNameValue, 1, 1, 1, 1);
		gridPane.add(itemQuantityTextField, 1, 2, 1, 1);
		
		// Receive item dialog "Ok"-button
		okButton.setText(bundle.getString("okBtn"));
		okButton.setOnAction(event -> handleReceiveItemOk());
		// Warehouse dialog "Cancel"-button
		cancelButton.setText(bundle.getString("cancelBtn"));
		cancelButton.setOnAction(event -> handleCancel());

	}

	/**
	 * Method initializes item shelving dialog window elements
	 * 
	 * @return
	 */
	@FXML
	public void initShelveItem() {

		Warehouse w = new Warehouse();
		Section s = new Section();
		Rack r = new Rack();
		Bin b = new Bin();
		
		gridPane.getChildren().clear();
		

		// Initialize item location details elements
//		rackIdCaption = new Label("Hyllyn tunnus");
//		rackIdValue = new Label();
		
		// Initialize item location warehouse elements
		ObservableList<Warehouse> warehouseList = w.getWarehouseObservableListFromDB();
		
		itemWarehouseCaption = new Label(bundle.getString("warehouseTreeItem"));
		itemWarehouseChoiceBox = new ChoiceBox<Warehouse>();
		itemWarehouseChoiceBox.setItems(warehouseList);
		itemWarehouseChoiceBox.setOnAction((event) -> {
			selectedItemWarehouseId = itemWarehouseChoiceBox.getSelectionModel().getSelectedItem().getWarehouseId();
			itemSectionChoiceBox.getSelectionModel().clearSelection();
			itemSectionChoiceBox.setItems(s.getSectionObservableListFromDBByWarehouseId(selectedItemWarehouseId));
			itemSectionChoiceBox.getSelectionModel().selectFirst();
			selectedItemSectionId = itemSectionChoiceBox.getSelectionModel().getSelectedItem().getSectionId();
		});
		
	
		// Initialize item location section elements
		ObservableList<Section> sectionList = selectedItemWarehouseId == 0 ? s.getSectionObservableListFromDB() :s.getSectionObservableListFromDBByWarehouseId(selectedItemWarehouseId);
		
		itemSectionCaption = new Label(bundle.getString("sectionColumn"));
		itemSectionChoiceBox = new ChoiceBox<Section>();
		itemSectionChoiceBox.setItems(sectionList);
		itemSectionChoiceBox.setOnAction((event) -> {
			selectedItemSectionId = itemSectionChoiceBox.getSelectionModel().getSelectedItem().getSectionId();
		});
		
		// Initialize item location Rack elemants
		ObservableList<Rack> rackList = selectedItemSectionId == 0 ? r.getRackObservableListFromDB() :r.getRackObservableListFromDBByWarehouseAndSection(selectedItemWarehouseId, selectedItemSectionId);
		itemRackCaption = new Label(bundle.getString("rackColumn"));
		itemRackChoiceBox = new ChoiceBox<Rack>();
		itemRackChoiceBox.setItems(rackList);
		itemRackChoiceBox.setOnAction((event) -> {
			selectedItemRackId = itemRackChoiceBox.getSelectionModel().getSelectedItem().getSectionId();
		});	
		
		// Initialize item location Bin elemants
		ObservableList<Bin> binList = selectedItemRackId == 0 ? b.getBinObservableListFromDB() :b.getBinObservableListFromDB(selectedItemRackId);
		itemBinCaption = new Label(bundle.getString("rackLocationLabel"));
		itemBinChoiceBox = new ChoiceBox<Bin>();
		itemBinChoiceBox.setItems(binList);
		itemBinChoiceBox.setOnAction((event) -> {
			selectedItemBinId = itemBinChoiceBox.getSelectionModel().getSelectedItem().getBinId();
		});	
		
		// Initialize warehouse details elements
		itemIdCaption = new Label(bundle.getString("itemIdTxt"));
		itemIdValue = new Label();
		itemNameCaption = new Label(bundle.getString("itemNameTxt"));
		itemNameValue = new Label();
		itemQuantityCaption = new Label(bundle.getString("shelvedLocationLabel"));
		itemQuantityTextField = new TextField();
		itemQuantityTextField.setText(Integer.toString(item.getItemReceived()-item.getItemShelved()));

		gridPane.add(itemIdCaption, 0, 0, 1, 1);
		gridPane.add(itemNameCaption, 0, 1, 1, 1);
		gridPane.add(itemQuantityCaption, 0, 2, 1, 1);
		gridPane.add(itemWarehouseCaption, 0, 3, 1, 1);
		gridPane.add(itemSectionCaption, 0, 4, 1, 1);
		gridPane.add(itemRackCaption, 0, 5, 1, 1);
		gridPane.add(itemBinCaption, 0, 6, 1, 1 );
		
		gridPane.add(itemIdValue, 1, 0, 1, 1);
		gridPane.add(itemNameValue, 1, 1, 1, 1);
		gridPane.add(itemQuantityTextField, 1, 2, 1, 1);
		gridPane.add(itemWarehouseChoiceBox, 1, 3, 1, 1);
		gridPane.add(itemSectionChoiceBox, 1, 4, 1, 1);
		gridPane.add(itemRackChoiceBox, 1, 5, 1, 1);
		gridPane.add(itemBinChoiceBox, 1, 6, 1, 1);
		
		// Receive item dialog "Ok"-button
		okButton.setText(bundle.getString("okBtn"));
		okButton.setOnAction(event -> handleShelveItemOk());
		// Warehouse dialog "Cancel"-button
		cancelButton.setText(bundle.getString("cancelBtn"));
		cancelButton.setOnAction(event -> handleCancel());

	}

	/**
	 * Sets the warehouse to be edited in the dialog.
	 * 
	 * @param item
	 */
	public void setItem(SupplyOrder supplyOrder, Item item, String task) {

		this.item = item;
		this.supplyOrder = supplyOrder;
		
		if (task.equals("receive")) {
			initReceiveItem();
		} else if (task.equals("shelve")) {
			initShelveItem();
		}

		
		itemIdValue.setText(Integer.toString(item.getItemId()));
		itemNameValue.setText(item.getItemName());
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
	private void handleReceiveItemOk() {
		if (isReceiveItemInputValid()) {
			int quantity = Integer.parseInt(itemQuantityTextField.getText());
			int newQuantity = item.getItemSaldo()-item.getItemReceived()-quantity;
			if (newQuantity>=0) {
				new SupplyOrder().updateSupplyOrderItemToDB(supplyOrder.getSupplyOrderId(), item.getItemId(), quantity+item.getItemReceived(), item.getItemShelved());		
			}
			okClicked = true;
			dialogStage.close();
		}
	}
	
	/**
	 * Called when the user clicks ok on warehouse dialog.
	 */
	@FXML
	private void handleShelveItemOk() {
		if (isReceiveItemInputValid()) {
			int quantity = Integer.parseInt(itemQuantityTextField.getText());
			int newQuantity = item.getItemReceived()-item.getItemShelved()-quantity;
			if (newQuantity>=0) {
				new SupplyOrder().updateSupplyOrderItemToDB(supplyOrder.getSupplyOrderId(), item.getItemId(), item.getItemReceived(), quantity+item.getItemShelved());		
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

	private boolean isReceiveItemInputValid() {
		String errorMessage = "";

		if (itemQuantityTextField.getText() == null || itemQuantityTextField.getText().length() == 0) {
			errorMessage += bundle.getString("itemAmounEmptyErr") + "\n";
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
			alert.setHeaderText("");
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
	
	public void setResources(ResourceBundle bundle) {
		this.bundle = bundle;
	}
	
}
