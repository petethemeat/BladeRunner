package Controllers;

import java.sql.SQLException;
import Database.*;

public class DeleteController 
{
	public static void run(String id) throws SQLException
	{
		dbSource source = Hub.getSource();
		dbConnection connection = new dbConnection(source);
	
		connection.startConnection();
		connection.deleteRow(id);
		connection.closeConnection();
	
	}
	
}
