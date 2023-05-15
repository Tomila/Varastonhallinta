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


public class ItemLocation {
	private StringProperty warehouseName;
	private StringProperty sectionType;
	private IntegerProperty rackID;
	private IntegerProperty bayNumber;
	private IntegerProperty levelNumber;
	private IntegerProperty binNumber;
	
	/**
	 * Default constructor
	 */
	
	public ItemLocation() {
		
	}
	
	/**
	 * Parametrized constructor
	 * 
	 * @param warehouseName
	 * @param sectionType
	 * @param rackID
	 * @param bayNumber
	 * @param levelNumber
	 * @param binNumber
	 */

	public ItemLocation(String warehouseName, String sectionType, int rackID, int bayNumber, int levelNumber, int binNumber) {
		this.warehouseName = new SimpleStringProperty(warehouseName);
		this.sectionType = new SimpleStringProperty(sectionType);
		this.rackID = new SimpleIntegerProperty(rackID);
		this.binNumber = new SimpleIntegerProperty(binNumber);
		this.levelNumber = new SimpleIntegerProperty(levelNumber);
		this.bayNumber = new SimpleIntegerProperty(bayNumber);
	}
	
	
//	public ItemLocation(int bayNumber, int levelNumber, int binNumber) {
//		this.bayNumber = new SimpleIntegerProperty(bayNumber);
//		this.levelNumber = new SimpleIntegerProperty(levelNumber);
//		this.binNumber = new SimpleIntegerProperty(binNumber);
//	}
	
	public StringProperty getWarehouseName() {
		return warehouseName;
	}

	public StringProperty getSectionType() {
		return sectionType;
	}

	public IntegerProperty getRackID() {
		return rackID;
	}


	public IntegerProperty getBayNumber() {
		return bayNumber;
	}


	public IntegerProperty getLevelNumber() {
		return levelNumber;
	}

	public IntegerProperty getBinNumber() {
		return binNumber;
	}
	
	// DB - Methods
	
	
}

