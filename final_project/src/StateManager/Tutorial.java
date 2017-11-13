package StateManager;

import java.awt.Color;
import java.awt.Graphics2D;

import Agents.Player;
import Driver.GamePanel;
import Graphics.Background;
import Maps.Map;


public class Tutorial extends GameState
{
	private Player player;
	private Background bg;
	private Map map;
	protected StateManager manager;
	
	public Tutorial(StateManager manager)
	{
		this.manager = manager;
		setup(); // level setup
	}
	
	public void setup()
	{
		this.bg = new Background("/backgrounds/tutorialbg.gif", 1);
		map = new Map("/tiles/tutorialtiles.gif", "/maps/tutorialmap.txt");
		this.player = new Player(map);
	}
	
	public void update()
	{
		player.update();
		//map.setPosition(GamePanel.WIDTH / 2 - player.getAgentX(), GamePanel.HEIGHT / 2 - player.getAgentY());
	}
	
	public void draw(Graphics2D graphics)
	{
		this.bg.draw(graphics);
		this.map.draw(graphics);
		this.player.draw(graphics);
		
	}
	
	public void keyPressed(int keyCode)
	{
		
	}
	
	public void keyReleased(int keyCode)
	{
		
	}
	
	
	
	
}
