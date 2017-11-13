package Agents;

import java.awt.Rectangle;

import Maps.Map;

public abstract class Agent 
{

	// map and pos of map
	protected Map map;
	protected int boxDim = 120;
	protected double mapX;
	protected double mapY;
	
	// agent pos
	protected double agentX;
	protected double agentY;
	protected double agentDX;
	protected double agentDY;
	protected double agentDyX;
	protected double agentDyY;
	
	// coll
	protected int agentHeight;
	protected int agentWidth;
	protected Boolean topLeftBlocked;
	protected Boolean topRightBlocked;
	protected Boolean bottomLeftBlocked;
	protected Boolean bottomRightBlocked;
	protected Boolean topBlocked;
	protected Boolean bottomBlocked;
	protected Boolean rightBlocked;
	protected Boolean leftBlocked;
	
	
	// anim and mvmnt
	protected Animation animation; // animate from spritesheet
	protected int thisAction; // what are we doing now?
	protected int lastAction;
	protected Boolean jumping;
	protected Boolean falling;
	protected static final int LEFT = 0;
	protected static final int RIGHT = 1;
	protected int direction; // use LEFT and RIGHT
	protected double mvtSpd;
	protected double mvtTer;
	protected double mvtDec;
	protected double fallSpd;
	protected double fallTer;
	protected double jmpStart;
	protected double jmpLimit;
	protected double jmpLength;
	protected double jmpDec;
	
	public void setBlockCollisions(double x, double y)
	{
		if(map.getType((int)(y - agentHeight / 2) / boxDim, (int)(x) / boxDim) == 1)
		{
			this.topBlocked = true;
		}
		else
		{
			this.topBlocked = false;
		}
		
		if(map.getType((int)(y + agentHeight / 2 - 1) / boxDim, (int)(x) / boxDim) == 1)
		{
			this.bottomBlocked = true;
		}
		else
		{
			this.bottomBlocked = false;
		}
		
		if(map.getType((int)(y) / boxDim, (int)(x + agentWidth / 2 - 1) / boxDim) == 1)
		{
			this.rightBlocked = true;
		}
		else
		{
			this.rightBlocked = false;
		}		
		
		if(map.getType((int)(y) / boxDim, (int)(x - agentWidth / 2) / boxDim) == 1)
		{
			this.leftBlocked = true;
		}
		else
		{
			this.leftBlocked = false;
		}
		
		if(map.getType((int)(y - agentHeight / 2) / boxDim, (int)(x - agentWidth / 2) / boxDim) == 1)
		{
			this.topLeftBlocked = true;
		}
		else
		{
			this.topLeftBlocked = false;
		}
		
		if(map.getType((int)(y - agentHeight / 2) / boxDim, (int)(x + agentWidth / 2 - 1) / boxDim) == 1)
		{
			this.topRightBlocked = true;
		}
		else
		{
			this.topRightBlocked = false;
		}

		if(map.getType((int)(y + agentHeight / 2 - 1) / boxDim, (int)(x - agentWidth / 2) / boxDim) == 1)
		{
			this.bottomLeftBlocked = true;
		}
		else
		{
			this.bottomLeftBlocked = false;
		}

		if(map.getType((int)(y + agentHeight / 2 - 1) / boxDim, (int)(x + agentWidth / 2 - 1) / boxDim) == 1)
		{
			this.bottomRightBlocked = true;
		}
		else
		{
			this.bottomRightBlocked = false;
		}
	}
	
	public void checkBlockCollisions()
	{
		// where are we in the world
		int blockRow = (int) (agentY / boxDim);
		int blockCol = (int) (agentX / boxDim);
		
		// where are we going in the world
		double xTo = agentX + agentDX;
		double yTo = agentY + agentDY;
		
		this.agentDyX = agentX;
		this.agentDyY = agentY;
		
		setBlockCollisions(xTo, agentY);
		if(this.agentDX < 0) // going left
		{
			if(this.topLeftBlocked || this.bottomLeftBlocked)
			{
				this.agentDX = 0;
				this.agentDyX = this.agentX + this.agentWidth / 2;
			}
			else
			{
				this.agentDyX += this.agentDX;
			}
		}
		else if(this.agentDX > 0) // going right
		{
			//this.agentDX = 0;
			if(this.topRightBlocked || this.bottomRightBlocked)
			{
				this.agentDX = 0;
				this.agentDyX = this.agentX - this.agentHeight / 2 + 1;
			}
			else
			{
				this.agentDyX += this.agentDX;
			}
		}
		
		setBlockCollisions(agentX, yTo);
		if(this.agentDY < 0) // jumping
		{
			if(this.topLeftBlocked || this.topRightBlocked)
			{
				this.agentDY = 0;
				this.falling = true;
				this.agentDyY = this.agentY /*+ this.agentHeight / 2*/;
			}
			else
			{
				this.agentDyY -= this.agentDY;
			}
		}
		else if(this.agentDY > 0) // falling
		{
			if(this.bottomLeftBlocked || this.bottomRightBlocked)
			{
				this.agentDY = 0;
				this.falling = false;
				this.agentDyY = this.agentY /*- this.agentHeight / 2 + 1*/;
			}
			else
			{
				this.agentDyY += this.agentDY;
			}
		}
		
		// check if falling
		setBlockCollisions(agentX, yTo);
		this.falling = true;
		if(this.bottomLeftBlocked || this.bottomRightBlocked) // there's ground beneath us
		{
			this.falling = false;
		}

	}
	
	// for projectiles and the like
	public Boolean isCollidingWith(Agent rhs)
	{
		Rectangle lhsBox = new Rectangle((int) (agentX - agentWidth / 2), (int) (agentY - agentHeight / 2), agentWidth, agentHeight);
		Rectangle rhsBox = new Rectangle((int) (rhs.getAgentX() - rhs.getAgentWidth() / 2), (int) (rhs.getAgentY() - rhs.getAgentHeight() / 2), rhs.getAgentWidth(), rhs.getAgentHeight());
		return lhsBox.intersects(rhsBox);
	}
	
	
	
	
	
	// all the getters / setters
	// there are so fucking many and honestly most of them are useless
	
	public void setPosition(double x, double y)
	{
		setAgentX(x);
		setAgentY(y);
	}
	
	public void setDirection(double x, double y)
	{
		setAgentDX(x);
		setAgentDY(y);
	}
	
	// sets our player's version of the map coordinates to where the map is moving - basically a synchro function
	public void setMapPos()
	{
		this.mapX = map.getx();
		this.mapY = map.gety();
	}
	
	public Map getMap() {
		return map;
	}

	public int getTileSize() {
		return boxDim;
	}

	public double getMapX() {
		return mapX;
	}

	public double getMapY() {
		return mapY;
	}

	public double getAgentX() {
		return agentX;
	}

	public double getAgentY() {
		return agentY;
	}

	public double getAgentDX() {
		return agentDX;
	}

	public double getAgentDY() {
		return agentDY;
	}


	public int getAgentHeight() {
		return agentHeight;
	}

	public int getAgentWidth() {
		return agentWidth;
	}

	public Animation getAnimation() {
		return animation;
	}

	public int getThisAction() {
		return thisAction;
	}

	public int getLastAction() {
		return lastAction;
	}

	public Boolean getJumping() {
		return jumping;
	}

	public Boolean getFalling() {
		return falling;
	}

	public int getDirection() {
		return direction;
	}

	public double getMvtSpd() {
		return mvtSpd;
	}

	public double getMvtTer() {
		return mvtTer;
	}

	public double getMvtDec() {
		return mvtDec;
	}

	public double getFallSpd() {
		return fallSpd;
	}

	public double getFallTer() {
		return fallTer;
	}

	public double getJmpLimit() {
		return jmpLimit;
	}

	public double getJmpLength() {
		return jmpLength;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setTileSize(int tileSize) {
		this.boxDim = tileSize;
	}

	public void setMapX(double mapX) {
		this.mapX = mapX;
	}

	public void setMapY(double mapY) {
		this.mapY = mapY;
	}

	public void setAgentX(double agentX) {
		this.agentX = agentX;
	}

	public void setAgentY(double agentY) {
		this.agentY = agentY;
	}

	public void setAgentDX(double agentDX) {
		this.agentDX = agentDX;
	}

	public void setAgentDY(double agentDY) {
		this.agentDY = agentDY;
	}

	public void setAgentHeight(int agentHeight) {
		this.agentHeight = agentHeight;
	}

	public void setAgentWidth(int agentWidth) {
		this.agentWidth = agentWidth;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void setThisAction(int thisAction) {
		this.thisAction = thisAction;
	}

	public void setLastAction(int lastAction) {
		this.lastAction = lastAction;
	}

	public void setJumping(Boolean jumping) {
		this.jumping = jumping;
	}

	public void setFalling(Boolean falling) {
		this.falling = falling;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setMvtSpd(double mvtSpd) {
		this.mvtSpd = mvtSpd;
	}

	public void setMvtTer(double mvtTer) {
		this.mvtTer = mvtTer;
	}

	public void setMvtDec(double mvtDec) {
		this.mvtDec = mvtDec;
	}

	public void setFallSpd(double fallSpd) {
		this.fallSpd = fallSpd;
	}

	public void setFallTer(double fallTer) {
		this.fallTer = fallTer;
	}

	public void setJmpLimit(double jmpLimit) {
		this.jmpLimit = jmpLimit;
	}

	public void setJmpLength(double jmpLength) {
		this.jmpLength = jmpLength;
	}
}
