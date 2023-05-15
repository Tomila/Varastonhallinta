package fi.metropolia.wms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Mihail Karvanen
 *
 */

public class Database {

	// MySQL database credentials
	final String MYSQL_URL = "your url";
	final String USERNAME = "your name";
	final String PWD = "your pwd";

	// instance of the database connection

	// the Query class instance
	protected Connection conn;

	// instance of class for handling a query
	protected Query query;

	/**
	 * The Database class constructor
	 * 
	 * @param db
	 * @param userName
	 * @param password
	 * @throws SQLException
	 */
	
	private long generatedId;
	
	public long getGeneratedId() {
		return generatedId;
	}
	public Database() throws SQLException {

		// conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);

	}

	private int query(String query, Object[] params) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {

			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			if (params != null) {
				int i = 1;
				for (Object param : params) {
					stmt.setObject(i, param);
					i++;
				}
			}
			int result = stmt.executeUpdate();
			try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
				if (generatedKeys.next()) {
					generatedId = generatedKeys.getLong(1);
				}
			}

			return result;
			
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return 0;
	}

	/**
	 * Method for removing data from the database
	 * 
	 * @param table Database table name
	 * @param req   Query string of the columns where the data have to be deleted
	 * @param param
	 * @return Returns the number of affected rows
	 */

	public int delete(String table, String req, Object[] param) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}

		query = new Query();
		query.delete(table).where(req);
		return query(query.getQuery(), param);

	}
	
	/**
	 * Inserts one row to a database table
	 * 
	 * @param table
	 * @param params
	 * @return
	 * @throws java.sql.SQLException
	 */
	public int insert(String table, Object[] params) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		query = new Query();
		query.insert(table).values(params);
		return query(query.getQuery(), params);
		
	}

	/**
	 * Inserts one row to a database table with columns
	 * 
	 * @param table
	 * @param columns
	 * @param params
	 * @return
	 * @throws java.sql.SQLException
	 */
	public int insert(String table, String[] columns, Object[] params) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		query = new Query();
		query.insert(table).setInsertColumns(columns).values(params);
		return query(query.getQuery(), params);
	}
	
	/**
	 * Updates data stored in a database table
	 * 
	 * @param table
	 * @param columns
	 * @param requirement
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int update(String table, String[] columns, String requirement, Object[] params) throws SQLException {

		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}

		query = new Query();

		query.update(table).set(columns).where(requirement);

		return query(query.getQuery(), params);
	}

	/**
	 * Returns data from a table
	 * 
	 * @param table
	 * @param columns
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public ResultSet select(String table, Object[] columns, Object[] params) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return this.select(table, columns, "", params);
	}

	
	
	/**
	 * Returns data from a table
	 * 
	 * @param table
	 * @param columns
	 * @param requirement
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public ResultSet select(String[] table, Object[] columns, String requirement, Object[] params) throws SQLException {

		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			query = new Query();
			query.select(columns).from(table).where(requirement);

			PreparedStatement ps = conn.prepareStatement(query.getQuery());
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					ps.setObject(index, param);
					index++;
				}
			}

			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return null;
	}
	
	/**
	 * Returns data from a table with "LEFT JOIN"
	 * 
	 * @param table
	 * @param columns
	 * @param requirement
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public ResultSet select(String[] table, Object[] columns, String requirement, Object[] params, String leftJoin) throws SQLException {

		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			query = new Query();
			query.select(columns).from(table).leftJoin(leftJoin).where(requirement);

			PreparedStatement ps = conn.prepareStatement(query.getQuery());
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					ps.setObject(index, param);
					index++;
				}
			}

			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return null;
	}
	
	/**
	 * Returns data from a table
	 * 
	 * @param table
	 * @param columns
	 * @param requirement
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public ResultSet select(String table, Object[] columns, String requirement, Object[] params) throws SQLException {

		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			query = new Query();
			query.select(columns).from(table).where(requirement);

			PreparedStatement ps = conn.prepareStatement(query.getQuery());
			if (params != null) {
				int index = 1;
				for (Object param : params) {
					ps.setObject(index, param);
					index++;
				}
			}

			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return null;
	}
	
	
	//Specifically made for inner join
	public ResultSet select(String query) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return null;
	}
	//Specifically made for inner join with where clause
	public ResultSet select(String query, int integerParemeter) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, integerParemeter);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return null;
	}
	
	public ResultSet selectItemLocationByID(int id) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			String query = "SELECT warehouse.WarehouseName, section.SectionType, rack.Rack_ID, bay.BayNumber, level.LevelNumber, bin.BinNumber\r\n"
					+ "FROM item_location \r\n"
					+ "INNER JOIN warehouse  \r\n"
					+ "ON warehouse.Warehouse_ID = item_location.Warehouse_ID\r\n"
					+ "INNER JOIN section  \r\n"
					+ "ON section.Section_ID = item_location.Section_ID  \r\n"
					+ "INNER JOIN rack  \r\n"
					+ "ON rack.Rack_ID = item_location.Rack_ID  \r\n"
					+ "INNER JOIN bay  \r\n"
					+ "ON bay.Bay_ID = item_location.Bay_ID\r\n"
					+ "INNER JOIN level  \r\n"
					+ "ON level.Level_ID = item_location.Level_ID  \r\n"
					+ "INNER JOIN bin  \r\n"
					+ "ON bin.Bin_ID = item_location.Bin_ID\r\n"
					+ "WHERE item_location.Item_ID = ?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return null;
	}
	
	public void updateSalesByOrderNumber(String query, int number) {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, number);
			ps.executeQuery();
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
	}

	/**
	 *
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public int count(String table) throws SQLException {
		try (Connection conn = DriverManager.getConnection(MYSQL_URL, USERNAME, PWD);) {
			PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM " + table);
			ResultSet result = ps.executeQuery();
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			do {
				System.err.println("Message: " + e.getMessage());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("SQL-State Code: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // Exit application due to error
		}
		return 0;
	}
	
}

