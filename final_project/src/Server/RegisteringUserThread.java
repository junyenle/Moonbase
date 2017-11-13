package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisteringUserThread extends Thread {
	private BufferedReader br;
	private PrintWriter pw;
	private RegistrationServer rs;
	
	public RegisteringUserThread(Socket s, RegistrationServer rs) {
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.rs = rs;
			
			// starts the thread
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in RegisteringUserThread constructor: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			// registeringPlayer has an input stream and an output stream
			
			// in registeringPlayer's input stream, there are two lines
			// first is username, second is unhashed password
			String username = br.readLine();
			String rawPassword = br.readLine();
			String hashedPassword = hashPassword(rawPassword);
			
			rs.register(username, hashedPassword, this);
		} catch (IOException ioe) {
			System.out.println("ioe in RegisteringUserThread.run() " + ioe.getMessage());
		}
	}
	
	private static String hashPassword(String password) {
		String passwordToHash = password;
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes
			
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
				//Get complete hashed password in hex format
				generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
		}
		
		return generatedPassword;
	}
	
	public void notifyRegistrationStatus(String registrationMessage) {
		pw.println(registrationMessage);
		pw.flush();
	}
}
