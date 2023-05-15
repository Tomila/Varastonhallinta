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
 * Contains language logic.
 * 
 * @author Mihail Karvanen
 *
 */

public class Language {

	private StringProperty languageName;
	private IntegerProperty languageId;
	/**
	 * Default Language-class constructor
	 * 
	 */
	public Language() {

	}

	/**
	 * Parametrized Language-class constructor
	 * 
	 * @param languageId
	 * @param languageName Warehouse name
	 */
	public Language(int languageId, String languageName) {
		this.languageId = new SimpleIntegerProperty(languageId);
		this.languageName = new SimpleStringProperty(languageName);
	}

	// Getters and setters ---------------------------------------------------- //

	// Language id
	public int getLanguageId() {
		return languageId.get();
	}

	public void setLanguageId(int languageId) {
		this.languageId.set(languageId);
	}

	public IntegerProperty getLanguageIdProperty() {
		return languageId;
	}

	// Language name
	public String getLanguageName() {
		return languageName.get();
	}

	public void setLanguageName(String languageName) {
		this.languageName.set(languageName);
	}

	public StringProperty getLanguageNameProperty() {
		return languageName;
	}

	// Database methods ----------------------------------------------------- //
	
	
	/**
	 * Method returns ArrayList containing all language objects from the DB
	 * 
	 * @return
	 */
	public ArrayList<Language> getLanguageListFromDB() {
		ArrayList<Language> languageList = new ArrayList<Language>();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Language_ID", "LanguageName" };
			Object[] params = null;

			ResultSet rs = database.select("language", columns, "1", params);

			while (rs.next()) {
				languageList.add(new Language(rs.getInt("Language_ID"), rs.getString("LanguageName")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return languageList;

	}

	/**
	 * Method returns an ObservableList containing all existing Language-objects from the Database
	 * 
	 * @return
	 */
	public ObservableList<Language> getLanguageObservableListFromDB() {
		ObservableList<Language> languageList = FXCollections.observableArrayList();
		Database database;
		try {
			database = new Database();
			String[] columns = { "Language_ID", "LanguageName" };
			Object[] params = null;

			ResultSet rs = database.select("language", columns, "1", params);
			while (rs.next()) {
				languageList.add(new Language(rs.getInt("Language_ID"), rs.getString("LanguageName")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return languageList;

	}

	@Override
	public String toString() {
		return languageName.get();
	}
	
}
