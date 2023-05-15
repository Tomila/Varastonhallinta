package fi.metropolia.wms.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fi.metropolia.wms.App;
import fi.metropolia.wms.model.CustomerOrder;
import fi.metropolia.wms.model.Item;
import fi.metropolia.wms.model.ItemLocation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
/**
 * Class is controller for CollectorView.fxml file
 * @author Roman Prokhozhev, Mihail Karvanen
 *
 */
public class CollectorViewController {
	@FXML
	private TableView<Map.Entry<Item, Integer>> collectedItemTable;
	@FXML
	private TableColumn<HashMap.Entry<Item, Integer>, String> CollectedItemAmountColumn;
	@FXML
	private TableColumn<HashMap.Entry<Item, Integer>, String> CollectedItemNameColumn;
	@FXML
	private TableView<Map.Entry<Item, Integer>> toCollectItemTable;
	@FXML
	private TableColumn<HashMap.Entry<Item, Integer>, String> toCollectItemAmountColumn;
	@FXML
	private TableColumn<HashMap.Entry<Item, Integer>, String> toCollectItemNameColumn;
	@FXML
	private Button itemCollectedButton;
	@FXML
	private Button deleteFromCollectedButton;
	@FXML
	private Button printOrderButton;

	private App app;
	private ObservableList<Map.Entry<Item, Integer>> items;
	private int orderNumber;
	private ResourceBundle bundle;
	
	/**
	 * Initializes and set elements to columns
	 */
	@FXML
	public void initialize() {
		toCollectItemNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String> p) {
						// this callback returns property for just one cell, you can't use a loop here
						// for first column we use key
						return p.getValue().getKey().getItemNameProperty();
					}
				});
		toCollectItemAmountColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String> p) {
						// for second column we use value
						return new SimpleStringProperty(p.getValue().getValue().toString());
					}
				});
		CollectedItemNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String> p) {
						return p.getValue().getKey().getItemNameProperty();
					}
				});
		CollectedItemAmountColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TableColumn.CellDataFeatures<Map.Entry<Item, Integer>, String> p) {
						return new SimpleStringProperty(p.getValue().getValue().toString());
					}
				});
	}
	
	/**
	 * Mouse event handler
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	public void getSelectedItem(MouseEvent event) throws IOException, SQLException {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (event.getClickCount() == 2 && toCollectItemTable.getSelectionModel().getSelectedItem() != null) {
				// Getting order number from table view after double click
				int itemID = toCollectItemTable.getSelectionModel().getSelectedItem().getKey().getItemId();
				app.showItemDetails(itemID);
			}
		}
	}
	/**
	 * Adds selected item to collected table
	 */
	@FXML
	public void addItemToCollectedTable() {
		if(toCollectItemTable.getSelectionModel().getSelectedItem() != null) {
			collectedItemTable.getItems().add(toCollectItemTable.getSelectionModel().getSelectedItem());
		}
	}
	/**
	 * Deletes selected item from collected table
	 */
	@FXML
	public void deletItemFromCollectedTable() {
		if (collectedItemTable.getSelectionModel().getSelectedItem() != null) {
			collectedItemTable.getItems().remove(collectedItemTable.getSelectionModel().getSelectedItem());
		}
	}
	/**
	 * Prints file with orders information in pdf format
	 * @throws DocumentException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void printOrder() throws DocumentException, IOException, SQLException {
		Document document = new Document();
		PdfWriter.getInstance(document,
				new FileOutputStream(bundle.getString("orderNumberTxt") + "_" + orderNumber + ".pdf"));

		document.open();

		PdfPTable itemTable = new PdfPTable(5);
		addItemTableHeader(itemTable);
		addItemTableRows(itemTable, toCollectItemTable.getItems());
		itemTable.setSpacingAfter(20f);

		Paragraph p1 = new Paragraph(bundle.getString("orderNumberTxt") + ": " + orderNumber);
		p1.setAlignment(Element.ALIGN_CENTER);
		p1.setFont(new Font(FontFactory.getFont(FontFactory.COURIER, 25, BaseColor.BLACK)));
		p1.setSpacingAfter(10f);

		PdfPTable locationTable = new PdfPTable(6);
		addLocationTableHeader(locationTable);
		ObservableList<ItemLocation> locations = FXCollections.observableArrayList();
		for (Map.Entry<Item, Integer> item : toCollectItemTable.getItems()) {
			locations.add(item.getKey().getItemLocation());
		}
		addLocationTableRows(locationTable, locations);
		itemTable.setSpacingAfter(10f);
		Paragraph p2 = new Paragraph(bundle.getString("itemsLocationTxt") + ":");
		p2.setAlignment(Element.ALIGN_CENTER);
		p2.setFont(new Font(FontFactory.getFont(FontFactory.COURIER, 25, BaseColor.BLACK)));
		p2.setSpacingAfter(10f);

		document.add(p1);
		document.add(itemTable);
		document.add(p2);
		document.add(locationTable);
		document.close();

		app.showfilePrintedWindow();
	}
	/**
	 * Marks order as collected
	 */
	@FXML
	public void markOrderAsCollected() {
		// Check first do two list contains same items or not
		ObservableList<Entry<Item, Integer>> toCollectItems = toCollectItemTable.getItems();
		ObservableList<Entry<Item, Integer>> collectedItems = collectedItemTable.getItems();
		if (toCollectItems.sorted().equals(collectedItems.sorted())) {
			try {
				app.showOrderCollectedWindow();
				app.markOrderAsCollected(orderNumber);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				app.showCheckCollectedListWindow();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param app
	 */
	public void setItemsByOrderedNumber(App app) {
		this.app = app;
		// Add observable list data to the table
		items = FXCollections.observableArrayList(new CustomerOrder().getOrderedItemListFromDB(orderNumber).entrySet());
		toCollectItemTable.setItems(items);
	}
	/**
	 * Sets order number
	 * @param number
	 */
	public void setOrderNumber(int number) {
		orderNumber = number;
	}
	/**
	 * Method adds headers to table. Method is used to print pdf file. 
	 * @param table
	 */
	private void addItemTableHeader(PdfPTable table) {
		Stream.of(bundle.getString("itemIdTxt"), bundle.getString("nameColumn"), bundle.getString("balanceColumn"),
				bundle.getString("priceColumn"), bundle.getString("orderedAmountTxt")).forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setBackgroundColor(BaseColor.GREEN);
					header.setBorderWidth(1);
					header.setPhrase(new Phrase(columnTitle));
					table.addCell(header);
				});
	}
	/**
	 * Method adds rows to table. Method is used to print pdf file. 
	 * @param table
	 */
	private void addItemTableRows(PdfPTable table, ObservableList<Map.Entry<Item, Integer>> items) {
		for (Entry<Item, Integer> itemEntry : items) {
			table.addCell(String.valueOf(itemEntry.getKey().getItemId()));
			table.addCell(itemEntry.getKey().getItemName());
			table.addCell(String.valueOf(itemEntry.getKey().getItemSaldo()));
			table.addCell(String.valueOf(itemEntry.getKey().getItemPrice()));
			table.addCell(String.valueOf(itemEntry.getValue()));
		}
	}
	/**
	 * Method adds strings with location details to table. Method is used to print pdf file. 
	 * @param table
	 */
	private void addLocationTableHeader(PdfPTable table) {
		Stream.of(bundle.getString("warehouseTreeItem"), bundle.getString("sectionColumn"),
				bundle.getString("rackColumn"), bundle.getString("bayColumn"), bundle.getString("levelColumn"),
				bundle.getString("binColumn")).forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setBackgroundColor(BaseColor.GREEN);
					header.setBorderWidth(1);
					header.setPhrase(new Phrase(columnTitle));
					table.addCell(header);
				});
	}
	/**
	 * Method adds rows to table. Method is used to print pdf file. 
	 * @param table
	 */
	private void addLocationTableRows(PdfPTable table, ObservableList<ItemLocation> locations) {
		for (ItemLocation locationEntry : locations) {
			table.addCell(locationEntry.getWarehouseName().get());
			table.addCell(locationEntry.getSectionType().get());
			table.addCell(String.valueOf(locationEntry.getRackID().get()));
			table.addCell(String.valueOf(locationEntry.getBayNumber().get()));
			table.addCell(String.valueOf(locationEntry.getLevelNumber().get()));
			table.addCell(String.valueOf(locationEntry.getBinNumber().get()));
		}
	}
	/**
	 * Sets ResourceBundle
	 * @param bundle
	 */
	public void setResources(ResourceBundle bundle) {
		this.bundle = bundle;
	}
}
