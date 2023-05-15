package fi.metropolia.wms;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fi.metropolia.wms.model.Section;
import fi.metropolia.wms.model.Warehouse;


class TestSectionClassMethods extends InitDBTest{
	
	// Warehouse-class instance
	private Section section = null;
	private Warehouse warehouse = new Warehouse();
	int newWarehouseId = warehouse.addWarehouseToDB("Test warehouse");
	
	// Database query
	String queryString;
	
	@BeforeEach
	void setUp() throws Exception {
		// New Warehouse-class instance
		section = new Section();
        System.out.println("Käynnistetään uusi Section-class testimetodi.");
	}

	@Test
	@DisplayName("Testataan addSectionToDB(String sectionType) - method")
	
	void addWarehouseToDBTest() throws SQLException{
		String sectionType = "Test section name";
		int newSectionId = 0;
		
		newSectionId = section.addSectionToDB(sectionType, newWarehouseId);
		
		queryString = "SELECT * FROM section WHERE Section_ID = " + newSectionId;
		
		stmt = conn.prepareStatement(queryString);

		ResultSet result = stmt.executeQuery();
		result.next();
		assertEquals(newSectionId, result.getInt(1),"Lisätty ID täsmää");
		assertEquals(sectionType, result.getString(2),"Lisätty nimi täsmää");
		
		
		// Poistetaan lisätty varasto
		section.removeSectionFromDBById(newSectionId);
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
	
	void updateSectionToDBTest() throws SQLException{
		String sectionType = "Test section name";
		int newSectionId = 0;

		
		newSectionId = section.addSectionToDB(sectionType, newWarehouseId);
		
		section.updateSectionToDB(newSectionId, "Test updateSectionName", newWarehouseId);
		
		queryString = "SELECT * FROM section WHERE Section_ID = " + newSectionId;
		
		stmt = conn.prepareStatement(queryString);

		ResultSet result = stmt.executeQuery();
		result.next();
		assertEquals(newSectionId, result.getInt(1),"Lisätty ID täsmää");
		assertEquals("Test updateSectionName", result.getString(2),"Päivitetty nimi täsmää");
		
		
		// Poistetaan lisätty varasto
		section.removeSectionFromDBById(newSectionId);
		warehouse.removeWarehouseFromDBById(newWarehouseId);
	}
	
	/**
	 * Testing getWarehouseNameFromDBById(int warehouseId) - method
	 * 
	 */
	@Test
	@DisplayName("Testataan getWarehouseNameFromDBById(int warehouseId) - method")
	
	void getSectionNameFromDBByIdTest() throws SQLException{
		String sectionName = "Test section name";
		int newSectionId = 0;
		String sectionDbName = "";

		newSectionId = section.addSectionToDB(sectionName, newWarehouseId);
		
		sectionDbName = section.getSectionNameFromDBById(newSectionId);
		
		assertEquals(sectionName, sectionDbName,"Nimet täsmää");
		
		section.removeSectionFromDBById(newSectionId);
		warehouse.removeWarehouseFromDBById(newWarehouseId);
	}


}
