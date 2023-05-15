package fi.metropolia.wms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public abstract class InitDBTest {
	


	// instance of the database connection
	protected static Connection conn;
	// statement instance
	protected PreparedStatement stmt;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		// Luodaan yhteys tietokantaan
		conn = DriverManager.getConnection("Your DB address", "Your username", "Your password");
        System.out.println("TESTAAMINEN ALKAA.");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		// Suljetaan yhteys tietokantaan
		conn.close();
        System.out.println("TESTAAMINEN VALMIS.");
	}

	@BeforeEach
	void setUp() throws Exception {
        System.out.println("Käynnistä uusi testimetodi.");
	}

	@AfterEach
	void tearDown() throws Exception {
        System.out.println("Testimetodi suoritettu.");
	}
}
