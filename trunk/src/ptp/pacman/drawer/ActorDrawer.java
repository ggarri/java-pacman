package ptp.pacman.drawer;

import java.awt.Graphics;

import ptp.pacman.base.Actor;

/** Abstract class for objects that take an actor and draw it on a canvas.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public abstract class ActorDrawer
{
    protected final Actor mActor;
    protected long mStartTime;
    protected int mCellSize;
    
    /** Constructor. Takes an actor and the cell size.
     * @param actor The actor to be drawn.
     * @param cellSize The cell size that will use the drawing.
     * */
    public ActorDrawer(Actor actor, int cellSize)
    {
        mActor = actor;
        mCellSize = cellSize;
        mStartTime = -1;
    }
    
    /** Starts the actor animation.
     * @param timeMillis The time (in milliseconds) when the animation starts.
     * */
    public void startAnimation(long timeMillis)
    {
        mStartTime = timeMillis;
    }
    
    /** Stops the actor animation.
     * */
    public void stopAnimation()
    {
        mStartTime = -1;
    }
    
    /** Returns if the animation is running or not.
     * @return If the animation has been started and not stopped.
     * */
    public boolean isAnimationRunning()
    {
        return mStartTime != -1;
    }
    
    /** Sets the cell size to draw the actor.
     * @return cellSize The new cell size.
     * */
    public void setCellSize(int cellSize)
    {
        mCellSize = cellSize;
    }
    
    /** Abstract method to be implemented by subclasses. Draws an actor in the specified position.
     * @param g The canvas where to draw
     * @param offsetX The horizontal offset.
     * @param offsetY The vertical offset.
     * @param timeMillis The time stamp for the animation
     * */
    public abstract void draw(Graphics g, int offsetX, int offsetY,
            long timeMillis);
}
