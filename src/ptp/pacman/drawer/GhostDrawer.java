package ptp.pacman.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import ptp.pacman.Vec2f;
import ptp.pacman.base.Ghost;

/** Draws a ghost in a canvas.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public final class GhostDrawer extends ActorDrawer
{
    private static Color[] colors = {Color.BLUE, Color.CYAN, Color.GREEN,
        Color.MAGENTA, Color.ORANGE, Color.RED, Color.PINK};

    private Color mColor;
    
    /** Constructor. Chooses a random color.
     * @param ghost The ghost to be drawn.
     * @param cellSize The cell size that will use the drawing.
     * */
    public GhostDrawer(Ghost ghost, int cellSize)
    {
        super(ghost, cellSize);
        Random r = new Random();
        mColor = colors[r.nextInt(colors.length)];
    }

    /** Draws a ghost on screen.
     * @see ptp.pacman.drawer.ActorDrawer#draw(Graphics, int, int, long)
     * */
    @Override
    public void draw(Graphics g, int offsetX, int offsetY, long timeMillis)
    {
    	Ghost ghost = (Ghost) mActor;
        Vec2f position = mActor.getPosition();
        int ox = offsetX+(int)(position.x*mCellSize),
            oy = offsetY+(int)(position.y*mCellSize),
            c = mCellSize,
            c12 = c/2,
            c15 = c/5,
            c35 = 3*c/5,
            c45 = 4*c/5,
            c16 = c/6;
        
        // Upper part
        if (ghost.getState() == Ghost.State.SCARED) {
        	g.setColor(Color.GRAY);
        }
        else {
        	g.setColor(mColor);
        }
        
        g.fillArc(ox, oy, mCellSize, mCellSize, 0, 180);
        
        // "Legs"
        int[] xs = {ox, ox+c, ox+c, ox+5*c16, ox+4*c16, ox+3*c16, ox+2*c16, ox+c16, ox};
        int[] ys = {oy+c12, oy+c12, oy+c, oy+c45, oy+c, oy+c45, oy+c, oy+c45, oy+c};
        g.fillPolygon(xs, ys, 9);
        // Eyes
        g.setColor(Color.WHITE);
        g.fillOval(ox+c15, oy+c15, c15, c15);
        g.fillOval(ox+c35, oy+c15, c15, c15);
    }
}
