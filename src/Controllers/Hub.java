package Controllers;

import Database.dbSource;
import Logging.BladeLogger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Hub {
	
	//This class will read important information from configuration and generate important shared data
	
	private static dbSource source;
	
	public final static BladeLogger logger = new BladeLogger();
	
	public static void start()
	{
		Properties prop = new Properties();
		InputStream input;
		
		try
		{
			input = new FileInputStream("config.properties");
			prop.load(input);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		String user = prop.getProperty("dbuser");
		String password = prop.getProperty("dbpassword");
		String serverName = prop.getProperty("dbhost");
		String dbName = prop.getProperty("dbname");
		
		source = new dbSource(user, password, serverName, dbName);
		
	}
	
	public static dbSource getSource()
	{
		return source;
	}
	
	public static void main(String args[])
	{
		Hub.start();
		Hub.logger.info("Blade runner test is starting");
	
		
		String[] fields2 = {"id", "blade_size", "start_date", "hours_used"};
		String[] values2 = {"35", "11", "2012-08-06 08:11:12", "30"};
		
		
		try
		{
			AddController.run(fields2, values2);
			
			String[] results = QueryController.run("20");
			
			for(String result : results)
			{
				System.out.println(result);
			}
		}
		catch(SQLException e)
		{
			
		}
		
		
	}

}
