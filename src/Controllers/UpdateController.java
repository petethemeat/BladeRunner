package Controllers;
import java.sql.SQLException;

import Database.*;

public class UpdateController
{
	public static void run(String id, String[] values, String[] fields) throws	SQLException	
	{
		dbSource source = Hub.getSource();
		dbConnection connection = new dbConnection(source);

		connection.startConnection();
		connection.updateField(id, fields, values);
		connection.closeConnection();
		
	}
}
