package fi.metropolia.wms.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import fi.metropolia.wms.db.Database;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * Contains the Rack's bin logic.
 * 
 * @author Mihail Karvanen
 *
 */

public class Bin {

	private IntegerProperty binId;
	private IntegerProperty rackId;
	private IntegerProperty bayId;
	private IntegerProperty levelId;
	private IntegerProperty binNumber;
	
	/**
	 * Default costructor
	 */
	public Bin() {
		
	}
	
	/**
	 * Parametrized constructor
	 * 
	 * @param binId
	 * @param rackId
	 * @param bayId
	 * @param levelId
	 * @param binNumber
	 */
	
	public Bin(int binId, int rackId, int bayId, int levelId, int binNumber) {
		this.binId = new SimpleIntegerProperty(binId);
		this.rackId = new SimpleIntegerProperty(rackId);
		this.bayId = new SimpleIntegerProperty(bayId);
		this.levelId = new SimpleIntegerProperty(levelId);
		this.binNumber = new SimpleIntegerProperty(binNumber);
	}
	
	// Getters and setters

	// Bin id
	public int getBinId() {
		return binId.get();
	}
	public void setBinId(int binId) {
		this.binId.set(binId);
	}
	public IntegerProperty getBinIdProperty() {
		return binId;
	}
	
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
	
	// Bay id
	public int getBayId() {
		return bayId.get();
	}
	public void setBayId(int bayId) {
		this.bayId.set(bayId);
	}
	public IntegerProperty getBayIdProperty() {
		return bayId;
	}
	
	// Level id
	public int getLevelId() {
		return levelId.get();
	}
	public void setlevelId(int levelId) {
		this.levelId.set(levelId);
	}
	public IntegerProperty getLevelIdProperty() {
		return levelId;
	}
	
	// Bin number
	public int getBinNumber() {
		return binNumber.get();
	}
	public void setBinNumber(int binNumber) {
		this.binNumber.set(binNumber);
	}
	public IntegerProperty getBInNumberProperty() {
		return binNumber;
	}
	
	@Override
	public String toString() {
		return bayId.get() + " - " + levelId.get() + " - " + binNumber.get();
	}

	// Database methods
	
	/**
	 * Method returns ObservableList containing objects of all bin available
	 * 
	 * @return
	 */
	public ObservableList<Bin> getBinObservableListFromDB() {
		ObservableList<Bin> binList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] tables = { "bin" };
			String[] columns = { "Bin_ID", "Rack_ID", "Bay_ID", "Level_ID", "BinNumber" };
			Object[] params = null;

			ResultSet rs = database.select(tables, columns, "1", params);

			while (rs.next()) {
				binList.add(new Bin(rs.getInt("Bin_ID"), rs.getInt("Rack_ID"), rs.getInt("Bay_ID"), rs.getInt("Level_ID"), rs.getInt("BinNumber")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return binList;

	}
	
	/**
	 * Method returns ObservableList containing objects of all bin available on a given rack
	 * 
	 * @return
	 */
	public ObservableList<Bin> getBinObservableListFromDB(int rackId) {
		ObservableList<Bin> binList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] tables = { "bin" };
			String[] columns = { "Bin_ID", "Rack_ID", "Bay_ID", "Level_ID", "BinNumber" };
			Object[] params = { rackId };

			ResultSet rs = database.select(tables, columns, "Rack_ID = ?", params);

			while (rs.next()) {
				binList.add(new Bin(rs.getInt("Bin_ID"), rs.getInt("Rack_ID"), rs.getInt("Bay_ID"), rs.getInt("Level_ID"), rs.getInt("BinNumber")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return binList;

	}
	
}
