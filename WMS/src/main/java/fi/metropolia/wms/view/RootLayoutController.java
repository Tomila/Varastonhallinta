package fi.metropolia.wms.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Application root layout. Also contains top menu bar.
 * 
 * @author Mihail Karvanen, Roman Prokhozhev
 */

public class RootLayoutController implements Initializable{

	// Language data
	ArrayList<Language> languageData;
	
	// Top menu bar
	
	 @FXML
	 private MenuBar topMenuBar;
	 
	 @FXML
	 private Menu settingsMenu;
	 
	 @FXML
	 private Menu languageMenu;
	
	 private ResourceBundle bundle;

	// Reference to the main application
	private App app;
	
//	public RootLayoutController() {
//
//	}
	
	//First initialize resources
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		//then fxml elements
		initialize();
	}
	
	@FXML
	private void initialize() {
		
		// Add "settings"-menu item with Language selection menu item to the top menu bar
		settingsMenu = new Menu(bundle.getString("settingsMenu"));
		languageMenu = new Menu(bundle.getString("languageMenu"));
		languageData = new Language().getLanguageListFromDB();
		for (Language lang : languageData) {
			MenuItem menuItem = new MenuItem(lang.getLanguageName());
			menuItem.setOnAction(event -> changeLocale(lang.getLanguageId()));
			languageMenu.getItems().add(menuItem);
		}
		settingsMenu.getItems().add(languageMenu);
		topMenuBar.getMenus().add(settingsMenu);
	}



	@FXML
	private void handleShowWarehouseEditor() {
		app.showWarehouseEditor();
	}

	@FXML
	private void handleStockOverview() {
		app.showStockOverview();
	}
	
	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

	/**
	 * Handles user logout.
	 */
	@FXML
	public void logout() {
		app.initRootLayout();
		app.showLoginLayout();
	}
	
	/**
	 * Handles localisation.
	 */
	public void changeLocale(int id) {
		app.ChangeLanguage(id);
		System.out.println(id);
	}
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setMainApp( App app) {

		this.app = app;

	}

}
