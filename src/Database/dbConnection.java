package Database;

import java.sql.*;
import java.util.ArrayList;

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
			
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + _source.getTable() +  " WHERE id=" + id);
						
		return rs;
				
	}
	private ResultSet getTable() throws SQLException
	{
		
		_connection.setReadOnly(false);
		
		Statement stmt = _connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + _source.getTable());
		
		return rs;
	}
	
	public void updateField(String id, String[] fields, String[] values) throws SQLException
	{
		
		ResultSet rs = this.queryBlade(id);
		
		rs.absolute(1);
		
		for(int i = 0; i < fields.length; i++)
		{
			rs.updateString(fields[i], values[i]);
		}
		
		rs.updateRow();
			
	}
	
	public String getField(String id, String field) throws SQLException
	{
		
		ResultSet rs = this.queryBlade(id);
		
		rs.absolute(1);
		return rs.getString(field);
	}
	
	public void addRow(String[] fields, String[] values) throws SQLException
	{
		ResultSet rs = this.getTable();
		
		rs.moveToInsertRow();
		
		for(int i = 0; i < fields.length; i++)
		{
			rs.updateString(fields[i], values[i]);
		}
		
		rs.insertRow();
		
	}
	
	public void deleteRow(String id) throws SQLException
	{

		ResultSet rs = queryBlade(id);
		
		rs.absolute(1);
		
		rs.deleteRow(); 
		
	}
	
	public ArrayList<String[]> getTableContents(String[] fields) throws SQLException
	{
		ArrayList<String[]> results = new ArrayList<String[]>();
		ResultSet rs = this.getTable();
		while(rs.next())
		{
			String result[] = new String[fields.length];
			for(int i = 0; i < fields.length; i++)
			{
				result[i] = rs.getString(fields[i]);
			}
			results.add(result);
		}
		
		return results;
	}
	
}
