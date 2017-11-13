package StateManager;

import java.awt.Graphics2D;

public abstract class GameState 
{
	// pointer back to the state manager
	protected StateManager manager;
	public abstract void setup();
	public abstract void update();
	public abstract void draw(Graphics2D graphics);
	public abstract void keyPressed(int keyCode);
	public abstract void keyReleased(int keyCode);
	
}
