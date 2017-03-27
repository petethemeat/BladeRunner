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
	
	private static String[] fields;
	
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
		String table = prop.getProperty("dbtable");
		
		String field = prop.getProperty("dbfields");
		
		fields = field.split(",");
		
		source = new dbSource(user, password, serverName, dbName, table);
		
	}
	
	public static dbSource getSource()
	{
		return source;
	}
	
	public static String[] getFields()
	{
		return fields;
	}

}
