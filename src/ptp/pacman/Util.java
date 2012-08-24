package ptp.pacman;

import javax.swing.ImageIcon;
import ptp.pacman.base.Actor.Direction;

/**
 * Class that contains useful application-wide static values and methods.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class Util
{
    public static final String ICON_BASE = "res/icons/";
    public static final String MAP_BASE = "res/maps/";
    
    /** Returns an icon with the given filename, adding the base path to its name.
     * @param name The icon name
     * @return An ImageIcon created form the given file.
     * */
    public static final ImageIcon IconWithName(String name)
    {
        return new ImageIcon(ICON_BASE+name);
    }
    
    /** Returns the full path for a map's name.
     * @param name The map file name
     * @return The path for the given name.
     * */
    public static final String MapPathWithName(String name)
    {
        return MAP_BASE + name;
    }
    
    /** Returns the opposite direction to the given one.
     * @param d The direction for which we want to know the opposite.
     * @return The opposite direction for the given one.
     * */
    public static Direction oppositeDirection(Direction d)
    {
        switch(d) {
        case UP:      return Direction.DOWN;
        case DOWN:    return Direction.UP;
        case LEFT:    return Direction.RIGHT;
        case RIGHT:   return Direction.LEFT;
        default:
        case STOPPED: return Direction.STOPPED;
        }
    }

}
