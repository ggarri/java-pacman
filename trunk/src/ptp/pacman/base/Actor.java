package ptp.pacman.base;

import ptp.pacman.Vec2f;
import ptp.pacman.Vec2i;

/**
 * Base class for elements that move in the game.
 * Note that the coordinate system goes from left to right and top to bottom.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public abstract class Actor
{
	/** Enumerate that defines an actor's direction (four main ones, and stopped)
	 * */
    public static enum Direction {
        UP, DOWN, LEFT, RIGHT, STOPPED
    }
    
	/** Enumerate that defines an actor's possible speeds
	 * */
    public static enum Speed {
        VERY_SLOW,
        SLOW,
        NORMAL,
        FAST,
        VERY_FAST
    }
    
    public static final Direction [] DIRECTIONS = {Direction.UP, Direction.DOWN, 
            Direction.LEFT, Direction.RIGHT, Direction.STOPPED};
    
    public static final int     OFFSET_STEPS =       60;
    public static final int     OFFSET_STEPS_1 =     OFFSET_STEPS-1;
    public static final float   OFFSET_STEPS_FLOAT = 60.0f;

    protected boolean mIsAlive;
    protected Direction mDirection, mNextDirection;
    protected Vec2i mCell, mOffset;
    protected Speed mSpeed;
    protected int mSpeedValue;
    protected PacmanMap mMap;
    private ActorController mActorController;
    private boolean mSuccessInLastMovement;
    protected Vec2i mInitialPosition;
    
    /** Base actor constructor.
     * @param initialPos actor's initial position. The actor will come back here when it is eaten
     * @param speed The actor's speed
     * @param map Map where the actor moves
     * @param controller Controller that will be called on every movement to get the next direction
	 * */
    public Actor(Vec2i initialPos, Speed speed, PacmanMap map, ActorController controller)
    {
        mCell = initialPos.copy();
        mOffset = new Vec2i(0,0);
        mDirection = mNextDirection = Direction.STOPPED;
        setSpeed(speed);
        mMap = map;
        mActorController = controller;
        mSuccessInLastMovement = false;
        mIsAlive = true;
        mInitialPosition = initialPos;
    }
    
    /** Gives the current actor's cell
     * @see ptp.pacman.base.Actor#getPosition()
     * @return The current cell position
     * */
    public Vec2i getCell()
    {
        return mCell;
    }
    
    /** Gets the current actor position, given its current cell and its offset.
     * @return The current actor position within a map.
     * */
    public Vec2f getPosition()
    {
        return new Vec2f(mCell.x + (float)mOffset.x/OFFSET_STEPS_FLOAT,
                          mCell.y + (float)mOffset.y/OFFSET_STEPS_FLOAT);
    }
    
    /** Gets the current actor direction.
     * @return The current direction.
     * */
    public Direction getDirection()
    {
        return mDirection;
    }
    
    /** Sets the actor controller. The actor controller will be called in every call to move() to know
     *  where the actor should go when it is in the next intersection (=> the offset is zero).
     * @param ac The new actor controller.
     * */
    public void setController(ActorController ac)
    {
    	mActorController = ac;
    }
    
    /** Sets the current speed for the actor.
     * @param speed The new speed.
     * */
    public void setSpeed(Speed speed)
    {
    	mSpeed = speed;
        switch(speed) {
        case VERY_SLOW: mSpeedValue = 1; break;
        case SLOW: mSpeedValue = 2; break;
        case NORMAL: mSpeedValue = 3; break;
        case FAST: mSpeedValue = 4; break;
        case VERY_FAST: mSpeedValue = 5; break;
        }
    }
    
    /** Tells if the actor collides another one, or not.
     * @param c The actor to check the collision with.
     * @return If both actors collide.
     * */
    public boolean collides(Actor c)
    {
        // Done in two steps to improve detection (first int, then float)
        if(Math.abs(mCell.x-c.mCell.x)<1 && Math.abs(mCell.y-c.mCell.y)<1) {
            final Vec2f p1 = getPosition();
            final Vec2f p2 = c.getPosition();
            if(Math.abs(p1.x-p2.x)<1 && Math.abs(p1.y-p2.y)<1)
                return true;
            else
                return false;
        } else { // Not in close cells
            return false;
        }
    }
    
    /** Tells if the actor could finish its last movement (=> didn't touch a wall).
     * @return If the actor could do its last movement.
     * */
    public boolean successInLastMovement()
    {
        return mSuccessInLastMovement;
    }
    
    /** Returns the current actor controller.
     * @return The actor controller.
     * */
    public ActorController getActorController()
    {
        return mActorController;
    }
    
    /** Abstract method that has to be implemented by subclasses. Returns if the cell at coordinates (x,y) can be traspassed.
     * Note that this is like this because for a Pacman, the line over the Ghosts start position is a wall, but not for the ghosts.
     * @param v Cell coordinates
     * @return If the cell at the given coordinates is a wall.
     * */
    protected abstract boolean checkCellForWall(Vec2i coord);
    
    /** Checks if the actor can move to the given direction.
     * @param direction The direction where the actor wants to move
     * @return If the actor can move to the given direction.
     * */
    private final Vec2i testCell = new Vec2i(),
         				  testOffset = new Vec2i(),
         				  testComp = new Vec2i();
    public final boolean canApplyMovement(Direction direction)
    {
        testCell.set(mCell);
        testOffset.set(mOffset);
        
        final int xdim = mMap.getDimensions().x,
                  ydim = mMap.getDimensions().y;
        final int xmax = xdim-1,
                  ymax = ydim-1;
        switch(direction){
            case UP:
                testOffset.y -= mSpeedValue;
                if(testOffset.y<0){
                    testOffset.y=OFFSET_STEPS-testOffset.y;
                    testCell.y -=1;
                }
                if(testCell.y<0){
                    testCell.y = ymax;
                    testOffset.y = 0;
                }
                break;
            case DOWN:
                testOffset.y += mSpeedValue;
                if(testOffset.y>OFFSET_STEPS_1){
                    testOffset.y=OFFSET_STEPS-testOffset.y;
                    testCell.y +=1;
                }
                if(testCell.y>ymax){
                    testCell.y = 0;
                    testOffset.y = 0;
                }
                break;
            case LEFT:
                testOffset.x -= mSpeedValue;
                if(testOffset.x<0){
                    testOffset.x=OFFSET_STEPS-testOffset.x;
                    testCell.x -=1;
                }
                if(testCell.x<0){
                    testCell.x = xmax;
                    testOffset.x = 0;
                }
                break;
            case RIGHT:
                testOffset.x += mSpeedValue;
                if(testOffset.x>OFFSET_STEPS_1){
                    testOffset.x=testOffset.x - OFFSET_STEPS;
                    testCell.x +=1;
                }
                if(testCell.x>xmax){
                    testCell.x = 0;
                    testOffset.x = 0;
                }
                break;
            case STOPPED:
                return false;
        }
        
        testComp.set(testCell);

        if(testOffset.y>0)
            testComp.y = (++testComp.y)%ydim;
        if(testOffset.x>0)
        	testComp.x = (++testComp.x)%xdim;
        
        if(checkCellForWall(testCell) && checkCellForWall(testComp)){
            return true;
        } else {
            return false;
        }
    }
    
    /** Moves the actor depending on the speed. This method checks if the actor is in an intersection (=>offset=0)
     * and tries to apply the next desired direction. If it fails, keeps moving in the current one.
     * */
    public void move()
    {
        // 1. Get next direction from controller
        if(mActorController != null)
            mNextDirection = mActorController.getNextDirection();
        
        // 2. Check 
        if(mNextDirection==Direction.STOPPED || !mOffset.isZero()){
            mSuccessInLastMovement = canApplyMovement(mDirection);
            if(mSuccessInLastMovement) {
                mCell.set(testCell);
                mOffset.set(testOffset);
            } else {
                mDirection=Direction.STOPPED;
            }
        } else {
            mSuccessInLastMovement = canApplyMovement(mNextDirection);
            if(mSuccessInLastMovement){ // intentar con la nueva
                mDirection = mNextDirection;
                mCell.set(testCell);
                mOffset.set(testOffset);
            } else {
                mSuccessInLastMovement = canApplyMovement(mDirection);
                if(mSuccessInLastMovement) {
                    mCell.set(testCell);
                    mOffset.set(testOffset);
                } else {
                    mDirection=Direction.STOPPED;
                }
            }
        }
    }
    
    /** Restarts actor position to the initial one (i. e. after dying or loading a new level)
     * */
    public void restart()
    {
    	mCell.set(mInitialPosition);
        mOffset.setZero();
    }

}
