package ptp.pacman.base;

/**
 * Interface that contains methods that have to be implemented by elements that the Game state
 * (i. e. a GUI panel, or if you are a true nerd, a console program).
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public interface GameRepresentationListener
{
	/** This method is called when the game needs a refresh (i.e. after a tick()).
	 * The classes that implement this interface can call the getMap(), getGhosts() and getPacmans() methods to draw the elements.
	 * @param g The game to be represented (drawn).
	 * */
    public void refresh(Game g);
}
