package ptp.pacman.drawer;

import java.awt.Color;
import java.awt.Graphics;

import ptp.pacman.Vec2f;
import ptp.pacman.base.Pacman;

/** Draws a Pacman in a canvas.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class PacmanDrawer extends ActorDrawer
{
    private static final float MIN_ANGLE = 200.0f;
    
    /** Constructor. Chooses a random color.
     * @param ghost The ghost to be drawn.
     * @param cellSize The cell size that will use the drawing.
     * */
    public PacmanDrawer(Pacman pacman, int cellSize)
    {
        super(pacman, cellSize);
    }
    
    /** Draws a Pacman on screen. Completes mouth animation.
     * @see ptp.pacman.drawer.ActorDrawer#draw(Graphics, int, int, long)
     * */
    @Override
    public void draw(Graphics g, int offsetX, int offsetY, long timeMillis)
    {
        Pacman pacman = (Pacman) mActor;
        if (!pacman.isAlive() || pacman.isDead())
        	return;
        
        // Interpolate between MIN_ANGLE and 360 degrees every 1000 ms
        long timeDiff = (timeMillis - mStartTime)%1000;
        if(timeDiff>500)
            timeDiff = 1000 - timeDiff;
        int angle = (int)(MIN_ANGLE + (360-MIN_ANGLE)*timeDiff/500);
        
        int startAngle = (360-angle)/2;
        switch(pacman.getDirection()){
            case RIGHT:  break;
            case LEFT:   startAngle += 180; break;
            case UP:     startAngle += 90; break;
            case DOWN:   startAngle -= 90; break;
            case STOPPED: startAngle = 0; angle = 360; break;
        }
        
        Vec2f pos = pacman.getPosition();
        
        int cellStartX = (int) (pos.x*mCellSize);
        int cellStartY = (int) (pos.y*mCellSize);
        

    	
        if(pacman.getState()==Pacman.State.NORMAL)
            g.setColor(Color.BLUE);
        else // State.EATER
            g.setColor(Color.RED);
        
        g.fillArc(offsetX + cellStartX,
                  offsetY + cellStartY,
                  mCellSize, mCellSize, startAngle, angle);
        
        g.setColor(Color.BLACK);
        
        g.drawArc(offsetX + cellStartX,
                  offsetY + cellStartY,
                  mCellSize, mCellSize, startAngle, angle);

    }
}
