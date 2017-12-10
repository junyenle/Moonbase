package Agents;

import java.awt.image.BufferedImage;

public class Animation 
{
	private BufferedImage[] frames;
	private int current;
	
	private long start;
	private long interval; // in NANOSECONDS
	
	private int iterationCount;
	
	public void Animation()
	{
		this.iterationCount = 0;
	}
	
	public void set(BufferedImage[] input)
	{
		this.frames = input;
		this.current = 0;
		start = System.nanoTime();
		this.iterationCount = 0;
	}
	
	public void setInterval(long interval)
	{
		this.interval = interval;
	}
	
	public void setCurrentFrame(int input)
	{
		this.current = input;
	}
	
	public void update()
	{
		if(System.nanoTime() - this.start > this.interval)
		{
			this.current += 1;
			this.start = System.nanoTime();
		}
		if(this.current == this.frames.length)
		{
			// loop back
			this.current = 0;
			this.iterationCount += 1;
		}
	}
	
	public int getFrameNumber()
	{
		return this.current;
	}
	
	public BufferedImage getFrameContent()
	{
		return this.frames[this.current];
	}
	
}
