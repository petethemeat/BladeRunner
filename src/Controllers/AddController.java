package Controllers;

import java.sql.SQLException;
import Database.*;

public class AddController 
{
	public static void run(String[] fields, String[] values) throws SQLException
	{
		dbSource source = Hub.getSource();
		
		dbConnection connection = new dbConnection(source);
		
		connection.startConnection();
		connection.addRow(fields, values);
		connection.closeConnection();
			
	
	}
}
