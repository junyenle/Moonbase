using System;
using System.Net.Sockets;
using System.IO;
		
try
{
	TcpClient client = new TcpClient("localhost",6789); 	// connect to server
	Console.WriteLine("Connected to Server");
	
	// anything past here isn't working yet
	NetworkStream stream = client.GetStream();				// get stream
	Console.WriteLine("got here");
	StreamWriter writer = new StreamWriter(stream);			// create writer
	
	Console.WriteLine("Sending test message");
	writer.WriteLine("Test Send");							// send text
	writer.Flush();

	while(true)
	{
		// do nothing
	}
		
} catch(Exception e) {
	Console.WriteLine (e);
}