package Database;
import java.sql.*;
import com.mysql.jdbc.jdbc2.optional.*;

public class dbSource
{
	private MysqlDataSource source;
	
	public dbSource(String user, String password, String serverName, String dbName)
	{
		
		source = new MysqlDataSource(); 	//Instantiate new mysql data source
		
		//Set data source details
		
		source.setUser(user);
		source.setPassword(password);
		source.setServerName(serverName);
		source.setDatabaseName(dbName);
		
	}
	
	public MysqlDataSource getSource()
	{
		return source;
	}
	
}
