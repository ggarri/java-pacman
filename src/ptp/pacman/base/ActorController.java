package ptp.pacman.base;

/**
 * Interface to be implemented by actor controllers (that tell the actor the next direction to move on).
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public interface ActorController
{
	/** Gets the next direction to be followed, as soon as the actor is in a intersection.
	 * @return The next direction
	 * */
    public Actor.Direction getNextDirection();
    
    /** Gets the controller name.
     * @return The controller name.
     * */
    public String getName();
}
