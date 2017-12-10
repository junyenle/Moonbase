package StateManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Graphics.Background;

public class RegisterState extends GameState
{
	// graphics
	private Background background;
	private Font font;
	
	// input
	private JTextField usernameField;
	private JTextField passwordField;
	private JLabel errorLabel;
	private JButton registerButton;
	private JButton backButton;
	
	// logic
	private StateManager manager;
	private boolean isDisplayed = false;
	private String errorMessage = "";
	
	public RegisterState(StateManager manager)
	{
		this.manager = manager;
		usernameField = new JTextField(30);
		passwordField = new JTextField(30);
		errorLabel = new JLabel(errorMessage);
		
		try
		{
			background = new Background("/backgrounds/titlescreen.gif", 1);
			background.setVector(-0.1, 0);
			this.font = new Font("Calibri", Font.PLAIN, 60);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setup()
	{
		
	}
	
	public void update()
	{
		background.update();
	}
	
	public void draw(Graphics2D graphics) 
	{
		if (!isDisplayed) {
			// set up the JFrame
			JFrame frame = new JFrame();
			frame.setPreferredSize(new Dimension(500, 250));
			JPanel panel = new JPanel();
			frame.add(panel, BorderLayout.CENTER);
			
			// add the username input
			panel.add(new JLabel("Username:"));
			panel.add(usernameField);
			
			// add the password input
			panel.add(new JLabel("Password:"));
			panel.add(passwordField);
			
			// dispaly register and back buttons
			registerButton = new JButton("Register");
			backButton = new JButton("Back");
			panel.add(registerButton);
			panel.add(backButton);

			// makes the JFrame visible
			frame.pack();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			
			background.draw(graphics);
			
			graphics.setColor(Color.white);
			graphics.setFont(this.font);
			
			registerButton.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        register();
			        if (errorMessage.equals("SUCCESS")) {
			        		manager.setCurrentState(StateManager.MENU);
			        		closeRegistrationBox(frame);
			        } else {
			        		panel.add(errorLabel);
			        		
			        		frame.revalidate();
			        		frame.repaint();
			        }
			    }
			});
			
			backButton.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
				    	manager.setCurrentState(StateManager.MENU);
				    	closeRegistrationBox(frame);
			    }
			});
			
			// draw the registration title
			graphics.drawString("Registration", 800, 150);
			isDisplayed = true;
		}
	}
	
	private void register() {
		try {
			Socket registeringUserSocket = new Socket("localhost", 6789);
			
			PrintWriter pw = new PrintWriter(registeringUserSocket.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(registeringUserSocket.getInputStream()));
			
			pw.println(usernameField.getText());
			pw.println(passwordField.getText());
			pw.flush();
			
			String registrationMessage = null;
			while (registrationMessage == null) {
				registrationMessage = br.readLine();
				Thread.yield();
			}
			
			errorMessage = registrationMessage;
			errorLabel.setText(errorMessage);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void closeRegistrationBox(JFrame frame) {
		isDisplayed = false;
	    	errorMessage = "";
	    	usernameField.setText("");
	    	passwordField.setText("");
	    	frame.dispose();
	}
	
	public void keyPressed(int keyCode)
	{
		
	}
	
	public void keyReleased(int keyCode)
	{
		
	}
}
