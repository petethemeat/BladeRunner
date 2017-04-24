package Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.dbConnection;
import Database.dbSource;

public class QueryAllController 
{
	public static ArrayList<String[]> run() throws SQLException
	{
		
		dbSource source = Hub.getSource();
	
		dbConnection connection = new dbConnection(source);

		connection.startConnection();
		String[] fields = Hub.getFields();

	
		//This depends on what we are trying to display.
		ArrayList<String[]> results = connection.getTableContents(fields);
		return results;
	}

}
