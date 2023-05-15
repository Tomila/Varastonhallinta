package fi.metropolia.wms.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import fi.metropolia.wms.db.Database;

/**
 * @author Mihail Karvanen
 *
 */
public class testDBWrapper {

	public static void main(String[] args) {
	    try {
            Database database = new Database();
            
            /**
             * Testing data insertion to the database
             * INSERT INTO `item` (`Item_ID`, `ItemName`, `Saldo`, `Price`) VALUES ('', 'Laptop', 2, 300);
             */
            /*
            // Array of the data objects to insert
            Object[] firstParams = {null, "New Laptop", 4, 500};
            
            // In database's "insert" method's the first parameter is a table's name 
            // and the second is an array of elements to add
            // method returning a number of the affected rows
            int success1 = database.insert("item", firstParams);
            
            System.out.println("Insert new item = " + success1);
            */
            
            /**
             * Testing data deletion from the database
             * DELETE FROM `Item` WHERE ItemName=`Laptop` AND Saldo=2;
             */
            /*
            Object[] params2 = {"New Laptop", 4};
            int success2 = database.delete("item", "ItemName = ? AND Saldo = ?", params2);
            System.out.println("Delete the Item 'Laptop' which 'saldo' is '2' = " + success2);
            */
            
            /**
             * Testing database's data updating
             * UPDATE `item` SET `ItemName` = 'New Laptop', `Saldo` = '4', `Price` = '500' WHERE `item`.`Item_ID` = 1;
             */
            /*
            String[] columns = {"ItemName", "Saldo", "Price"};        
            Object[] params = {"New Laptop", 4, 500, 1};
            int success4 = database.update("item", columns, "Item_ID = ?", params);
            System.out.println("Edit an item = " + success4);
            */
            
            /**
             * Testing database's data printing
             * SELECT ItemName, Saldo FROM `item` WHERE Price=500;
             */
            
            String[] columns3 = {"ItemName", "Saldo"};
            Object[] params3 = {500};
            ResultSet rs = database.select("item", columns3, "Price = ?", params3);

            while(rs.next()) {
                System.out.println(rs.getString("ItemName") + " - " + rs.getString("Saldo"));
            }
            
        System.out.println(database.count("item"));
    } catch (SQLException ex) {
        System.out.println("error - "+ex.getMessage());
    }
	}

}
