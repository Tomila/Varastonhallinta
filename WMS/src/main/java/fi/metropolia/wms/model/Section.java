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
 * Contains warehouse section logic.
 * 
 * @author Mihail Karvanen
 *
 */

public class Section {

	private IntegerProperty sectionId;
	private IntegerProperty warehouseId;
	private StringProperty sectionType;
	private StringProperty warehouseName;

	/**
	 * Default Section-class constructor
	 * 
	 */
	public Section() {

	}

	/**
	 * Section-class constructor with only Section parameters
	 * 
	 * @param sectionId
	 * @param sectionType Section name
	 * @param warehouseId
	 */
	public Section(int sectionId, String sectionType, int warehouseId) {
		this.sectionId = new SimpleIntegerProperty(sectionId);
		this.sectionType = new SimpleStringProperty(sectionType);
		this.warehouseId = new SimpleIntegerProperty(warehouseId);
	}

	/**
	 * Section-class constructor with section's warehouse Name
	 * 
	 * @param sectionId
	 * @param sectionType   Section name
	 * @param warehouseId
	 * @param warehouseName
	 */
	public Section(int sectionId, String sectionType, int warehouseId, String warehouseName) {
		this.sectionId = new SimpleIntegerProperty(sectionId);
		this.sectionType = new SimpleStringProperty(sectionType);
		this.warehouseId = new SimpleIntegerProperty(warehouseId);
		this.warehouseName = new SimpleStringProperty(warehouseName);

	}

	// Getters and setters

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

	// Section type
	public String getSectionType() {
		return sectionType.get();
	}

	public void setSectionType(String sectionType) {
		this.sectionType.set(sectionType);
	}

	public StringProperty getSectionTypeProperty() {
		return sectionType;
	}

	// Warehouse id
	public int getSectionWarehouseId() {
		return warehouseId.get();
	}

	public void setSectionWarehouseId(int warehouseId) {
		this.warehouseId.set(warehouseId);
	}

	public IntegerProperty getSectionWarehouseIdProperty() {
		return warehouseId;
	}

	// Warehouse name
	public String getSectionWarehouseName() {
		return warehouseName.get();
	}

	public void setSectionWarehouseName(String whName) {
		this.warehouseName.set(whName);
	}

	public StringProperty getSectionWarehouseNameProperty() {
		return warehouseName;
	}

	@Override
	public String toString() {
		return sectionId.get() + " - " + sectionType.get();
	}

	// Database methods

	/**
	 * Method inserts new section to the database
	 * 
	 * @param sectionType
	 * @param warehouseId
	 * @return
	 */
	public int addSectionToDB(String sectionType, int warehouseId) {
		Database database = null;

		try {
			database = new Database();
			Object[] params = { null, sectionType, warehouseId };
			database.insert("section", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}

		return (int) database.getGeneratedId();
	}

	/**
	 * Method updates section to the database
	 * 
	 * @param sectionType Section name
	 * @param sectionId
	 * @param warehouseId
	 * @return
	 */
	public int updateSectionToDB(int sectionId, String sectionType, int warehouseId) {
		Database database = null;

		try {
			database = new Database();
			String[] columns = { "Section_ID", "SectionType", "Warehouse_ID" };
			Object[] params = { sectionId, sectionType, warehouseId, sectionId };
			database.update("section", columns, "Section_ID = ?", params);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}
		return (int) database.getGeneratedId();
	}

	/**
	 * Method returns ObservableList containing objects of all sections with their
	 * warehouse names from the DB
	 * 
	 * SELECT Section_ID, SectionType, section.Warehouse_ID, warehouse.WarehouseName
	 * FROM `section`, `warehouse` WHERE section.Warehouse_ID =
	 * warehouse.Warehouse_ID ;
	 * 
	 * @return
	 */
	public ObservableList<Section> getSectionObservableListFromDB() {
		ObservableList<Section> sectionList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] tables = { "section", "warehouse" };
			String[] columns = { "Section_ID", "SectionType", "section.Warehouse_ID", "warehouse.Warehouse_ID",
					"warehouse.WarehouseName" };
			Object[] params = { "warehouse.Warehouse_ID" };

			ResultSet rs = database.select(tables, columns, "section.Warehouse_ID = warehouse.Warehouse_ID", params);

			while (rs.next()) {
				sectionList.add(new Section(rs.getInt("Section_ID"), rs.getString("SectionType"),
						rs.getInt("Warehouse_ID"), rs.getString("WarehouseName")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sectionList;

	}

	/**
	 * Method returns ArrayList containing objects of all sections from the DB
	 * 
	 * @return
	 */
	public ArrayList<Section> getSectionListFromDBByWarehouseId(int warehouseId) {
		ArrayList<Section> sectionList = new ArrayList<Section>();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Section_ID", "SectionType", "Warehouse_ID" };
			Object[] params = { warehouseId };

			ResultSet rs = database.select("section", columns, "Warehouse_ID = ?", params);

			while (rs.next()) {
				sectionList.add(
						new Section(rs.getInt("Section_ID"), rs.getString("SectionType"), rs.getInt("Warehouse_ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sectionList;

	}

	/**
	 * Method returns ObservableList containing objects of all Sections from the DB
	 * 
	 * @return
	 */
	public ObservableList<Section> getSectionObservableListFromDBByWarehouseId(int warehouseId) {
		ObservableList<Section> sectionList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Section_ID", "SectionType", "Warehouse_ID" };
			Object[] params = { warehouseId };

			ResultSet rs = database.select("section", columns, "Warehouse_ID = ?", params);

			while (rs.next()) {
				sectionList.add(
						new Section(rs.getInt("Section_ID"), rs.getString("SectionType"), rs.getInt("Warehouse_ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sectionList;

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
	 * Method returns warehouse's name from the database by warehouse Id
	 * 
	 * @param warehouseId
	 * @return
	 */
	public String getSectionNameFromDBById(int sectionId) {
		String sName = null;
		Database database;
		try {
			database = new Database();
			String[] columns = { "SectionType" };
			Object[] params = { sectionId };

			ResultSet rs = database.select("section", columns, "Section_ID = ?", params);

			while (rs.next()) {
				sName = rs.getString("SectionType");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sName;
	}
	
	/**
	 * Method returns Section object from the database by the section id
	 * 
	 * @return Section-object
	 */

	public Section getSectionFromDBById(int sectionId) {
		Section section = null;
		Database database;
		try {
			database = new Database();
			String[] columns = { "Section_ID", "SectionType", "Warehouse_ID" };
			Object[] params = { sectionId };

			ResultSet rs = database.select("section", columns, "Section_ID = ?", params);

			while (rs.next()) {
				section = new Section(rs.getInt("Section_ID"), rs.getString("SectionType"), rs.getInt("Warehouse_ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return section;

	}

	/**
	 * Method removes section from the database by Id
	 * 
	 * @param id Section Id
	 * @return
	 */
	public int removeSectionFromDBById(int id) {
		Database database;
		int result = 0;
		try {
			database = new Database();
			Object[] params2 = { id };
			database.delete("section", "Section_ID = ?", params2);

		} catch (SQLException e) {
			System.out.println("error - " + e.getMessage());
		}

		return result;
	}

}

