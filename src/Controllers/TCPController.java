package Controllers;

import java.io.IOException;

import Communication.TCPConnection;

public class TCPController 
{
	public static String run() throws IOException
	{
		TCPConnection connection = new TCPConnection(Hub.getPort());
		
		connection.startConnection();
		String id = connection.receive();
		connection.endConnection();
		
		return id;
	}
	
	
}
