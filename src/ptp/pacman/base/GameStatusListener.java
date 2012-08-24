package ptp.pacman.base;

/**
 * Interface that contains methods that have to be implemented by classes that show the game state
 * (i. e. a GUI panel).
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public interface GameStatusListener
{
	/** Indicates the time that the users have spent in a level, and the remaining time before they get a GAME OVER.
	 * @param currentSeconds The elapsed time, in seconds
	 * @param remainingSeconds The remaining time, in seconds
	 * */
    public void setTime(int currentSeconds, int remainingSeconds);

	/** Indicates the current map's name.
	 * @param name The name of the map
	 * */
    public void setMapName(String name);

	/** Indicates that the game has just started (i. e. for a new level).
	 * */
    public void gameStarted();
    
	/** Indicates that the game has finished (aka GAME OVER).
	 * */
    public void gameOver();

	public void pacmansWin();
}
