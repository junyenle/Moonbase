package StateManager;

import java.awt.Graphics2D;
import java.util.Vector;

public class StateManager 
{	
	// #DEFINE
	public static final int MENU = 0;
	public static final int TUTORIAL = 1;
	public static final int REGISTER = 2;
	public static final int LOGIN = 3;
		
	private Vector<GameState> states = new Vector<GameState>(); // vector of game states
	private int currentStateIndex = MENU; // index of the current state in the states vector
	
	public StateManager()
	{
		states.add(new MenuState(this));
		states.add(new Tutorial(this));
		states.add(new RegisterState(this));
		states.add(new LoginState(this));
	}
	
	// set a new state to be the current state
	public void setCurrentState(int state)
	{
		currentStateIndex = state;
		states.get(currentStateIndex).setup();
	}
	
	// update the current game state
	public void update()
	{
		states.get(currentStateIndex).update();
	}
	
	// sends graphics of current state to screen
	public void draw(Graphics2D graphics)
	{
		states.get(currentStateIndex).draw(graphics);
	}
	
	public void keyPressed(int keyCode)
	{
		states.get(currentStateIndex).keyPressed(keyCode);
	}
	
	public void keyReleased(int keyCode)
	{
		states.get(currentStateIndex).keyReleased(keyCode);
	}
	
	
}
