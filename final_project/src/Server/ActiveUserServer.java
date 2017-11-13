package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActiveUserServer {
	private List<ActiveUserThread> activeUserThreads;
	
	public ActiveUserServer(int port) {
		try {
			System.out.println("Trying to bind to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Login server bound to port " + port);
			
			activeUserThreads = new ArrayList<ActiveUserThread>();
			
			// loop to keep accepting logins
			while (true) {
				System.out.println("Active users logged in: " + activeUserThreads.size());
				Socket loggingInPlayer = ss.accept();
				System.out.println("Login connection from " + loggingInPlayer.getInetAddress());
				
				// every cilent that connects gets a server thread
				ActiveUserThread activeUser = new ActiveUserThread(loggingInPlayer, this);
				while (!activeUser.attemptedLogin()) {
					Thread.yield();
				}
				
				if (!activeUser.loginFailed()) {
					activeUserThreads.add(activeUser);
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in LoginServer constructor: " + ioe.getMessage());
		}
	}
	
	public boolean login(String username, String password, ActiveUserThread loginUser) {
		System.out.println("Logging in user with:");
		System.out.println("Username = " + username);
		System.out.println("Password = " + password);
		System.out.println();
		
		// add user to database
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean foundUser = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ColiseumDatabase?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			ps.setString(1, username); // set first variable in prepared statement
			rs = ps.executeQuery();
			String existingUser = null;
			String existingPass = null;
			while (rs.next()) {
				foundUser = true;
				existingUser = rs.getString("username");
				existingPass = rs.getString("pass");
			}
			if (!foundUser) {
				loginUser.notifyLoginStatus("Username and password combination did not work!");
				return false;
			} else if (existingUser.equals(username) && existingPass.equals(password)) {
				loginUser.notifyLoginStatus("SUCCESS");
				return true;
			} else {
				loginUser.notifyLoginStatus("Username and password combination did not work!");
				return false;
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		ActiveUserServer ls = new ActiveUserServer(6788);
	}
}
