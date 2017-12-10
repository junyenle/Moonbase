package Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Driver.GamePanel;

public class Background 
{
	private BufferedImage image;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double moveScale;
	
	public Background(String filename, double movespeed)
	{
		try
		{
			this.image = ImageIO.read(getClass().getResourceAsStream(filename));
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void setPos(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy)
	{
		this.x = (x * moveScale) % GamePanel.WIDTH; // x movement
		this.y = (y * moveScale) % GamePanel.HEIGHT; // y movement
	}
	
	public void update()
	{
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D graphics)
	{
		graphics.drawImage(this.image, (int) x, (int) y, null);
		
		if(x < 0) // off screen
		{
			graphics.drawImage(this.image, (int) x + GamePanel.WIDTH, (int) y, null);
		}
		
		if(x < 0) // off screen
		{
			graphics.drawImage(this.image, (int) x - GamePanel.WIDTH, (int) y, null);
		}
	}
	
}
