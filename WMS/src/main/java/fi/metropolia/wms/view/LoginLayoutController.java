package fi.metropolia.wms.view;

import fi.metropolia.wms.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * 
 * @author Mihail Karvanen, Roman Prokhozhev
 */

public class LoginLayoutController {


	private static int sessionID = 0;

	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;

	// Reference to the main application
	private App app;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(App app) {
		this.app = app;
	}

	public void initialize() {
	}

	/**
	 * Check authorization credentials.
	 * 
	 * If accepted, return a sessionID for the authorized session otherwise, return
	 * null.
	 */

	public void authorize() {
		int sessionID = (("open".equals(user.getText()) && "sesame".equals(password.getText()))
				|| ("admin".equals(user.getText()) && "admin".equals(password.getText()))) ? generateSessionID() : 0;
		if (sessionID != 0) {
			app.authenticated(sessionID);
		}

	}

	private int generateSessionID() {
		if (user.getText().equals("open")) {
			sessionID = 1;
		} else if (user.getText().equals("admin")) {
			sessionID = 2;
		}

		return sessionID;
	}
}
