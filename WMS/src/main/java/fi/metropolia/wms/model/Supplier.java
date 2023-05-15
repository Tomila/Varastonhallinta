package fi.metropolia.wms.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import fi.metropolia.wms.db.Database;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Handles product suppliers logic.
 * 
 * @author Mihail Karvanen
 *
 */

public class Supplier {

	private IntegerProperty supplierId;
	private StringProperty supplierName;
	private StringProperty supplierAddr;
	private StringProperty supplierDescr;
	
	// Getters and Setters

		// Supplier Id
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
		
		// Supplier address
		public String getSupplierAddr() {
			return supplierAddr.get();
		}
		public void setSupplierAddr(String supplierAddr) {
			this.supplierAddr.set(supplierAddr);
		}
		public StringProperty getSupplierAddrProperty() {
			return supplierAddr;
		}
		
		// Supplier description
		public String getSupplierDescr() {
			return supplierDescr.get();
		}
		public void setSupplierDescr(String supplierDescr) {
			this.supplierDescr.set(supplierDescr);
		}
		public StringProperty getSupplierDescrProperty() {
			return supplierDescr;
		}
		
		
		/**
		 * Default constructor
		 * 
		 */
		public Supplier() {

		}
		
		/**
		 * Parametrized constructor
		 *  
		 * @param itemId
		 * @param supplierName
		 * @param supplierAddr
		 * @param supplierDescr
		 * 
		 */
		public Supplier(int supplierId, String supplierName, String supplierAddr, String supplierDescr) {
			this.supplierId = new SimpleIntegerProperty(supplierId);
			this.supplierName = new SimpleStringProperty(supplierName);
			this.supplierAddr = new SimpleStringProperty(supplierAddr);
			this.supplierDescr = new SimpleStringProperty(supplierDescr);
		}
		
		@Override
		public String toString() {
			return supplierName.get();
		}
		
		// Database methods

		/**
		 * Method returns ObservableList containing objects of all suppliers from the DB
		 * 
		 * @return
		 */
		public ObservableList<Supplier> getSupplierObservableListFromDB() {
			ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
			Database database;
			try {
				database = new Database();
				String[] columns = { "Supplier_ID", "SupplierName", "SupplierAddr", "SupplierDescr" };
				Object[] params = null;

				ResultSet rs = database.select("supplier", columns, "1", params);

				while (rs.next()) {
					supplierList.add(new Supplier(rs.getInt("Supplier_ID"), rs.getString("SupplierName"), rs.getString("SupplierAddr"),
							rs.getString("SupplierDescr")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return supplierList;
		}
		
		/**
		 * Method returns ObservableList containing supplier item objects 
		 * 
		 * @return
		 */
		public ObservableList<Item> getSupplierItemsObservableListFromDB(int supplierId){
			ObservableList<Item> supplierItemList = FXCollections.observableArrayList();
			Database database;
			try {
				database = new Database();
				String[] tables = { "item" };
				String[] columns = { "Item_ID", "ItemName", "Price"};
				Object[] params = {supplierId};

				ResultSet rs = database.select(tables, columns, "Supplier_ID = ?", params);

				while (rs.next()) {
					supplierItemList.add(new Item(rs.getInt("Item_ID"), rs.getString("ItemName"), rs.getDouble("Price")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return supplierItemList;
		}
		
		/**
		 * Method returns supplier object from the database by the supplier id
		 * 
		 * @return
		 */

		public Supplier getSupplierFromDBById(int supplierId) {
			Supplier supplier = null;
			Database database;
			try {
				database = new Database();
				String[] columns = { "Supplier_ID", "SupplierName", "SupplierAddr", "SupplierDescr"};
				Object[] params = { supplierId };

				ResultSet rs = database.select("supplier", columns, "Supplier_ID = ?", params);

				while (rs.next()) {
					supplier = new Supplier(rs.getInt("Supplier_ID"), rs.getString("SupplierName"), rs.getString("SupplierAddr"), rs.getString("SupplierDescr"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return supplier;

		}

	
}
