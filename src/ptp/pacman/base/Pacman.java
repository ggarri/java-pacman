package ptp.pacman.base;

import ptp.pacman.Vec2i;

/**
 * Class that represents the Pacmans in the game.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public final class Pacman extends Actor
{
    public static final int  POINTS_NORMAL = 1;
    public static final int  POINTS_EATER =  3;
    public static final long MILLIS_EATER = 15000;
    
    public static enum State {
        NORMAL, EATER,
    }
    
    protected State mState;
    protected long mStateEaterStartMillis;
    protected int mLives;
    protected int mPoints;
    protected boolean mDead;
    protected PacmanStatusListener mStatusListener;
    
    /** Pacman constructor.
     * @param initialPos Pacman initial position. The actor will come back here when it is eaten
     * @param speed The Pacman's speed
     * @param map Map where the Pacman moves
     * @param controller The pacman controller
	 * */
    public Pacman(Vec2i pos, Speed speed, PacmanMap map, int lives, ActorController controller)
    {
        super(pos, speed, map, controller);
        mState = State.NORMAL;
        mLives = lives;
        mPoints = 0;
        mNextDirection = mDirection;
        mDead = false;
    }
    
    /** Gets the Pacman state (normal or eater)
     * @return This Pacman's state.
     * */
    public State getState()
    {
        return mState;
    }
    
    /** Gets the number of lives that the Pacman has.
     * @return The number of lives.
     * */
    public int getLives()
    {
        return mLives;
    }
    
    /** Tells if the Pacman is alive or not (=>lives>0).
     * @see ptp.pacman.base.Pacman#getLives()
     * @return If the Pacman is alive or not.
     * */
    public boolean isAlive()
    {
        return mLives >= 0;
    }
    
    public boolean isDead()
    {
    	return mDead;
    }
    
    public void restart()
    {
    	super.restart();
    	mDead = false;
    	mLives = 3;
    	mPoints = 0;
    	mState = State.NORMAL;
    	mDirection = mNextDirection = Direction.STOPPED;
    	if(mStatusListener!=null){
            mStatusListener.setLives(mLives);
    		mStatusListener.setPoints(mPoints);
    	}
    }
    
    public void revive(){
    	super.restart();
    	mDead = false;
    	mState = State.NORMAL;
    	mDirection = mNextDirection = Direction.STOPPED;
    }
    
    /** Decrements the number of lives and comes back to the initial position.
     * Updates the status listener, if set.
     * */
    public void die()
    {
    	if(!mDead) {
	        --mLives;
	        mDead = true;
	        if(mStatusListener!=null)
	            mStatusListener.setLives(mLives);
    	}
    }
    
    /** Indicates that a ghost has been eaten, and increments the number of points.
     * Notifies the status listener, if set.
     * */
	public void eat()
	{
		mPoints += 100;
        if(mStatusListener!=null)
            mStatusListener.setPoints(mLives);
	}
    
    /** Gets the number of points.
     * @return The number of points.
     * */
    public int getPoints()
    {
        return mPoints;
    }
    
    /** Increments the number of points.
     * @return The number of points.
     * */
    public void addPoints(int points)
    {
        mPoints += points;
        
        if(mStatusListener!=null)
            mStatusListener.setPoints(mPoints);
    }
    
    /** Sets the status listener for this Pacman. Updates its when it's set.
     * @param listener The new status listener.
     * */
    public void setStatusListener(PacmanStatusListener listener)
    {
        mStatusListener = listener;
        listener.setEater(mState == State.EATER);
        listener.setLives(mLives);
        listener.setPoints(mPoints);
    }

    /** Returns if the cell at coordinates (x,y) can be traspassed (they are not walls or lines)
     * @param v Cell coordinates
     * @return If the cell at the given coordinates is a wall.
     * */
    @Override
    protected boolean checkCellForWall(Vec2i v)
    {
        char cell = mMap.getCell(v.x,v.y);
        return !PacmanMap.IsWallOrLine(cell);
    }
    
    /** Move method. Do not call!
     * @throws Exception 
     * @see ptp.pacman.base.Pacman#move(long)
     * */
    @Override
	public void move()
    {
    	super.move();
    }
    
    /** Moves the Pacman, doing the same ass seen in Actor class.
     * It needs to know the time to update some of the state variables of the Pacman.
     * @see ptp.pacman.base.Actor#move()
     * @param timeMillis The current time, in milliseconds
     * */
    public void move(long timeMillis)
    {
        // 1. Move
        super.move();
        
        // 2. Check for items in my current cell
        final int xdim = mMap.getDimensions().x,
                  ydim = mMap.getDimensions().y;
        int x = mCell.x, y = mCell.y;
        switch(mDirection) {
        case UP: y = (++y)%ydim; break;
        case LEFT: x = (++x)%xdim; break;
        }
        char cell = mMap.getCell(x, y);
        if(!PacmanMap.IsWallOrLine(cell)) {
            cell = mMap.consumePoint(x, y);
            if(cell == PacmanMap.CELL_POINT_SMALL) {
                if(mState == State.NORMAL)
                    addPoints(POINTS_NORMAL);
                else
                    addPoints(POINTS_EATER);
            } else if (cell == PacmanMap.CELL_POINT_BIG) {
                mState = State.EATER;
                mStateEaterStartMillis = timeMillis;
            }
        }
        
        if(mState == State.EATER) {
            if(timeMillis - mStateEaterStartMillis > MILLIS_EATER) {
                mState = State.NORMAL;
            } else {
            }
        }
    }
}
