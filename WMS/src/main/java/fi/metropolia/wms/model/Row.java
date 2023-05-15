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

public class Row {
	
//	private IntegerProperty rowId;
//	private IntegerProperty warehouseId;
//	private IntegerProperty sectionId;
//	private StringProperty rowName;
//
//	
//	/**
//	 * Default Row-class constructor
//	 * 
//	 */
//	public Row() {
//
//	}
//	
//	/**
//	 * Row-class constructor with parameters
//	 * @param sectionType Section name
//	 */
//	public Row(int rowId, String rowName, int warehouseId, int sectionId) {
//		this.rowId = new SimpleIntegerProperty(rowId);
//		this.rowName = new SimpleStringProperty(rowName);
//		this.sectionId = new SimpleIntegerProperty(sectionId);
//		this.warehouseId = new SimpleIntegerProperty(warehouseId);
//	}
//	
//	// Getters and setters
//
//	// Row id
//	public int getRowId() {
//		return rowId.get();
//	}
//	public void setRowId(int rowId) {
//		this.rowId.set(rowId);
//	}
//	public IntegerProperty getRowIdProperty() {
//		return rowId;
//	}
//	
//	// Row name
//	public String getRowName() {
//		return rowName.get();
//	}
//	public void setRowName(String rowName) {
//		this.rowName.set(rowName);
//	}
//	public StringProperty getRowNameProperty() {
//		return rowName;
//	}
//	
//	// Section id
//	public int getSectionId() {
//		return sectionId.get();
//	}
//	public void setSectionId(int sectionId) {
//		this.sectionId.set(sectionId);
//	}
//	public IntegerProperty getSectionIdProperty() {
//		return sectionId;
//	}
//	
//	// Warehouse id
//	public int getWarehouseId() {
//		return warehouseId.get();
//	}
//	public void setWarehouseId(int warehouseId) {
//		this.sectionId.set(warehouseId);
//	}
//	public IntegerProperty getWarehouseIdProperty() {
//		return warehouseId;
//	}
//	
//	// Database methods
//	
//	
//	/**
//	 * Method inserts new section to the database
//	 * @param rowName
//	 * @param warehouseId
//	 * @param sectionId
//	 * @return
//	 */
//	public int addRowToDB(String rowName, int warehouseId, int sectionId) {
//		Database database;
//		int result = 0;
//		try {
//			database = new Database();
//	        Object[] params = {null, rowName, warehouseId, sectionId};
//	        result = database.insert("row", params);
//	        
//		} catch (SQLException e) {
//			System.out.println("error - "+ e.getMessage());
//		}
//		 
//        return result;
//	}
//	
//	/**
//	 * Method returns ArrayList containing objects of all sections from the DB
//	 * @return
//	 */
//	public ArrayList<Row> getRowListFromDBBySectionId(int sectionId){
//		ArrayList<Row> rowList = new ArrayList<Row>();
//		Database database;
//		try {
//			database = new Database();
//	        String[] columns = {"Row_ID", "RowName", "Warehouse_ID", "Section_ID"};
//	        Object[] params = {sectionId};
//
//	            ResultSet rs = database.select("row", columns, "Section_ID = ?", params );
//
//	            while(rs.next()) {	
//	            	rowList.add(new Row(rs.getInt("Row_ID"), rs.getString("RowName"), rs.getInt("Warehouse_ID"), rs.getInt("Section_ID")));
//	            }
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return rowList;
//		
//	}
//	
//	/**
//	 * Method returns ObservableList containing objects of all Sections from the DB
//	 * @return
//	 */
//	public ObservableList<Row> getItemListFromDBBySectionId(int sectionId){
//		ObservableList<Row> rowList = FXCollections.observableArrayList();
//		Database database;
//		try {
//			database = new Database();
//	        String[] columns = {"Row_ID", "RowName", "Warehouse_ID", "Section_ID"};
//	        Object[] params = {sectionId};
//
//	            ResultSet rs = database.select("row", columns, "Section_ID = ?", params );
//
//	            while(rs.next()) {	
//	            	rowList.add(new Row(rs.getInt("Row_ID"), rs.getString("RowName"), rs.getInt("Warehouse_ID"), rs.getInt("Section_ID")));
//	            }
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return rowList;
//		
//	}

}
