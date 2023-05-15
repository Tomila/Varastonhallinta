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
 * Contains warehouse rack's logic
 * 
 * @author Mihail Karvanen
 *
 */

public class Rack {

	private IntegerProperty rackId;
	private IntegerProperty sectionId;
	private IntegerProperty warehouseId;
	private StringProperty warehouseName;
	private StringProperty sectionName;
	
	/**
	 * Default Section-class constructor
	 * 
	 */
	public Rack() {

	}

	/**
	 * Parametrized rack-class constructor
	 * 
	 * @param rackId
	 * @param sectionId
	 * @param warehouseId
	 */
	public Rack(int rackId, int warehouseId, int sectionId) {
		this.rackId = new SimpleIntegerProperty(rackId);
		this.sectionId = new SimpleIntegerProperty(sectionId);
		this.warehouseId = new SimpleIntegerProperty(warehouseId);
	}
	
	/**
	 * Parametrized rack-class constructor
	 * 
	 * @param rackId
	 * @param sectionId
	 * @param warehouseId
	 * @param warehouseName
	 * @param sectionName
	 */
	public Rack(int rackId, int warehouseId, int sectionId, String warehouseName, String sectionName) {
		this.rackId = new SimpleIntegerProperty(rackId);
		this.sectionId = new SimpleIntegerProperty(sectionId);
		this.warehouseId = new SimpleIntegerProperty(warehouseId);
		this.warehouseName = new SimpleStringProperty(warehouseName);
		this.sectionName = new SimpleStringProperty(sectionName);
	}

	// Getters and setters --------------------------------------------//

	// Rack id
	public int getRackId() {
		return rackId.get();
	}

	public void setRackId(int rackId) {
		this.rackId.set(rackId);
	}

	public IntegerProperty getRackIdProperty() {
		return rackId;
	}

	// Section id
	public int getSectionId() {
		return sectionId.get();
	}

	public void setSectionId(int sectionId) {
		this.sectionId.set(sectionId);
	}

	public IntegerProperty getSectionIdProperty() {
		return sectionId;
	}

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

	public void setWarehouseName(String whName) {
		this.warehouseName.set(whName);
	}

	public StringProperty getWarehouseNameProperty() {
		return warehouseName;
	}
	
	// Section name
	public String getSectionName() {
		return sectionName.get();
	}

	public void setSectionName(String sName) {
		this.sectionName.set(sName);
	}

	public StringProperty getSectionNameProperty() {
		return sectionName;
	}
	
	
	@Override
	public String toString() {
		return Integer.toString(rackId.get());
	}
	
	// Database methods ---------------------------------------------- //

	/**
	 * Method inserts new rack into the database
	 * 
	 * @param warehouseId
	 * @param sectionId
	 * @return Generated rack's id in the database
	 */
	public int addRackToDB(int warehouseId, int sectionId) {
		Database database = null;

		try {
			database = new Database();
			Object[] params = { null, warehouseId, sectionId };
			database.insert("rack", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
		return (int) database.getGeneratedId();
	}
	
	
	/**
	 * Method inserts rack levels into the database
	 * 
	 * @param rackId 
	 * @param amount Amount of levels to add
	 * @return 
	 */
	public void addRackLevelsToDB(int rackId, int amount) {
		Database database = null;

		try {
			database = new Database();
			for(int i=1; i<=amount; i++) {
				Object[] params = { null, rackId, i };
				database.insert("level", params);
			}
		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
	}
	/**
	 * Method inserts rack columns into the database
	 * 
	 * @param rackId 
	 * @param amount Amount of columns to add
	 * @return 
	 */
	public void addRackColumnsToDB(int rackId, int amount) {
		Database database = null;

		try {
			database = new Database();
			for(int i=1; i<=amount; i++) {
				Object[] params = { null, rackId, i };
				database.insert("bay", params);
			}
		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
	}
	/**
	 * Method inserts new rack into the database
	 * 
	 * @param rackId
	 * @param levels
	 * @param columns
	 * @return Generated rack's id in the database
	 */

	public void addRackBinsToDB(int rackId, int levels, int columns) {
		Database database = null;

		try {
			database = new Database();
			int[] bayId = new int[columns];

			for (int i = 1; i <= columns; i++) {
				Object[] params1 = { null, rackId, i };
				database.insert("bay", params1);
				bayId[i - 1] = (int) database.getGeneratedId();
			}

			for (int i = 1; i <= levels; i++) {
				Object[] params2 = { null, rackId, i };
				database.insert("level", params2);
				int levelId = (int) database.getGeneratedId();
				for (int j = 1; j <= bayId.length; j++) {
					for (int k = 1; k <= 3; k++) {
						Object[] params3 = { null, rackId, bayId[j - 1], levelId, k };
						database.insert("bin", params3);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
	}
	
	/**
	 * Method updates rack to the database
	 * 
	 * @param rackId
	 * @param sectionId
	 * @param warehouseId
	 * @return
	 */
	public int updateRackToDB(int rackId, int warehouseId, int sectionId) {
		Database database = null;

		try {
			database = new Database();
			String[] columns = { "Rack_ID", "Warehouse_ID", "Section_ID" };
			Object[] params = { rackId, warehouseId, sectionId, rackId };
			database.update("rack", columns, "Rack_ID = ?", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
		return (int) database.getGeneratedId();
	}
	
	
	/**
	 * Method returns an ObservableList containing all the Rack-objects from the Database
	 * 
	 * @return
	 */
	public ObservableList<Rack> getRackObservableListFromDB() {
		ObservableList<Rack> rackList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Rack_ID", "Warehouse_ID", "Section_ID" };
			Object[] params = null;

			ResultSet rs = database.select("rack", columns, "1", params);

			while (rs.next()) {
				rackList.add(new Rack(rs.getInt("Rack_ID"), rs.getInt("Warehouse_ID"), rs.getInt("Section_ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rackList;
	}
	
	/**
	 * Method returns an ObservableList containing all the Rack-objects from the Database  by given warehouse and section Id
	 * 
	 * @return
	 */
	public ObservableList<Rack> getRackObservableListFromDBByWarehouseAndSection(int warehouseId, int sectionId) {
		ObservableList<Rack> rackList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Rack_ID", "Warehouse_ID", "Section_ID" };
			Object[] params = {warehouseId, sectionId};

			ResultSet rs = database.select("rack", columns, "Warehouse_ID = ? AND Section_ID = ?", params);

			while (rs.next()) {
				rackList.add(new Rack(rs.getInt("Rack_ID"), rs.getInt("Warehouse_ID"), rs.getInt("Section_ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rackList;
	}
	
	/**
	 * Method removes rack from the database by Id
	 * 
	 * @param rackId
	 * @return Returns number of affected row in the database 
	 */
	public int removeRackFromDBById(int id) {
		Database database;
		int result = 0;
		try {
			database = new Database();
			Object[] params2 = { id };
			database.delete("rack", "Rack_ID = ?", params2);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * Method returns warehouse name from DB by Id
	 * 
	 * 
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
	 * Method returns section's name from DB by Id
	 * 
	 * 
	 * @return
	 */

	public String getSectionNameFromDBById(int sectionId) {

		String sName = null;
		Database database;
		try {
			database = new Database();
			String[] columns = { "SectionName" };
			Object[] params = { sectionId };

			ResultSet rs = database.select("section", columns, "Section_ID = ?", params);

			while (rs.next()) {
				sName = rs.getString("SectionName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sName;
	}
}

