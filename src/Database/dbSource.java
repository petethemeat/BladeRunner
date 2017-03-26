package Database;

import com.mysql.jdbc.jdbc2.optional.*;

public class dbSource
{
	private MysqlDataSource source;
	private String _table;
	
	public dbSource(String user, String password, String serverName, String dbName, String table)
	{
		
		source = new MysqlDataSource(); 	//Instantiate new mysql data source
		
		//Set data source details
		
		source.setUser(user);
		source.setPassword(password);
		source.setServerName(serverName);
		source.setDatabaseName(dbName);
		
		_table = table;
		
	}
	
	public MysqlDataSource getSource()
	{
		return source;
	}
	
	public String getTable()
	{
		return _table;
	}
	
}
