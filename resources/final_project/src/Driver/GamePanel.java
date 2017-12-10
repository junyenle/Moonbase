package Driver;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import StateManager.StateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener
{
	// window settings
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// state manager
	private StateManager manager;
	
	// game thread and associated data
	private Thread gameThread;
	private Boolean isRunning;
	
	// graphics
	private BufferedImage tempImage;
	private Graphics2D currentGraphics;
	
	// constructor
	public GamePanel()
	{
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() 
	{
		super.addNotify();
		if(gameThread == null) 
		{
			gameThread = new Thread(this);
			addKeyListener(this);
			gameThread.start();
		}
	}
	
	// sets up the game at run
	public void setup() 
	{
		tempImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		currentGraphics = (Graphics2D) tempImage.getGraphics();	
		isRunning = true;
		manager = new StateManager();
		
		
	}
	
	// runs game
	public void run() 
	{
		// set up graphics
		setup();
		
		// will keep track of timing to avoid off-synch the game due to processing time
		long startTime;
		long elapsedTime;
		long waitTime;
		
		// loops and listens for events, updates GUI accordingly
		while(isRunning)
		{
			startTime = System.nanoTime();
			
			update(); // update game state
			readyImage(); 
			updateImage();
			
			elapsedTime = System.nanoTime() - startTime;
			waitTime = targetTime - elapsedTime / 1000000;
			
			try
			{
				if(waitTime < 0)
				{
					waitTime = 10;
				}
				
				Thread.sleep(waitTime);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	

	private void update() 
	{
		manager.update();
	}
	
	// generates image
	private void readyImage() 
	{
		manager.draw(currentGraphics);
	}
	
	// draws image to screen
	private void updateImage() 
	{
		Graphics updatedGraphics= getGraphics();
		updatedGraphics.drawImage(tempImage, 0, 0, null);
		updatedGraphics.dispose();
		
	}



	public void keyTyped(KeyEvent key) 
	{
		// TODO
	}
	
	public void keyPressed(KeyEvent key) 
	{
		manager.keyPressed(key.getKeyCode());		
	}
	
	public void keyReleased(KeyEvent key) 
	{
		manager.keyReleased(key.getKeyCode());		
	}
	
}
