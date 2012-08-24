package ptp.pacman.base;

import ptp.pacman.controller.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with static methods that contains the controllers for the played games.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class GameControllers
{
	private static final List<KeyboardController> mKeyboardControllers = new ArrayList<KeyboardController>();
	private static final List<AbstractGhostController> mAbstractControllers = new ArrayList<AbstractGhostController>();
	
	/** Gives a list with the user keyboard controllers.
	 * @return The keyboard controllers.
	 * */
	public static List<KeyboardController> KeyboardControllers()
	{
		return mKeyboardControllers;
	}
	
	/** Gives a list with the ghost controllers.
	 * @return The ghost controllers.
	 * */
	public static List<AbstractGhostController> GhostControllers()
	{
		return mAbstractControllers;
	}
	
	// Static block that inits the keyboard controllers
	static
	{
        // Create PacMan Controllers
        KeyboardController kc0 = new KeyboardController("Player 1", KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        KeyboardController kc1 = new KeyboardController("Player 2", KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S);
        KeyboardController kc2 = new KeyboardController("Player 3", KeyEvent.VK_ALT, KeyEvent.VK_ALT, KeyEvent.VK_ALT, KeyEvent.VK_ALT);
        KeyboardController kc3 = new KeyboardController("Player 4", KeyEvent.VK_ALT, KeyEvent.VK_ALT, KeyEvent.VK_ALT, KeyEvent.VK_ALT);
        mKeyboardControllers.add(kc0);
        mKeyboardControllers.add(kc1);
        mKeyboardControllers.add(kc2);
        mKeyboardControllers.add(kc3);
	}
	
	/** Static method that creates the ghost controllers given the current game
	 * @param game The current game for which the controllers are created.
	 * */
	public static void InitGhostControllers(Game game)
	{
        //	 Create Ghost Controllers
		mAbstractControllers.clear();
        AbstractGhostController dgc1 = new Dijkstra2GhostController(game);
        AbstractGhostController dgc2 = new Dijkstra2GhostController(game);
        AbstractGhostController dgc3 = new DijkstraGhostController(game);
        AbstractGhostController dgc4 = new Dijkstra2GhostController(game);
        mAbstractControllers.add(dgc1);
        mAbstractControllers.add(dgc2);
        mAbstractControllers.add(dgc3);
        mAbstractControllers.add(dgc4);
	}

	
}
