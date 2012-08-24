package ptp.pacman.base;

/**
 * Interface that contains methods that have to be implemented by elements that show a ghost's state
 * (i. e. a GUI panel).
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public interface GhostStatusListener
{
    /** Set the number of times that this ghost has eaten Pacman (muahahahahahaha).
     * @param times The number of times that it happened.
     * */
    public void setTimesPacmanEaten(int times);
    
    /** Set the number of times that Pacman has eaten this ghost.
     * @param times The number of times that it happened.
     * */
    public void setTimesEatenByPacman(int times);
}
