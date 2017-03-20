package Controllers;

import java.sql.SQLException;
import Database.*;

public class DeleteController 
{
	public static void run(String id)
	{
		dbSource source = Hub.getSource();
	
		dbConnection connection = new dbConnection(source);
	
		try 
		{
		
			connection.startConnection();
			connection.deleteRow(id);
			connection.closeConnection();
			
		} 
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
