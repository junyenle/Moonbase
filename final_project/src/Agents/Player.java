package Agents;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Maps.Map;

public class Player extends Agent
{
	private ArrayList<BufferedImage[]> sprites = new ArrayList<BufferedImage[]>();
	private static final int STAND = 0;
	private static final int WALK = 1;
	private final int[] frameCount = {1, 1};
	
	public Player(Map map)
	{
		this.map = map;
		this.agentWidth = 70;
		this.agentHeight = 120;
		int spriteWidth = 120;
		int spriteHeight = 120;
		this.mvtSpd = 0.5;
		this.mvtTer = 1.5;
		this.mvtDec = 0.5;
		this.fallSpd = 0.8;
		this.fallTer = 8.0;
		//this.jmpLimit = -5.0;
		this.jmpDec = 0.5;
		this.jmpStart = -0.5;
		this.direction = Agent.RIGHT;
		this.jumping = false;
		this.falling = true;
		this.thisAction = STAND;
		this.setPosition(1000, 200);
		this.agentDX = 0;
		this.agentDY = 0;
		
		try
		{
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/sprites/playersprites.gif"));
			for(int i = 0; i < 2; i++) // for each animation
			{
				BufferedImage[] temp = new BufferedImage[frameCount[i]]; 
				for(int j = 0; j < frameCount[i]; j++)
				{
					temp[j] = spriteSheet.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
				}
				
				sprites.add(temp); // add each sprite map to sprites arraylist
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		this.animation = new Animation();
		this.animation.set(sprites.get(thisAction));
		this.animation.setInterval(10000000);
	}
	
	public void update()
	{
		//System.out.println("X: " + this.agentDX);
		//System.out.println("Y: " + this.agentDY);
		// movement
		if(this.direction == Agent.LEFT)
		{
			this.agentDX -= this.mvtSpd;
			if(this.agentDX < -this.mvtTer)
			{
				this.agentDX = -this.mvtTer;
			}
		}
		else if(this.direction == Agent.RIGHT)
		{
			this.agentDX += this.mvtSpd;
			if(this.agentDX > this.mvtTer)
			{
				this.agentDX = this.mvtTer;
			}
		}
		else // stopping
		{
			if(this.agentDX > 0)
			{
				this.agentDX -= this.mvtDec;
				if(this.agentDX < 0)
				{
					this.agentDX = 0;
				}
			}
			else if(this.agentDX < 0)
			{
				this.agentDX += this.mvtDec;
				if(this.agentDX > 0)
				{
					this.agentDX = 0;
				}
			}
		}
		
		// jumping
		if(this.jumping && !this.falling)
		{
			this.agentDY = this.jmpStart;
			this.falling = true;
		}
		
		if(this.falling)
		{
			//System.out.println("Currently falling");
			//System.out.println("DY: " + agentDY);
			//System.out.println("and adding " + fallSpd);
			this.agentDY += this.fallSpd;
			if(agentDY > 0)
			{
				this.jumping = false;
			}
			if(agentDY < 0 && !this.jumping)
			{
				agentDY += jmpDec;
			}
			if(agentDY > fallTer)
			{
				//System.out.println("setting to fallTer");
				agentDY = fallTer;
			}
			
		}
		
		checkBlockCollisions();
		setPosition(this.agentDyX, this.agentDyY);
		
		// animations
		if(this.agentDX != 0) // moving
		{
			if(this.thisAction != WALK)
			{
				this.thisAction = WALK;
				animation.set(sprites.get(WALK));
				animation.setInterval(50000000);
			}
		}
		else // idle
		{
			if(this.thisAction != STAND)
			{
				this.thisAction = STAND;
				animation.set(sprites.get(STAND));
				animation.setInterval(10000000);
			}
		}
	}
	
	public void draw(Graphics2D graphics)
	{
		setMapPos();
		
		if(this.direction == Agent.RIGHT)
		{
			graphics.drawImage(animation.getFrameContent(), (int)(this.agentX + this.mapX - this.agentWidth / 2), (int)(this.agentY + this.mapY - this.agentHeight / 2), null);			
		}
		else
		{
			graphics.drawImage(animation.getFrameContent(), (int)(this.agentX + this.mapX - this.agentWidth / 2 + this.agentWidth), (int)(this.agentY + this.mapY - this.agentHeight / 2), -this.agentWidth, this.agentHeight, null);
		}	
	}
	
	public void keyPressed(int keyCode)
	{
		if(keyCode == KeyEvent.VK_ENTER)
		{
			System.exit(0);
		}
		if(keyCode == KeyEvent.VK_LEFT)
		{
			System.out.println("GOT HERE");
			this.direction = Agent.LEFT;
		}
		if(keyCode == KeyEvent.VK_RIGHT)
		{
			this.direction = Agent.RIGHT;
		}
		if(keyCode == KeyEvent.VK_Z)
		{
			this.jumping = true;
			this.falling = true;
		}
	}
	
	public void keyReleased(int keyCode)
	{
		if(keyCode == KeyEvent.VK_LEFT)
		{
			this.direction = Agent.LEFT;
		}
		if(keyCode == KeyEvent.VK_RIGHT)
		{
			this.direction = Agent.RIGHT;
		}
		if(keyCode == KeyEvent.VK_Z)
		{
			this.jumping = false;
		}
	}	

	
}
