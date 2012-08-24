package ptp.pacman.controller;

import java.util.Random;

import ptp.pacman.Vec2i;
import ptp.pacman.base.Actor;
import ptp.pacman.base.ActorController;
import ptp.pacman.base.Game;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.Pacman;
import ptp.pacman.base.Actor.Direction;

/** Abstract base class for ghost controllers.
 * As they are IA-based game controllers, they need to know about the map and the ghost they are controlling.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public abstract class AbstractGhostController implements ActorController
{
    protected final Game mGame;
    protected String mControllerName;
    protected Ghost mGhost;
    protected Direction mDirection = Direction.UP;
    protected Random mRandom = new Random();
    
    /** Constructor.
     * @param g The current game
     * */
    public AbstractGhostController(Game g)
    {
        mGame = g;
    }
    
    /** Sets the ghost for the current controller.
     * @param g The ghost to be controlled.
     * */
    public void setGhost(Ghost g)
    {
        mGhost = g;
    }
    
    /** @see ptp.pacman.base.ActorController#getNextDirection()
     * */
    public abstract Direction getNextDirection();

    /** Generates a random direction and sets it as the current one.
     * */
    protected void generateRandomDirection()
    {
        mDirection = Actor.DIRECTIONS[mRandom.nextInt(4)];
    }
    
    /** Generates a random direction that is different to the previous one, and sets it.
     * */
    protected void generateRandomDifferentDirection()
    {
        int n = mRandom.nextInt(4);
        if(Actor.DIRECTIONS[n] == mDirection) {
            n = (1 + mRandom.nextInt(3)) % 4;
        }
            
        mDirection = Actor.DIRECTIONS[n];
    }
    
    /** @see ptp.pacman.base.ActorController#getName()
     * */
    @Override
    public String getName()
    {
        return mControllerName;
    }
    
    /** Returns the closest Pacman of those who are in the current map
     * */
    public Pacman closestPacman()
    {
        Vec2i minDistance = new Vec2i(0,0);
        int minDistanceSquaredLength = Integer.MAX_VALUE;
        Pacman closestPacman = null;
        for(Pacman p: mGame.getPacmans()) {
        	if (p.isAlive() && !p.isDead()){
	            Vec2i distance = mGhost.getCell().distance(p.getCell());
	            int distanceLength = distance.squaredLength();
	            if(distanceLength < minDistanceSquaredLength) {
	                closestPacman = p;
	                minDistance.set(distance);
	                minDistanceSquaredLength = distanceLength;
	            }
        	}
        }
        
        return closestPacman;
    }

}
