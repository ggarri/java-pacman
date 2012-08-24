package ptp.pacman.controller;

import java.awt.event.KeyAdapter;

import ptp.pacman.base.Actor.Direction;
import ptp.pacman.base.ActorController;

/** Obviously, a controller that is directed by the keyboard.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class KeyboardController extends KeyAdapter implements ActorController
{
    private Direction mDirection = Direction.STOPPED;
    private final String mPlayerName;
    private int mKeyLeft;
	private int mKeyRight;
	private int mKeyUp;
	private int mKeyDown;
    
	/** Constructor. Takes the four keys that control the actor. If they are repeated, you will probably have a problem ;).
	 * */
    public KeyboardController(String playerName, int keyLeft, int keyRight, 
            int keyUp, int keyDown)
    {
        mPlayerName = playerName;
        mKeyLeft = keyLeft;
        mKeyRight = keyRight;
        mKeyUp = keyUp;
        mKeyDown = keyDown;
    }
    
    @Override
    public Direction getNextDirection()
    {
        return mDirection;
    }

    /** This method captures the user actions and set the next direction to the corresponding key.
     * @param e The key event to analyze.
     * */
    @Override
    public void keyPressed(java.awt.event.KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        System.out.println("Push : " + String.valueOf(keyCode));
        if(keyCode == mKeyLeft) {
            mDirection = Direction.LEFT;
            //System.out.println("Left: "+KeyEvent.getKeyText(keyCode));
        } else if(keyCode == mKeyRight) {
            mDirection = Direction.RIGHT;
            //System.out.println("Right: "+KeyEvent.getKeyText(keyCode));
        } else if(keyCode == mKeyUp) {
            mDirection = Direction.UP;
            //System.out.println("Up: "+KeyEvent.getKeyText(keyCode));
        } else if(keyCode == mKeyDown) {
            mDirection = Direction.DOWN;
            //System.out.println("Down: "+KeyEvent.getKeyText(keyCode));
        }
    }

    @Override
    public String getName()
    {
        return mPlayerName;
    }
    
    /** Returns the key code for a given direction.
     * @param dir A direction.
     * @return The corresponding key code.
     * */
    public int getKeyCodeDirection(Direction dir)
    {
    	int key = -1;
    	switch(dir) {
    	case UP: key = mKeyUp; break;
    	case DOWN : key = mKeyDown; break;
    	case LEFT : key = mKeyLeft; break;
    	case RIGHT : key = mKeyRight; break;
    	}
    	return key;
    }

    /** Sets the direction for a given code.
     * @param dir A direction.
     * @param c A key code.
     * */
    public void setKeyCodeDirection(Direction dir, int c) 
	{
		switch(dir) {
    	case UP: mKeyUp = c; break;
    	case DOWN : mKeyDown = c; break;
    	case LEFT : mKeyLeft = c; break;
    	case RIGHT : mKeyRight = c; break;
    	}
	}
}
