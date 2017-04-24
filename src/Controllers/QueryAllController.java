package Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.dbConnection;
import Database.dbSource;

public class QueryAllController 
{
	public static String[][] run() throws SQLException
	{
		
		dbSource source = Hub.getSource();
	
		dbConnection connection = new dbConnection(source);

		connection.startConnection();
		String[] fields = Hub.getFields();

	
		//This depends on what we are trying to display.
		ArrayList<String[]> results = connection.getTableContents(fields);
		
		String[][] rss = new String[results.size()][];
		for(int i = 0; i < results.size(); i++)
		{
			rss[i] = results.get(i);
		}
		return rss;
	}
	

}
