package ptp.pacman.controller;

import ptp.pacman.Vec2i;
import ptp.pacman.base.Actor.Direction;
import ptp.pacman.base.Game;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.Pacman;

/** A ghost controller that follows the closest Pacman using the Manhattan distance algorithm.
 * Ghosts that use this algorithm usually get trapped in corners.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class ManhattanGhostController extends AbstractGhostController
{
    static int sIndex = 1;
    
    /** Constructor.
     * @param g The current game
     * */
    public ManhattanGhostController(Game g)
    {
        super(g);
        mControllerName = "Manhattan #"+sIndex;
        ++sIndex;
    }
    
    /** Returns the next direction depending on the Manhattan distance to the closest Pacman.
     * @see http://en.wikipedia.org/wiki/Manhattan_distance
     * */
    @Override
    public Direction getNextDirection()
    {
    	Pacman closestPacman = closestPacman();
        // 1. Get ghost and closest pacman positions
        Vec2i pp = closestPacman.getCell(),
              gp = mGhost.getCell();
        Vec2i distance = gp.distance(pp);
        
        if (closestPacman.getState() == Pacman.State.EATER) {
        	mGhost.setState(Ghost.State.SCARED);
        }
        else {
        	mGhost.setState(Ghost.State.ATTACKING);
        }
        
        // 2. Get the two possible directions to go to it
        Direction horizontalDirection = Direction.STOPPED,
                  verticalDirection = Direction.STOPPED;
        
        if(pp.x < gp.x)
            horizontalDirection = Direction.LEFT;
        else if(pp.x > gp.x)
            horizontalDirection = Direction.RIGHT;

        if(pp.y < gp.y)
            verticalDirection = Direction.UP;
        else if(pp.y > gp.y)
            verticalDirection = Direction.DOWN;
        
        // 3. Ghost tries to go to the shortest distance
        Direction firstTry = verticalDirection,
                  secondTry = horizontalDirection;
        
        if(distance.x < distance.y){
            firstTry = horizontalDirection;
            secondTry = verticalDirection;
        }
        
        // 4. Try to go to both directions
        // If the ghost is in a corner, it's fucked up.
        if(firstTry != Direction.STOPPED &&
                mGhost.canApplyMovement(firstTry)) {
             return firstTry;
         } else {
             return secondTry;
         }

    }
}
