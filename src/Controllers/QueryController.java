package Controllers;

import java.sql.SQLException;

import Database.*;

public class QueryController
{
	public static String[] run(String id) throws SQLException
	{
		dbSource source = Hub.getSource();
		
		dbConnection connection = new dbConnection(source);
	
		connection.startConnection();
		String[] fields = Hub.getFields();
	
		
		//This depends on what we are trying to display.
		String[] results = new String[fields.length];	
		for(int i = 0; i < fields.length; i++)
		{
			results[i] = connection.getField(id, fields[i]);
		}

		connection.closeConnection();
			
	
		return results;
	}
}
