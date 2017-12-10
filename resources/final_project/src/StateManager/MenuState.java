package StateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Graphics.Background;

public class MenuState extends GameState
{
	// graphics
	private Background background;
	private ArrayList<String> menuOptions= new ArrayList<String>();
	private Color textColor;
	private Font font;
	
	// logic
	private StateManager manager;
	private int cursorIndex = 0;
	
	public MenuState(StateManager manager)
	{
		this.manager = manager;
		menuOptions.add("Start Game");
		menuOptions.add("Login");
		menuOptions.add("Register");
		menuOptions.add("Quit Game"); // TODO: add more menu options
		
		try
		{
			background = new Background("/backgrounds/titlescreen.gif", 1);
			background.setVector(-0.1, 0);
			
			this.textColor = Color.GREEN;
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
		background.draw(graphics);
	
		graphics.setColor(this.textColor);
		graphics.setFont(this.font);
		//graphics.drawString("Coliseum v2 Java Swing", 900, 400); // TODO: centering function
		
		for(int i = 0; i < menuOptions.size(); i++)
		{
			if(i == cursorIndex)
			{
				graphics.setColor(Color.WHITE);
			}
			else
			{
				graphics.setColor(Color.GRAY);
			}
			graphics.drawString(menuOptions.get(i), 100, 500 + i * 100);
		}
	}
	
	private void executeChoice() // select menu option
	{
		if (this.cursorIndex == 0)
		{
			manager.setCurrentState(StateManager.TUTORIAL);
		}
		else if (this.cursorIndex == 1)
		{
			manager.setCurrentState(StateManager.LOGIN);
		}
		else if (this.cursorIndex == 2)
		{
			manager.setCurrentState(StateManager.REGISTER);
		}
		
		else if (this.cursorIndex == 3)
		{
			System.exit(0);
		}
		// TODO: add more menu options
	}
	
	public void keyPressed(int keyCode)
	{
		if(keyCode == KeyEvent.VK_ENTER) // proceed
		{
			executeChoice();
		}
		if(keyCode == KeyEvent.VK_DOWN) // proceed
		{
			if(this.cursorIndex != this.menuOptions.size() - 1)
			{
				cursorIndex += 1;
			}
		}
		if(keyCode == KeyEvent.VK_UP) // proceed
		{
			if(this.cursorIndex != 0)
			{
				cursorIndex -= 1;
			}
		}
	}
	
	public void keyReleased(int keyCode)
	{
		
	}
}
