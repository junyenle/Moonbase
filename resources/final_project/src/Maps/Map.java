package Maps;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Driver.GamePanel;

public class Map 
{
	// note: maps will be 16x9 (120 px)
	
	// current pos on map
	private double x;
	private double y;
	
	// map specifications
	// screen
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;
	private double scroller = 0.05;
	
	// data
	private int[][] map; // will be populated from file
	private int mapWidth; // pixel size
	private int mapHeight;
	private int tileSize = 120; // tile size
	private int mapRows; // need to set height of map
	private int mapColumns; // need to set width of map
	private int tileRowsOnScreen = 9; 
	private int tileColsOnScreen = 16; 
	
	// tiles
	private BufferedImage tileSheet; // sprite sheet but for tiles
	private int numTiles; // for coordinate access of tilesheet
	private Tile[][] tiles; // will be populated from file - holds resources to populate map
	private int tileCols;
	private int tileRows;
	
	public Map(String tileSource, String mapSource) 
	{
		tileSetup(tileSource);
		mapSetup(mapSource);
		setPosition(0,0);
	}
	
	// imports the tile resources
	public void tileSetup(String filename)
	{
		try
		{
			tileSheet = ImageIO.read(getClass().getResourceAsStream(filename));
			this.tileCols = tileSheet.getWidth() / this.tileSize; // size of resource
			this.tileRows = tileSheet.getHeight() / this.tileSize;
			tiles = new Tile[tileRows][tileCols];
			
			BufferedImage temp;
			for(int i = 0; i < tileCols; i++) // for each column
			{
				temp = tileSheet.getSubimage(i * tileSize, 0, tileSize, tileSize); // get tile
				tiles[0][i] = new Tile(temp, 0 /* nonblocked tile */);
				temp = tileSheet.getSubimage(i * tileSize,  tileSize,  tileSize,  tileSize);
				tiles[1][i] = new Tile(temp, 1 /* blocked tile */);
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void mapSetup(String filename)
	{
		try
		{
			InputStream input = getClass().getResourceAsStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			this.mapRows = Integer.valueOf(br.readLine());
			this.mapColumns = Integer.valueOf(br.readLine());
			this.mapHeight = this.mapRows * tileSize;
			this.mapWidth = this.mapColumns * tileSize;
			map = new int[this.mapRows][this.mapColumns];
			
			this.xmin = GamePanel.WIDTH - this.mapWidth;
			this.xmax = 0;
			this.ymin = GamePanel.HEIGHT - this.mapHeight;
			this.ymax = 0;
			
			for(int i = 0; i < mapRows; i++)
			{
				String rowContent = br.readLine();
				String[] rowSplit = rowContent.split("\\s+");
				for(int j = 0; j < mapColumns; j++)
				{
					map[i][j] = Integer.valueOf(rowSplit[j]);
				}
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int getTileSize()
	{
		return tileSize;
	}
	
	public int getx()
	{
		return (int) x;
	}
	
	public int gety()
	{
		return (int) y;
	}
	
	public int getMapWidth()
	{
		return mapWidth;
	}
	
	public int getMapHeight()
	{
		return mapHeight;
	}
	
	// returns type of tile
	public int getType(int r, int c)
	{
		// get row of tile
		int row = map[r][c] / this.tileCols;
		int col = map[r][c] % this.tileCols;
		return tiles[row][col].getType();
	}
	
	// sets camera pos 
	public void setPosition(double x, double y)
	{
		double deltaX = x - this.x;
		double deltaY = y - this.y;
		this.x += scroller * deltaX;
		this.y += scroller * deltaY;
		
		if(x < xmin)
		{
			x = xmin;
		}
		
		if(x > xmax)
		{
			x = xmax;	
		}
		
		if(y < ymin)
		{
			y = ymin;
		}
		
		if(y > ymax)
		{
			y = ymax;
		}
	}
	
	// render actual map
	public void draw(Graphics2D graphics)
	{
		int rowStart = (int)((-1) * this.y / this.tileSize);
		int colStart = (int)((-1) * this.x / this.tileSize);
		
		for(int i = rowStart; i <  rowStart + this.tileRowsOnScreen; i++)
		{
			if(i >= mapRows)
			{
				break;
			}
			for(int j = colStart; j < colStart + tileColsOnScreen; j++)
			{
				if(j >= mapColumns)
				{
					break;
				}
				//System.out.print(map[i][j] + " ");
				int row = map[i][j] / this.tileCols;
				int col = map[i][j] % this.tileCols;
				graphics.drawImage(tiles[row][col].getGraphic(), (int) this.x + j * tileSize, (int) this.y + i * tileSize, null);
			}
			//System.out.println();
		}
	}
	
	
}
