package fi.metropolia.wms.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
 * @author Mihail Karvanen
 *
 */
public class SupplyOrder {
	
	private IntegerProperty supplyOrderId;
	private StringProperty supplyOrderNumber;
	private IntegerProperty supplierId;
	private StringProperty supplierName;
	private IntegerProperty itemId;
	private StringProperty itemName;
	private DoubleProperty orderAmount;
	private StringProperty dateCreate;
	private StringProperty dateModify;
	private StringProperty contactPerson;
	private IntegerProperty quantity;

	/**
	 * Default SupplyOrder-class constructor
	 * 
	 */
	public SupplyOrder() {

	}

	/**
	 * SupplyOrder-class parametrized constructor
	 * 
	 * @param sectionId
	 * @param sectionType Section name
	 * @param warehouseId
	 */
	public SupplyOrder(int supplyOrderId, int supplierId, String supplierName, String supplyOrderNumber, double orderAmount, String dateCreate, String dateModify, String contactPerson) {
		this.supplyOrderId = new SimpleIntegerProperty(supplyOrderId);
		this.supplierId = new SimpleIntegerProperty(supplierId);
		this.supplierName = new SimpleStringProperty(supplierName);
		this.supplyOrderNumber = new SimpleStringProperty(supplyOrderNumber);
		this.orderAmount = new SimpleDoubleProperty(orderAmount);
		this.dateCreate = new SimpleStringProperty(dateCreate);
		this.dateModify = new SimpleStringProperty(dateModify);
		this.contactPerson = new SimpleStringProperty(contactPerson);
	}

	// Getters and setters

	// Supplier id
	public int getSupplyOrderId() {
		return supplyOrderId.get();
	}

	public void setSupplyOrderId(int supplyOrderId) {
		this.supplyOrderId.set(supplyOrderId);
	}

	public IntegerProperty getSupplyOrderIdProperty() {
		return supplyOrderId;
	}

	// Supply order number
	public String getSupplyOrderNumber() {
		return supplyOrderNumber.get();
	}

	public void setSupplyOrderNumber(String supplyOrderNumber) {
		this.supplyOrderNumber.set(supplyOrderNumber);
	}

	public StringProperty getSupplyOrderNumberProperty() {
		return supplyOrderNumber;
	}

	// Supplier id
	public int getSupplierId() {
		return supplierId.get();
	}

	public void setSupplierId(int supplierId) {
		this.supplierId.set(supplierId);
	}

	public IntegerProperty getSupplierIdProperty() {
		return supplierId;
	}
	
	// Supplier name
	public String getSupplierName() {
		return supplierName.get();
	}

	public void setSupplierName(String supplierName) {
		this.supplierName.set(supplierName);
	}

	public StringProperty getSupplierNameProperty() {
		return supplierName;
	}
	
	// Item id
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
	
	// Supply oreder amount
	public Double getOrderAmount() {
		return orderAmount.get();
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount.set(orderAmount);
	}

	public DoubleProperty getOrderAmountProperty() {
		return orderAmount;
	}
	
	// Date_create
	public String getDateCreate() {
		return dateCreate.get();
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate.set(dateCreate);
	}

	public StringProperty getDateCreateProperty() {
		return dateCreate;
	}
	
	// Date_modify
	public String getDateModify() {
		return dateModify.get();
	}

	public void setDateModify(String dateModify) {
		this.dateModify.set(dateModify);
	}

	public StringProperty getDateModifyProperty() {
		return dateModify;
	}
	
	// Quantity
	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}

	public IntegerProperty getQuantityProperty() {
		return quantity;
	}
	
	// Contact person
	public String getContactPerson() {
		return contactPerson.get();
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson.set(contactPerson);
	}

	public StringProperty getContactPersonProperty() {
		return contactPerson;
	}

//	@Override
//	public String toString() {
//		return sectionId.get() + " - " + sectionType.get();
//	}

	// Database methods

	/**
	 * Method inserts new supply order to the database
	 * 
	 * @param supplyOrderNumber
	 * @param orderAmount
	 * @param dateCreate
	 * @param contactPerson
	 * @return
	 */
	public int addSupplyOrderToDB( int supplierId, String supplyOrderNumber, double orderAmount, Date dateCreate, String contactPerson) {
		Database database = null;

		try {
			database = new Database();
			Object[] params = { null, supplierId, supplyOrderNumber, orderAmount, dateCreate, dateCreate,  contactPerson };
			database.insert("supply_order", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}

		return (int) database.getGeneratedId();
	}

	/**
	 * Method updates Supply Order Item  to the database
	 * 
	 */
	public void updateSupplyOrderItemToDB(int supplyOrderId, int itemId, int received, int shelved) {
		Database database = null;

		try {
			database = new Database();
			String[] columns = { "Received", "Shelved" };
			Object[] params = { received, shelved, supplyOrderId, itemId };
			database.update("supply_order_item", columns, "SupplyOrder_ID = ? AND Item_ID = ?", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
	}

	/**
	 * Method returns ObservableList containing objects of all supply orders
	 * 
	 * 
	 * @return
	 */
	public ObservableList<SupplyOrder> getSupplyOrderObservableListFromDB() {
		ObservableList<SupplyOrder> supplyOrderList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] tables = { "supply_order" };
			String[] columns = { "SupplyOrder_ID", "supply_order.Supplier_ID", "supplier.SupplierName", "SupplyOrderNumber", "OrderAmount", "Date_create", "Date_modify", "ContactPerson"};
			Object[] params = null;
			String join = "supplier ON supplier.Supplier_ID = supply_order.Supplier_ID ";

			ResultSet rs = database.select(tables, columns, "1", params, join);

			while (rs.next()) {
				supplyOrderList.add(new SupplyOrder(rs.getInt("SupplyOrder_ID"), rs.getInt("supply_order.Supplier_ID"), rs.getString("SupplierName"),  rs.getString("SupplyOrderNumber"),
						rs.getDouble("OrderAmount"), rs.getString("Date_create"), rs.getString("Date_modify"), rs.getString("ContactPerson")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return supplyOrderList;

	}
	
	
	/**
	 * Method returns ObservableList containing objects of all supply order's items
	 * 
	 * @param supplyOrderId
	 * @return
	 */
	public ObservableList<Item> getSupplyOrderItemsObservableListFromDB(int supplyOrderId) {
		ObservableList<Item> supplyOrderItemList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] tables = { "supply_order_item" };
			String[] columns = { "Item_ID", "ItemName", "ItemPrice", "ItemQuantity", "received", "shelved", "SupplierName"};
			Object[] params = {supplyOrderId};

			ResultSet rs = database.select(tables, columns, "SupplyOrder_ID = ?", params);

			while (rs.next()) {
				supplyOrderItemList.add(new Item(rs.getInt("Item_ID"), rs.getString("ItemName"), rs.getDouble("ItemPrice"),
						rs.getInt("ItemQuantity"), rs.getInt("Received"), rs.getInt("Shelved"), rs.getString("SupplierName")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return supplyOrderItemList;

	}
	
	/**
	 * Method returns ArrayList containing all supply orders item objects from the DB
	 * 
	 * @return
	 */
	public ArrayList<Item> getSupplyOrderItemsArrayListFromDB(int supplyOrderId) {
		ArrayList<Item> supplyOrderItemArrayList = new ArrayList<Item>();
		Database database;
		try {
			database = new Database();
			String[] tables = { "supply_order_item" };
			String[] columns = { "Item_ID", "ItemName", "ItemPrice", "ItemQuantity", "received", "shelved", "SupplierName"};
			Object[] params = {supplyOrderId};

			ResultSet rs = database.select(tables, columns, "SupplyOrder_ID = ?", params);

			while (rs.next()) {
				supplyOrderItemArrayList.add(new Item(rs.getInt("Item_ID"), rs.getString("ItemName"), rs.getDouble("ItemPrice"),
						rs.getInt("ItemQuantity"), rs.getInt("Received"), rs.getInt("Shelved"), rs.getString("SupplierName")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return supplyOrderItemArrayList;

	}
	
	/**
	 * Method inserts new supply order item to the database
	 * SupplyOrder_ID 	Item_ID 	ItemName 	ItemPrice 	ItemQuantity 	Received 	Shelved 	SupplierName 	
	 * @param supplyOrderId
	 * @param itemId
	 * @param itemAmount
	 * @return
	 */
	public void addSupplyOrderItemToDB(int supplyOrderId, int itemId, String itemName, double itemPrice, int itemQuantity, String supplierName) {
		Database database = null;

		try {
			database = new Database();
			Object[] params = {supplyOrderId, itemId, itemName, itemPrice, itemQuantity, 0, 0, supplierName };
			database.insert("supply_order_item", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
	}
	
	/**
	 * Method updates Supply Order Item  to the database
	 * SupplyOrder_ID 	Supplier_ID 	SupplyOrderNumber 	OrderAmount 	Date_create 	Date_modify 	ContactPerson 	
	 * 
	 */
	public void updateSupplyOrderToDB(int supplyOrderId, double orderAmount, Date dateModified) {
		Database database = null;

		try {
			database = new Database();
			String[] columns = { "OrderAmount", "Date_modify" };
			Object[] params = { orderAmount, dateModified, supplyOrderId};
			database.update("supply_order", columns, "SupplyOrder_ID = ?", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
	}

//	/**
//	 * Method returns ArrayList containing objects of all sections from the DB
//	 * 
//	 * @return
//	 */
//	public ArrayList<Section> getSectionListFromDBByWarehouseId(int warehouseId) {
//		ArrayList<Section> sectionList = new ArrayList<Section>();
//		Database database;
//		try {
//			database = new Database();
//			String[] columns = { "Section_ID", "SectionType", "Warehouse_ID" };
//			Object[] params = { warehouseId };
//
//			ResultSet rs = database.select("section", columns, "Warehouse_ID = ?", params);
//
//			while (rs.next()) {
//				sectionList.add(
//						new Section(rs.getInt("Section_ID"), rs.getString("SectionType"), rs.getInt("Warehouse_ID")));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return sectionList;
//
//	}
//
//	/**
//	 * Method returns ObservableList containing objects of all Sections from the DB
//	 * 
//	 * @return
//	 */
//	public ObservableList<Section> getSectionObservableListFromDBByWarehouseId(int warehouseId) {
//		ObservableList<Section> sectionList = FXCollections.observableArrayList();
//		Database database;
//		try {
//			database = new Database();
//			String[] columns = { "Section_ID", "SectionType", "Warehouse_ID" };
//			Object[] params = { warehouseId };
//
//			ResultSet rs = database.select("section", columns, "Warehouse_ID = ?", params);
//
//			while (rs.next()) {
//				sectionList.add(
//						new Section(rs.getInt("Section_ID"), rs.getString("SectionType"), rs.getInt("Warehouse_ID")));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return sectionList;
//
//	}
//
//	/**
//	 * Method returns warehouse name from DB by Id
//	 * 
//	 * 
//	 * @return
//	 */
//
//	public String getWarehouseNameFromDBById(int warehouseId) {
//
//		String whName = null;
//		Database database;
//		try {
//			database = new Database();
//			String[] columns = { "WarehouseName" };
//			Object[] params = { warehouseId };
//
//			ResultSet rs = database.select("warehouse", columns, "Warehouse_ID = ?", params);
//
//			while (rs.next()) {
//				whName = rs.getString("WarehouseName");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return whName;
//
//	}
//	
//	/**
//	 * Method returns warehouse's name from the database by warehouse Id
//	 * 
//	 * @param warehouseId
//	 * @return
//	 */
//	public String getSectionNameFromDBById(int sectionId) {
//		String sName = null;
//		Database database;
//		try {
//			database = new Database();
//			String[] columns = { "SectionType" };
//			Object[] params = { sectionId };
//
//			ResultSet rs = database.select("section", columns, "Section_ID = ?", params);
//
//			while (rs.next()) {
//				sName = rs.getString("SectionType");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return sName;
//	}
//	
//	/**
//	 * Method returns Section object from the database by the section id
//	 * 
//	 * @return Section-object
//	 */
//
//	public Section getSectionFromDBById(int sectionId) {
//		Section section = null;
//		Database database;
//		try {
//			database = new Database();
//			String[] columns = { "Section_ID", "SectionType", "Warehouse_ID" };
//			Object[] params = { sectionId };
//
//			ResultSet rs = database.select("section", columns, "Section_ID = ?", params);
//
//			while (rs.next()) {
//				section = new Section(rs.getInt("Section_ID"), rs.getString("SectionType"), rs.getInt("Warehouse_ID"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return section;
//
//	}
//
//	/**
//	 * Method removes section from the database by Id
//	 * 
//	 * @param id Section Id
//	 * @return
//	 */
//	public int removeSectionFromDBById(int id) {
//		Database database;
//		int result = 0;
//		try {
//			database = new Database();
//			Object[] params2 = { id };
//			database.delete("section", "Section_ID = ?", params2);
//
//		} catch (SQLException e) {
//			System.out.println("error - " + e.getMessage());
//		}
//
//		return result;
//	}
	
}
