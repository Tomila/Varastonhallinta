package fi.metropolia.wms.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fi.metropolia.wms.db.Database;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * Contains warehouse logic.
 * 
 * @author Mihail Karvanen
 *
 */

public class Warehouse {

	private StringProperty warehouseName;
	private IntegerProperty warehouseId;

	/**
	 * Default warehouse-class constructor
	 * 
	 */
	public Warehouse() {

	}

	/**
	 * Warehouse-class constructor
	 * 
	 * @param warehouseName Warehouse name
	 */
	public Warehouse(int warehouseId, String warehouseName) {
		this.warehouseId = new SimpleIntegerProperty(warehouseId);
		this.warehouseName = new SimpleStringProperty(warehouseName);
	}

	// Getters and setters ---------------------------------------------------- //

	// Warehouse id
	public int getWarehouseId() {
		return warehouseId.get();
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId.set(warehouseId);
	}

	public IntegerProperty getWarehouseIdProperty() {
		return warehouseId;
	}

	// Warehouse name
	public String getWarehouseName() {
		return warehouseName.get();
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName.set(warehouseName);
	}

	public StringProperty getWarehouseNameProperty() {
		return warehouseName;
	}

	// Database methods ----------------------------------------------------- //

	/**
	 * Method inserts new warehouse to the database
	 * 
	 * @param warehouseName
	 * @return Database generated id
	 */
	public int addWarehouseToDB(String warehouseName) {
		Database database = null;

		try {
			database = new Database();
			Object[] params = { null, warehouseName };
			database.insert("warehouse", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
		return (int) database.getGeneratedId();
	}

	/**
	 * Method updates an existing warehouse in the database
	 * 
	 * @param warehouseName
	 * @param warehouseId
	 * @return Databases generated id
	 */
	public int updateWarehouseToDB(int id, String warehouseName) {
		Database database = null;
		int result = 0;
		try {
			database = new Database();
			String[] columns = { "WarehouseName" };
			Object[] params = {warehouseName, id };
			database.update("warehouse", columns, "Warehouse_ID = ?", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
		System.out.println(result);
		return (int) database.getGeneratedId();
	}

	/**
	 * Method returns ArrayList containing all warehouses objects from the DB
	 * 
	 * @return
	 */
	public ArrayList<Warehouse> getWarehouseListFromDB() {
		ArrayList<Warehouse> warehouseList = new ArrayList<Warehouse>();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Warehouse_ID", "WarehouseName" };
			Object[] params = null;

			ResultSet rs = database.select("warehouse", columns, "1", params);

			while (rs.next()) {
				warehouseList.add(new Warehouse(rs.getInt("Warehouse_ID"), rs.getString("WarehouseName")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return warehouseList;

	}

	/**
	 * Method returns an ObservableList containing all Warehouses objects from the Database
	 * 
	 * @return
	 */
	public ObservableList<Warehouse> getWarehouseObservableListFromDB() {
		ObservableList<Warehouse> warehouseList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Warehouse_ID", "WarehouseName" };
			Object[] params = null;

			ResultSet rs = database.select("warehouse", columns, "1", params);

			while (rs.next()) {
				warehouseList.add(new Warehouse(rs.getInt("Warehouse_ID"), rs.getString("WarehouseName")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return warehouseList;

	}

	/**
	 * Method removes warehouse from the database by Id
	 * 
	 * @param id Warehouse Id
	 * @return
	 */
	public int removeWarehouseFromDBById(int id) {
		Database database;
		int result = 0;
		try {
			database = new Database();
			Object[] params2 = { id };
			database.delete("warehouse", "Warehouse_ID = ?", params2);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}

		return result;
	}

	/**
	 * Method returns warehouse's name from the database by warehouse Id
	 * 
	 * @param warehouseId
	 * @return
	 */
	public String getWarehouseNameFromDBById(int warehouseId) {
		String whName = null;
		Database database;
		try {
			database = new Database();
			String[] columns = { "WarehouseName" };
			Object[] params = { warehouseId };

			ResultSet rs = database.select("warehouse", columns, "Warehouse_ID = ?", params);

			while (rs.next()) {
				whName = rs.getString("WarehouseName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return whName;
	}

	/**
	 * Method returns warehouse object from the database by the warehouse id
	 * 
	 * @return
	 */

	public Warehouse getWarehouseFromDBById(int warehouseId) {
		Warehouse warehouse = null;
		Database database;
		try {
			database = new Database();
			String[] columns = { "Warehouse_ID", "WarehouseName" };
			Object[] params = { warehouseId };

			ResultSet rs = database.select("warehouse", columns, "Warehouse_ID = ?", params);

			while (rs.next()) {
				warehouse = new Warehouse(rs.getInt("Warehouse_ID"), rs.getString("WarehouseName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return warehouse;

	}

	@Override
	public String toString() {
		return warehouseId.get() + " - " + warehouseName.get();
	}

}

