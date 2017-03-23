package Controllers;

import java.sql.SQLException;

import Database.*;

public class QueryController
{
	public static String[] run(String id)
	{
		dbSource source = Hub.getSource();
		
		dbConnection connection = new dbConnection(source);
		
		//This depends on what we are trying to display.
		String[] results = new String[4];	
		
		try 
		{
			connection.startConnection();
			results[0] = connection.getField(id, "id");
			results[1] = connection.getField(id, "blade_size");
			results[2] = connection.getField(id, "start_date");
			results[3] = connection.getField(id, "hours_used");
			connection.closeConnection();
			
		}
		

		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
	
	public static void main(String args[])
	{
		Hub.start();
		String[] results = QueryController.run("2");
		
		for(String result : results)
		{
			System.out.println(result);
		}
	}
}
