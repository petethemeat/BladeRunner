package Database;

import java.sql.*;

import com.mysql.jdbc.jdbc2.optional.*;

public class dbConnection 
{
	private dbSource _source;
	private Connection _connection;
	
	public dbConnection(dbSource source)
	{
		_source = source;
	}
	
	public void startConnection() throws SQLException
	{
		_connection = _source.getSource().getConnection();
	}
	
	public void closeConnection() throws SQLException
	{
		_connection.close();
	}
	
	private ResultSet queryBlade(String id) throws SQLException
	{
		_connection.setReadOnly(false);
			
		Statement stmt = _connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
		ResultSet rs = stmt.executeQuery("SELECT * FROM saw_blades WHERE id=" + id);
						
		return rs;
				
	}
	
	public void updateField(String id, String field, String value) throws SQLException
	{
		
		ResultSet rs = this.queryBlade(id);
		
		rs.absolute(1);
		rs.updateString(field, value);
		rs.updateRow();
			
	}
	
	public String getField(String id, String field) throws SQLException
	{
		
		ResultSet rs = this.queryBlade(id);
		
		rs.absolute(1);
		return rs.getString(field);
	}
	
}
