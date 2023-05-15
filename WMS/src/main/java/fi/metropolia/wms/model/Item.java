package fi.metropolia.wms.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import fi.metropolia.wms.db.Database;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * Handles product logic.
 * 
 * @author Mihail Karvanen
 *
 */

public class Item {

	private IntegerProperty itemId;
	private StringProperty itemName;
	private IntegerProperty itemSaldo;
	private IntegerProperty received;
	private IntegerProperty shelved;
	private DoubleProperty itemPrice;
	private StringProperty itemSupplierName;

	public Item() {

	}

	public Item(int itemId, String itemName, int itemSaldo, double itemPrice) {
		this.itemId = new SimpleIntegerProperty(itemId);
		this.itemName = new SimpleStringProperty(itemName);
		this.itemSaldo = new SimpleIntegerProperty(itemSaldo);
		this.itemPrice = new SimpleDoubleProperty(itemPrice);
		
	}
	
	/**
	 * Parametrized constructor for the item reception
	 *  
	 * @param itemId
	 * @param itemName
	 * @param itemPrice
	 * @param itemSaldo
	 * @param received
	 * @param shelved
	 * @param supplierName
	 */
	public Item(int itemId, String itemName, double itemPrice, int itemSaldo, int itemReceived, int itemShelved, String supplierName) {
		this.itemId = new SimpleIntegerProperty(itemId);
		this.itemName = new SimpleStringProperty(itemName);
		this.itemSaldo = new SimpleIntegerProperty(itemSaldo);
		this.itemPrice = new SimpleDoubleProperty(itemPrice);
		this.received = new SimpleIntegerProperty(itemReceived);
		this.shelved = new SimpleIntegerProperty(itemShelved);
		this.itemSupplierName = new SimpleStringProperty(supplierName);
	}
	
	/**
	 * Parametrized constructor for the supply order
	 *  
	 * @param itemId
	 * @param itemName
	 * @param itemPrice
	 */
	public Item(int itemId, String itemName, double itemPrice) {
		this.itemId = new SimpleIntegerProperty(itemId);
		this.itemName = new SimpleStringProperty(itemName);
		this.itemPrice = new SimpleDoubleProperty(itemPrice);
	}

	// Database methods

	/**
	 * Method inserts new Item to the database
	 * 
	 * @return Number of affected rows
	 * @throws SQLException
	 */
	public int addItemToDB() throws SQLException {
		Database database = new Database();

		Object[] params = { null, itemName, itemSaldo, itemPrice };
		int result = database.insert("item", params);

		return result;
	}

	/**
	 * Method returns ObservableList containing objects of all items from the DB
	 * 
	 * @return
	 */
	public ObservableList<Item> getItemListFromDB() {
		ObservableList<Item> itemList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Item_ID", "ItemName", "Saldo", "Price" };
			Object[] params = null;

			ResultSet rs = database.select("item", columns, "1", params);
			// getInt(price) todennäköisesti muuttuu getDouble/Float
			while (rs.next()) {
				itemList.add(new Item(rs.getInt("Item_ID"), rs.getString("ItemName"), rs.getInt("Saldo"),
						rs.getInt("Price")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemList;
	}

	/**
	 * Get Item from the database by Id
	 * 
	 * @param Id Item's Id
	 * @return Item
	 * @throws SQLException
	 */
	public Item getItemFromDBById(int Id) throws SQLException {
		Database database = new Database();
		Item item = null;

		String[] columns = { "*" };
		Object[] params = new Object[1];
		params[0] = Id;

		ResultSet rs = database.select("item", columns, "Item_ID = ?", params);

		while (rs.next()) {
			item = new Item(rs.getInt("Item_ID"), rs.getString("ItemName"), rs.getInt("Saldo"), rs.getInt("Price"));
		}

		return item;
	}

	public ItemLocation getItemLocation() throws SQLException {
		Database database = new Database();
		ItemLocation location = null;
		ResultSet rs = database.selectItemLocationByID(this.itemId.getValue().intValue());
		while (rs.next()) {
			location = new ItemLocation(rs.getString("WarehouseName"), rs.getString("SectionType"),
					rs.getInt("Rack_ID"), rs.getInt("BayNumber"), rs.getInt("LevelNumber"), rs.getInt("BinNumber"));
		}
		
		return location;
	}

	// Getters and Setters

	// Item Id
	public int getItemId() {
		return itemId.get();
	}

	public void setItemId(int itemId) {
		this.itemId.set(itemId);
	}

	public IntegerProperty getItemIdProperty() {
		return itemId;
	}

	// Item name
	public String getItemName() {
		return itemName.get();
	}

	public void setItemName(String itemName) {
		this.itemName.set(itemName);
	}

	public StringProperty getItemNameProperty() {
		return itemName;
	}

	// Item saldo
	public int getItemSaldo() {
		return itemSaldo.get();
	}

	public void setItemSaldo(int itemSaldo) {
		this.itemSaldo.set(itemSaldo);
	}

	public IntegerProperty getItemSaldoProperty() {
		return itemSaldo;
	}

	// Item price
	public double getItemPrice() {
		return itemPrice.get();
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice.set(itemPrice);
	}

	public DoubleProperty getItemPriceProperty() {
		return itemPrice;
	}
	
	// Item supplier name
	public String getItemSupplierName() {
		return itemSupplierName.get();
	}

	public void setItemSupplierName(String itemSupplierName) {
		this.itemSupplierName.set(itemSupplierName);
	}

	public StringProperty getItemSupplierNameProperty() {
		return itemSupplierName;
	}
	
	// Received quantity
	public int getItemReceived() {
		return received.get();
	}

	public void setItemReceived(int received) {
		this.received.set(received);
	}

	public IntegerProperty getItemReceivedProperty() {
		return received;
	}
	
	// Shelved quantity
	public int getItemShelved() {
		return shelved.get();
	}

	public void setItemShelved(int shelved) {
		this.shelved.set(shelved);
	}

	public IntegerProperty getItemShelvedProperty() {
		return shelved;
	}


}




