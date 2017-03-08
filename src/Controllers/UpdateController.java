package Controllers;
import java.sql.SQLException;

import Database.*;

public class UpdateController
{
	public static void run(String id, String[] values, String[] fields)
	{
		dbSource source = Hub.getSource();
		dbConnection connection = new dbConnection(source);
		
		try 
		{
			connection.startConnection();
			connection.updateField(id, fields, values);
			connection.closeConnection();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
}
