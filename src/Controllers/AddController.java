package Controllers;

import java.sql.SQLException;
import Database.*;

public class AddController 
{
	public static void run(String[] fields, String[] values)
	{
		dbSource source = Hub.getSource();
		
		dbConnection connection = new dbConnection(source);
		
		try 
		{
			connection.startConnection();
			connection.addRow(fields, values);
			connection.closeConnection();
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
