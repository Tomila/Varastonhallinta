package fi.metropolia.wms.db;

/**
 * @author Miahil Karvanen
 *
 * Class contains methods for DB-wrapper to build the SQL-query 
 */

public class Query {
	private StringBuilder query;
	
	/**
	 * Method for building the delete request SQL-query
	 * @param table Table name.
	 * @return Query for data deletion from the table.
	 */
	public Query delete(String table) {
		
		query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(table);
		return this;
	}
	
	/**
	 * Method for building the update request's SQL-query
	 * @param table Table name.
	 * @return Query for data deletion from the table.
	 */
	public Query update(String table) {
		query = new StringBuilder();
		query.append("UPDATE ");
		query.append(table);
		query.append(" SET ");
		return this;
	}
	
	/**
    * Method for building the select request's SQL-query
    * @param columns
    * @return
    */
   public Query select(Object[] columns){
       query = new StringBuilder();
       query.append("SELECT ");
		/**
		 * If we give the select() method a value of null as a parameter, 
		 * it appends the * character to the SQL query, which will return 
		 * all columns in the database table.
		 */       
       if(columns != null){
           for(Object column : columns){
               query.append(column);
               query.append(",");
           }
           //removes the last question mark
           query.deleteCharAt(query.lastIndexOf(","));
       }
       else
           query.append("*");

       return this;
   }
	
	/**
	 * Adds columns to the INSERT SQL-query
	 * @param columns
	 */
	public Query setInsertColumns(String[] columns) {
		int cou = columns.length;
		if(cou==0)
			throw new IllegalArgumentException("Invalid argument count");
		;
		query.append("(");
		for(String column : columns) {
			query.append(column);
			query.append(",");
		}
		query.deleteCharAt(query.lastIndexOf(","));
		query.append(")");
		return this;
	}
	
   
   
	/**
	 * Adds columns to the SQL-query
	 * @param columns
	 */
	public Query set(String[] columns) {
		int cou = columns.length;
		if(cou==0)
			throw new IllegalArgumentException("Invalid argument count");
		;
		for(String column : columns) {
			query.append(column);
			query.append(" = ");
			query.append("?");
			query.append(",");
		}
		query.deleteCharAt(query.lastIndexOf(","));
		return this;
	}
	
	/**
    * Method for building the insert request's SQL-query
    * @param table
    * @return
    */
   public Query insert(String table){
       query = new StringBuilder();
       query.append("INSERT INTO ");
       query.append(table);
       return this;
   }

   /**
    * Adds values param to the  SQL-query
    * @param params
    * @return
    */
   public Query values(Object[] params){
       query.append(" VALUES(");

       int cou = params.length;

       if(cou == 0)
           throw new IllegalArgumentException("Invalid parameter count");

       for (int i = 0; i<cou; i++) {
           query.append("?,");
       }
       //removes the last comma
       query.deleteCharAt(query.lastIndexOf(","));
       query.append(");");
       return this;
   }
	
	/**
	 * Adds the WHERE condition to the SQL-query as a request requirement
	 * @param requirement
	 * @return
	 */
	public Query where(String requirement) {
		query.append(" WHERE ");
		query.append(requirement);
		return this;
	}
	
	/**
	 * Adds the LEFT JOIN condition to the SQL-query as a request requirement
	 * @param requirement
	 * @return
	 */
	public Query leftJoin(String requirement) {
		query.append(" LEFT JOIN ");
		query.append(requirement);
		return this;
	}
	
	/**
	 * Adds the OUTPUT condition to the SQL-query as a request requirement
	 * @param requirement
	 * @return
	 */
	public Query output(String element) {
		query.append(" OUTPUT ");
		query.append(element);
		return this;
	}
	
	/**
	 * Adds the FROM condition to the SQL-query as a request requirement
	 * @param table
	 * @return
	*/
	public Query from(String table) {
		query.append(" FROM ");
		query.append(table);
		return this;
	}
	
	/**
	 * Adds the FROM condition with multiply tables to the SQL-query as a request requirement
	 * 
	 * @param table
	 * @return
	 */
	public Query from(String[] table) {
		int cou = table.length;
		if (cou == 0)
			throw new IllegalArgumentException("Invalid argument count");
		;
		query.append(" FROM ");

		for (String tableName : table) {
			query.append(tableName);
			query.append(", ");
		}
		query.deleteCharAt(query.lastIndexOf(","));

		return this;
	}
	
    /**
    * Returns the generated SQL query
    * @return query
    */
   public String getQuery(){
       return query.toString();
   }
}
