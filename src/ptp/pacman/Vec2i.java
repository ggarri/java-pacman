package ptp.pacman;

/**
 * Class that represents a 2D vector composed of integer values.
 * The coordinates are represented as "int" for efficiency.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class Vec2i implements Comparable<Vec2i>
{
    public int x, y;
    
    /** Constructor. Sets values to zero.
     * */
    public Vec2i()
    {
    	x = 0;
    	y = 0;
    }
    
    /** Constructor with initialization values.
     * @param x Value of the first component of the vector
     * @param y Value of the second component of the vector
     * */
    public Vec2i(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /** Copy constructor. Creates a Vec2i whose values are copies of another one.
     * @param vi Vector whose values are about to be copied.
     * */
    public Vec2i(Vec2i vi)
    {
        this.x = vi.x;
        this.y = vi.y;
    }
    
    /** Copy constructor. Creates a Vec2i whose values are truncated of the ones of the target 
     * @see ptp.pacman.Vec2f
     * @param vi Vector whose values are about to be truncated and copied.
     * */
    public Vec2i(Vec2f v)
    {
        this.x = (int) v.x;
        this.y = (int) v.y;
    }
    
    /** Creates a copy of the current vector.
     * @return A new Vec2i whose values are copies of the current one.
     * */
    public Vec2i copy()
    {
        return new Vec2i(this);
    }
    
    /** Says wether the components of the vector are zero (x == y == 0).
     * @return Wether the vector equals (0, 0)
     * */
    public boolean isZero()
    {
        return x==0 && y == 0;
    }
    
    /** Sets vector's values (x and y) to 0.
     * @see ptp.pacman.Vec2i#set(int, int)
     * */
    public void setZero()
    {
        set(0,0);
    }
    
    /** Sets vector's values (x and y) to the given values.
     * @param x New value for the x component
     * @param y New value for the y component-
     * */
    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /** Sets vector's values (x and y) to the values of the given Vec2i.
     * @param v Vector whose components are going to be copied
     * */
    public void set(Vec2i v)
    {
        this.x = v.x;
        this.y = v.y;
    }
    
    /** Sets vector's values (x and y) to the truncated values of the given Vec2f.
     * @param v Vector whose components are going to be truncated and copied
     * */
    public void set(Vec2f v)
    {
        this.x = (int)v.x;
        this.y = (int)v.y;
    }
    
    /** Add the given values to the corresponding coordinates of the Vec2i object.
     * @param x New value for the x component
     * @param y New value for the y component
     * */
    public void add(int x, int y)
    {
        this.x += x;
        this.y += y;
    }
    
    /** Add the values of the given Vec2i to the corresponding coordinates of this one.
     * @see ptp.pacman.Vec2i#add(int, int)
     * @param v Vector whose values are being added to the current ones
     * */
    public void add(Vec2i v)
    {
        this.x += v.x;
        this.y += v.y;
    }
    
    /** Add the (truncated) given values to the corresponding coordinates of the Vec2i object.
     * @see ptp.pacman.Vec2i#add(Vec2i)
     * @param v Vector whose values are being added to the current ones
     * */
    public void add(Vec2f v)
    {
        this.x += (int) v.x;
        this.y += (int) v.y;
    }
    
    /** Returns the length of the vector (its module)
     * @return The length of the vector.
     * */
    public double length()
    {
        return Math.sqrt(x*x+y*y);
    }
    
    /** Returns the squared length of the vector (it just doesn't make any square root).
     * @return The squared length of the vector.
     * */
    public int squaredLength()
    {
        return x*x+y*y;
    }
    
    /** Returns the distance to another vector (just the difference of the coordinates).
     * @param v The vector to get the distance to.
     * @return A new Vec2i that contains the distance between this and v.
     * */
    public Vec2i distance(Vec2i v)
    {
        return new Vec2i(x - v.x, y - v.y);
    }
    
    /** Returns the module of distance to another vector (just the difference of the coordinates).
     * @see ptp.pacman.Vec2i#distance(Vec2i)
     * @see ptp.pacman.Vec2i#length()
     * @param v The vector to get the distance to.
     * @return The module of the calculated distance.
     * */
    public double distanceLength(Vec2i v)
    {
        int dx = this.x - v.x,
            dy = this.y - v.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    /** Returns the Manhattan distance length to another vector
     * @see ptp.pacman.Vec2i#distance(Vec2i)
     * @see ptp.pacman.Vec2i#distanceLength(Vec2i)
     * @param v The vector to get the Manhattan distance to.
     * @return The module of the calculated Manhattan distance.
     * */
    public int manhattanLength(Vec2i v)
    {
        int dx = this.x - v.x,
            dy = this.y - v.y;
        return Math.abs(dx) + Math.abs(dy);
    }
    
    
    /** Returns a string with format "(x, y)".
     * @returns A string with a representation of the current vector.
     * */
    @Override
    public String toString()
    {
        return "("+x+", "+y+")";
    }
    
    // TODO: improve comparison
    /** Compares to vectors. If the coordinates are different, returns always 1.
     * @returns 0 If both have the same values for x and y coordinates, 1 in other case.
     * */
    @Override
    public int compareTo(Vec2i o)
    {
        if(x == o.x && y == o.y) {
            return 0;
        } else {
            return 1;
        }
    }
}
