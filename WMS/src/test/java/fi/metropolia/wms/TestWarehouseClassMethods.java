package fi.metropolia.wms;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fi.metropolia.wms.model.Warehouse;


class TestWarehouseClassMethods extends InitDBTest{
	
	// Warehouse-class instance
	private Warehouse warehouse = null;
	
	// Database query
	String queryString;
	
	@Override
	@BeforeEach
	void setUp() throws Exception {
		// New Warehouse-class instance
		warehouse = new Warehouse();
        System.out.println("Käynnistetään uusi Warehouse-class testimetodi.");
	}

	@Test
	@DisplayName("Testataan addWarehouseToDB(String warehouseName) - method")
	
	void addWarehouseToDBTest() throws SQLException{
		String warehouseName = "Test warehouse name";
		int newWarehouseId = 0;

		
		newWarehouseId = warehouse.addWarehouseToDB(warehouseName);
		
		queryString = "SELECT * FROM warehouse WHERE Warehouse_ID = " + newWarehouseId;
		
		stmt = conn.prepareStatement(queryString);

		ResultSet result = stmt.executeQuery();
		result.next();
		assertEquals(newWarehouseId, result.getInt(1),"Lisätty ID täsmää");
		assertEquals(warehouseName, result.getString(2),"Lisätty nimi täsmää");
		
		
		// Poistetaan lisätty varasto
		warehouse.removeWarehouseFromDBById(newWarehouseId);
		
	}
	
	
	/**
	 * Updating warehouse test
	 * 
	 * @param warehouseName
	 * @param warehouseId
	 * @return Databases generated id
	 */
	
	@Test
	@DisplayName("Testataan updateWarehouseToDB(int id, String warehouseName) - method")
	
	void updateWarehouseToDBTest() throws SQLException{
		String warehouseName = "Test warehouse name";
		int newWarehouseId = 0;

		
		newWarehouseId = warehouse.addWarehouseToDB(warehouseName);
		
		warehouse.updateWarehouseToDB(newWarehouseId, "Test updateWarehouseName");
		
		queryString = "SELECT * FROM warehouse WHERE Warehouse_ID = " + newWarehouseId;
		
		stmt = conn.prepareStatement(queryString);

		ResultSet result = stmt.executeQuery();
		result.next();
		assertEquals(newWarehouseId, result.getInt(1),"Lisätty ID täsmää");
		assertEquals("Test updateWarehouseName", result.getString(2),"Päivitetty nimi täsmää");
		
		
		// Poistetaan lisätty varasto
		warehouse.removeWarehouseFromDBById(newWarehouseId);
	}
	
	/**
	 * Testing getWarehouseNameFromDBById(int warehouseId) - method
	 * 
	 */
	@Test
	@DisplayName("Testataan getWarehouseNameFromDBById(int warehouseId) - method")
	
	void getWarehouseNameFromDBByIdTest() throws SQLException{
		String warehouseName = "Test warehouse name";
		int newWarehouseId = 0;
		String warehouseDbName = "";

		newWarehouseId = warehouse.addWarehouseToDB(warehouseName);
		
		warehouseDbName = warehouse.getWarehouseNameFromDBById(newWarehouseId);
		
		assertEquals(warehouseName, warehouseDbName,"Nimet täsmää");
		
		warehouse.removeWarehouseFromDBById(newWarehouseId);
	}


}
