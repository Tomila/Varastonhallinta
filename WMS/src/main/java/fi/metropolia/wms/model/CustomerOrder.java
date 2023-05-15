package fi.metropolia.wms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fi.metropolia.wms.db.Database;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * Class takes customer orders from DB and puts orders data in needed java format.
 * 
 * @author Roman Prokhozhev
 */


public class CustomerOrder {
	
	private StringProperty customerName;
	private IntegerProperty orderNumber;
	private ObservableList<CustomerOrder> orderList;
	
	/**
	 * Empty constructor. Sometimes may be used in special situations.
	 */
	public CustomerOrder() {
	}
	
	/**
	 * Constructor with customer name and its orders order number. 
	 * @param orderNumber
	 * @param customerName
	 */
	public CustomerOrder(int orderNumber, String customerName) {
		this.customerName = new SimpleStringProperty(customerName);
		this.orderNumber = new SimpleIntegerProperty(orderNumber);
	}
	
	/**
	 * Gets all not collected yet orders from DB and puts them into CustomerOrder objects.
	 * @return ObservableList of CustomerOrder objects
	 */
	//I'm not sure that it supposed to be public method. We need to think about it later(This hole class could also be a singleton?) 
	public ObservableList<CustomerOrder> getOrderListFromDB() {
		if (this.orderList != null && !this.orderList.isEmpty()) {
			return orderList;
		} else {
			ObservableList<CustomerOrder> orderList = FXCollections.observableArrayList();
			try {
				Database database = new Database();
				ResultSet rs = database.select(
						"SELECT sales_order.SalesOrderNumber, customer.CustomerName\r\n" + "FROM sales_order\r\n"
								+ "INNER JOIN customer\r\n" + "ON sales_order.Customer_ID=customer.Customer_ID\r\n"
										+ "WHERE sales_order.Done = 0;");
				while (rs.next()) {
					orderList.add(new CustomerOrder(rs.getInt("SalesOrderNumber"), rs.getString("CustomerName")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.orderList = orderList;
			return orderList;
		}
	}

	/**
	 * Gets all orders ordered items by order number from DB. 
	 * @param orderNumber  Is needed to find order
	 * @return ObservableMap of Item and Integer objects which describe ordered item and ordered amount.
	 */
	// This method should have order number as a parameter to finds needed order
	public ObservableMap<Item, Integer> getOrderedItemListFromDB(int orderNumber) {
		// init all orders first (EDIT LATER)
		// getOrderListFromDB();
		ObservableMap<Item, Integer> orderedItemList = FXCollections.observableHashMap();
		try {
			Database database = new Database();
			//ObservableList<CustomerOrder> orders = getOrderListFromDB();
			ResultSet rs = database.select(
					"SELECT item.ItemName, item.Item_ID, item.Saldo, item.Price, solditem.Quantity "
							+ "FROM solditem, item\r\n" + "WHERE item.Item_ID=solditem.SoldItemID\r\n"
							+ "AND solditem.SalesOrderNumber = ?;",
					orderNumber);

			// getInt(price) todennäköisesti muuttuu getDouble/Float
			while (rs.next()) {
				orderedItemList.put(new Item(rs.getInt("Item_ID"), rs.getString("ItemName"), rs.getInt("Saldo"),
						rs.getInt("Price")), (Integer) rs.getInt("Quantity"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Just testing output
		/*
		 * orderedItemList.entrySet().forEach(entry -> {
		 * System.out.println(entry.getKey().getItemNameProperty().getValue() + " " +
		 * entry.getValue()); });
		 */
		return orderedItemList;
	}
	
	/**
	 * Marking order as collected in DB.
	 * @param orderNumber Number of collected order
	 */
	public void markOrderAsCollected(int orderNumber) {
		try {
			Database database = new Database();
			//ObservableList<CustomerOrder> orders = getOrderListFromDB();
			database.updateSalesByOrderNumber(
					"UPDATE sales_order\r\n"
					+ "SET sales_order.Done = 1\r\n"
					+ "WHERE sales_order.SalesOrderNumber = ?;",
					orderNumber);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns customer name
	 * @return customer name
	 */
	public StringProperty getCustomerNameProperty() {
		return customerName;
	}
	
	/**
	 * Returns order number
	 * @return order number
	 */
	public IntegerProperty getOrderNumberProperty() {
		return orderNumber;
	}

}