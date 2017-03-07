package Database;

import java.sql.*;

import com.mysql.jdbc.jdbc2.optional.*;

public class k {
		
		
		public static void main(String[] args)
		{
			
			dbSource db = new dbSource("root", "P5t5r12!", "localhost", "tenaris");
			dbConnection connection = new dbConnection(db);
		
			
			try 
			{
				connection.startConnection();
				connection.updateField("1", "blade_size", "74");
				System.out.println(connection.getField("1", "blade_size"));
				
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
