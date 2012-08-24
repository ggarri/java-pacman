package ptp.pacman.base;

/**
 * Interface that contains methods that have to be implemented by elements that show a Pacman's state
 * (i. e. a GUI panel).
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public interface PacmanStatusListener
{
    /** Tells the listener if the Pacman has "Eater" status.
     * @param isEater Wether the Pacman can eat ghosts or not.
     * */
    public void setEater(boolean isEater);
    
    /** Sets the number of remaining lives that the Pacman has.
     * @param lives The number of lifes.
     * */
    public void setLives(int lives);
    
    /** Sets the number of points that the Pacman has.
     * @param lives The number of points.
     * */
    public void setPoints(int points);
}
