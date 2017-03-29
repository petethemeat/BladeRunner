package Communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPConnection
{	
	private int _port;
	private ServerSocket _socket;
	
	public TCPConnection(int port)
	{
		_port = port;
	}
	
	public void startConnection() throws IOException
	{
		_socket = new ServerSocket(_port);
	}
	
	public String receive() throws IOException
	{
		Socket dataSocket = _socket.accept();
		Scanner sc = new Scanner(dataSocket.getInputStream());
		return sc.next();	
	}
	
	public void endConnection() throws IOException
	{
		_socket.close();
	}
}
