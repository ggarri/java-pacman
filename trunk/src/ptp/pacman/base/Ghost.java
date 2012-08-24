package ptp.pacman.base;

import ptp.pacman.Vec2i;

/**
 * Class that represents the ghosts in the game.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public final class Ghost extends Actor
{
	/** Enumerate that defines Ghost attitude: it will follow Pacman to kill it,
	 * or will go away from it.
	 * */
	public static enum State {
        ATTACKING, SCARED ,
    }

	private State mState;
    private GhostStatusListener mStatusListener;
    private int mEatenByPacman, mPacmanEaten;

    /** Ghost constructor.
     * @param initialPos Ghost initial position. The actor will come back here when it is eaten
     * @param speed The ghost's speed
     * @param map Map where the ghost moves
     * @param controller The ghost controller
	 * */
    public Ghost(Vec2i initialPos, Speed speed, PacmanMap map, ActorController controller)
    {
        super(initialPos, speed, map, controller);
        mEatenByPacman = 0;
        mPacmanEaten = 0;
        mState = State.ATTACKING;
    }
    
    /** Sets the status listener for this ghost. Updates its when it's set.
     * @param listener The new status listener.
     * */
    public void setStatusListener(GhostStatusListener listener)
    {
        mStatusListener = listener;
        listener.setTimesEatenByPacman(mEatenByPacman);
        listener.setTimesPacmanEaten(mPacmanEaten);
    }
    
    /** Gets the ghost state (normal or scared)
     * @return This ghost's state.
     * */
    public State getState()
    {
    	return mState;
    }
    
    /** Sets this ghost's state.
     * @param state The new state
     * */
    public void setState(State state)
    {
    	mState = state;
    }
    
    /** Returns if the cell at coordinates (x,y) can be traspassed (they are not walls)
     * @param v Cell coordinates
     * @return If the cell at the given coordinates is a wall.
     * */
    @Override
    protected boolean checkCellForWall(Vec2i v)
    {
        char cell = mMap.getCell(v.x, v.y);
    	return !PacmanMap.IsWall(cell);
    }
    
    /** Increments the eaten Pacman counter and goes back to the start position.
     * Updates the status listener, if set.
     * */
    public void capture()
    {
    	++mEatenByPacman;
    	if(mStatusListener!=null)
            mStatusListener.setTimesEatenByPacman(mEatenByPacman);
    }
    
    /** Increments the number of times that the ghost has been eaten by a Pacman, and comes back to the initial position.
     * Updates the status listener, if set.
     * */
	public void die() 
	{
		++mEatenByPacman;
		restart();

        if(mStatusListener!=null)
            mStatusListener.setTimesEatenByPacman(mEatenByPacman);
    }
}
