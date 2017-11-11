using System;
using System.Net.Sockets;
using System.IO;
		
try
{
	TcpClient client = new TcpClient("localhost",6789); 	// connect to server
	Console.WriteLine("Connected to Server");
	
	// anything past here isn't working yet
	// NetworkStream stream = client.GetStream();				// get stream
	// StreamWriter writer = new StreamWriter(stream);			// create writer
	
	// writer.WriteLine("Test Send");							// send text
	// writer.Flush();											
		
} catch(Exception e) {
	Console.WriteLine (e);
}