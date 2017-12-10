package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationServer {
	private BufferedReader br;
	private PrintWriter pw;
	
	public RegistrationServer(int port) {
		try {
			System.out.println("Trying to bind to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Registration server bound to port " + port);
			
			while (true) {
				Socket registeringPlayer = ss.accept();
				System.out.println("Registration connection from " + registeringPlayer.getInetAddress());
				
				// every cilent that connects gets a server thread
				RegisteringUserThread rut = new RegisteringUserThread(registeringPlayer, this);
			}
		} catch (IOException ioe) {
			System.out.println("ioe in RegistrationServer constructor: " + ioe.getMessage());
		}
	}
	
	public void register(String username, String password, RegisteringUserThread registeringUser) {
		System.out.println("Registering user with:");
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
			while (rs.next()) {
				foundUser = true;
				String existingUser = rs.getString("username");
				
				System.out.println("Someone tried to register with an existing username: " + existingUser);
			}
			
			// if the username already exists
			if (foundUser) {
				registeringUser.notifyRegistrationStatus("The username you chose is taken!");
			}
			// if the username does not exist
			else {
				// register the user into the database
				String query = "INSERT INTO Users (username, pass, highScore)" + " VALUES ('" + username + "', '" + password + "', 0)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.execute();
				registeringUser.notifyRegistrationStatus("SUCCESS");
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
	}
	
	public static void main(String[] args) {
		RegistrationServer cr = new RegistrationServer(6789);
	}
}
