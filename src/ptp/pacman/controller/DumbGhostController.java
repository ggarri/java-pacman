package ptp.pacman.controller;

import ptp.pacman.base.Game;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.Pacman;
import ptp.pacman.base.Actor.Direction;


/** Ghost controller generates a random direction. Ghosts following this algorithm are too stupid to live.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class DumbGhostController extends AbstractGhostController
{
    private static int sIndex = 1;
    
    /** Constructor.
     * @param g The current game
     * */
    public DumbGhostController(Game g)
    {
    	super(g);
        mControllerName = "Dumb #"+sIndex;
        ++sIndex;
    }
    
    /** Returns a random direction. It's stupid, but smart enough to know if it should be scared or not.
     * If Pacman's state is eater, ghosts controlled with this controller will just act as blonde-american-girls in zombie
     * films: they will scream but won't do anything else.
     * */
    @Override
    public Direction getNextDirection()
    {
    	Pacman closestPacman = closestPacman();
    	
    	if (closestPacman.getState() == Pacman.State.EATER) {
        	mGhost.setState(Ghost.State.SCARED);
        } else {
        	mGhost.setState(Ghost.State.ATTACKING);
        }
    	
        if(mGhost==null) {
            generateRandomDifferentDirection();
        } else if (!mGhost.successInLastMovement()) {
            generateRandomDifferentDirection();
        } else { // Can change, or not, with a probability of 0.05
            if(mRandom.nextFloat() < 0.05) {
                generateRandomDirection();
            }
        }
        
        return mDirection;
    }
}
