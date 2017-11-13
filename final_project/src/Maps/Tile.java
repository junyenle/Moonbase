package Maps;

import java.awt.image.BufferedImage;

public class Tile 
{
	private BufferedImage graphic;
	private int type; // 1 blocked, 0 nonblocked

	public Tile(BufferedImage graphic, int type)
	{
		this.graphic = graphic;
		this.type = type;
	}
	
	public BufferedImage getGraphic()
	{
		return this.graphic;
	}
	
	public int getType()
	{
		return this.type;
	}
	
}
