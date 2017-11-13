package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ActiveUserThread extends Thread {
	private BufferedReader br;
	private PrintWriter pw;
	String username;
	int highscore;
	private ActiveUserServer as;
	boolean loginFailed;
	boolean attemptedLogin;
	
	public ActiveUserThread(Socket s, ActiveUserServer ls) {
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.as = ls;
			attemptedLogin = false;
			loginFailed = false;
			
			// starts the thread
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ActiveUserThread constructor: " + ioe.getMessage());
		}
	}
	
	public void run() {
		String username = null;	
		String rawPassword = null;
		try {
			username = br.readLine();
			rawPassword = br.readLine();
		} catch (IOException ioe) {
			System.out.println("ioe when retrieving login username and password: " + ioe.getMessage());
		}
		String hashedPassword = hashPassword(rawPassword);
		
		boolean loggedIn = as.login(username, hashedPassword, this);
		
		attemptedLogin = true;
		
		if (loggedIn) {
			System.out.println("login was a success!");
			while (!hashedPassword.equals("hello")) {
				Thread.yield();
			}
		} else {
			loginFailed = true;
		}
	}
	
	public boolean attemptedLogin() {
		return attemptedLogin;
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
	
	public String getUsername() {
		return username;
	}
	
	public void notifyLoginStatus(String loginStatus) {
		pw.println(loginStatus);
		pw.flush();
	}
	
	public boolean loginFailed() {
		return loginFailed;
	}
	
	public PrintWriter getPrintWriter() {
		return pw;
	}
}
