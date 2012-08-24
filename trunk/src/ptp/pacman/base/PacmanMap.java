package ptp.pacman.base;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import ptp.pacman.Util;
import ptp.pacman.Vec2i;

public final class PacmanMap
{
	public static final char 
	        CELL_EMPTY =            ' ',
		    // 
		    CELL_POINT_SMALL =      '·',
		    CELL_POINT_BIG =        'o',
		    // 
		    CELL_LINE =             '-',
		    CELL_GHOST_START =      'G',
		    CELL_PACMAN_START =     'P',
		    // 
		    CELL_FULL =             'H',
		    CELL_V_LEFT =           'a',
		    CELL_V_RIGHT =          'b',
		    CELL_H_TOP =            'c',
		    CELL_H_BOTTOM =         'd',
		    
		    CELL_DIAG_45_TOP =      'e',
		    CELL_DIAG_45_BOTTOM =   'f',
		    CELL_DIAG_135_TOP =     'g',
		    CELL_DIAG_135_BOTTOM =  'h',
		    CELL_DIAG_225_TOP =     'i',
		    CELL_DIAG_225_BOTTOM =  'j',
		    CELL_DIAG_315_TOP =     'k',
		    CELL_DIAG_315_BOTTOM =  'l';

    int mHeight, mWidth, mNumPoints;
	char [][] mCells;
	final ArrayList<Vec2i> mStartPositions = new ArrayList<Vec2i>();
    final ArrayList<Vec2i> mGhostStartPositions = new ArrayList<Vec2i>();
    final String mName;
	
    /** Default constructor. Gets map path, reads the file and stores the map representation.
     * @param filename The path of the file that represents the map.
     * */
	public PacmanMap(String filename)
	{
		mName = filename;
		mNumPoints = 0;
		System.out.println(filename);
        try {
        	FileReader fr = new FileReader(Util.MapPathWithName(filename));
            BufferedReader br  = new BufferedReader(fr);
			readFile(br);
			br.close();
	        fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Copy constructor. Creates a copy of a map.
	 * @param map The PacmanMap to be copied.
	 * */
	public PacmanMap(PacmanMap map)
	{
		mName = map.mName;
		mHeight = map.mHeight;
		mWidth = map.mWidth;
		mCells = new char[map.mHeight][map.mWidth];
		for(int i=0 ; i<mHeight; ++i)
			System.arraycopy(map.mCells[i], 0, mCells[i], 0, mWidth);
		
		mStartPositions.addAll(map.mStartPositions);
		mGhostStartPositions.addAll(map.mGhostStartPositions);
	}
	
	/** Returns the map's name.
	 * @return The name.
	 * */
	public String getName()
	{
		return mName;
	}
	
	/** Internal method that reads a map file and generates the map representation in memory.
	 * */
    protected void readFile(BufferedReader in) throws Exception
    {
    	String line;
    	String [] divLine;

        // Read the dimension of the map
        divLine = in.readLine().split(" ",2);
        mWidth = Integer.valueOf(divLine[0]);
        mHeight = Integer.valueOf(divLine[1]);
        mCells = new char[mHeight][mWidth];
        System.out.println("Map : " + mHeight + "," + mWidth);
        
        // Read map cells, adding Pacman and Ghost start cells to their vectors.
        try{
	        for (int i=0 ; i< mHeight; i++) {
	        	line = in.readLine();
	        	if (line.length() != mWidth) 
	        		throw new Exception("Wrong number of columns");
	        	for (int j=0 ; j < mWidth ; j++){
	        		if (line.charAt(j) == CELL_PACMAN_START){
	        		    mStartPositions.add(new Vec2i(j,i));
	        			mCells[i][j] = CELL_EMPTY;
	        		} else if(line.charAt(j) == CELL_GHOST_START) {
                        mGhostStartPositions.add(new Vec2i(j,i));
	        			mCells[i][j] = CELL_EMPTY;
	        		} else {
	        			if(line.charAt(j) == CELL_POINT_SMALL || line.charAt(j) == CELL_POINT_BIG) {
		        			++mNumPoints;
		        		}
	        			mCells[i][j] = line.charAt(j);
	        		}
	        	}
	        }
        }catch(Exception e){
        	throw new Exception("Number of cols read incorrect\n" + e.getMessage());
        }
    }
    
    /** Number of start positions for Pacmans.
     * @return The number of start positions for Pacmans.
     * */
    public int getNumberOfPacmanStartPositions()
    {
        return mStartPositions.size();
    }
    
    /** Returns a Pacman start position, given its index.
     * @param i Index of the position. If it's bigger or equal to the number of positions, THIS GAME WILL EXPLODE.
     * @return The desired Pacman start position.
     * */
    public Vec2i getPacmanStart(int i)
    {
        return mStartPositions.get(i);
    }
    
    /** Returns the first Pacman start position.
     * @return The first Pacman start position.
     * @see ptp.pacman.base.PacmanMap#getPacmanStart(int)
     * */
    public Vec2i getFirstPacmanStart()
    {
        return mStartPositions.get(0);
    }
    
    /** Number of start positions for ghosts.
     * @return The number of start positions for ghosts.
     * */
    public int getNumberOfGhostStartPositions()
    {
        return mGhostStartPositions.size();
    }
    
    /** Returns a ghost start position, given its index.
     * @param i Index of the position. If it's bigger or equal to the number of positions, [CENSORED].
     * @return The desired ghost start position.
     * */
    public Vec2i getGhostStart(int i)
    {
        return mGhostStartPositions.get(i);
    }
    
    /** Returns the first ghost start position.
     * @return The first ghost start position.
     * @see ptp.pacman.base.PacmanMap#getGhostStart(int)
     * */
    public Vec2i getFirstGhostStart()
    {
        return mGhostStartPositions.get(0);
    }
    
    /** Returns a random ghost start position. Makes the game cooler.
     * @return A rando ghost start position.
     * @see ptp.pacman.base.PacmanMap#getGhostStart(int)
     * */
    public Vec2i getRandomGhostStart()
    {
        Random r = new Random();
        int index = r.nextInt(mGhostStartPositions.size());
        return mGhostStartPositions.get(index);
    }
    
    /** Returns the dimensions of the map.
     * @return A ptp.pacman.Vec2i with the width and height of the map.
     * */
    public Vec2i getDimensions()
    {
        return new Vec2i(mWidth, mHeight);
    }
    
    /** Returns the width of the map.
     * @return The width of the map.
     * */
    public int getWidth()
    {
        return mWidth;
    }
    
    /** Returns the height of the map.
     * @return The height of the map.
     * */
    public int getHeight()
    {
        return mHeight;
    }

    /** Method used by a Pacman that consumes a point (if it exists, obviously), given the coordinates of a cell.
     * Please note that its visibility is "package" so it can't be called from outside the base package.
     * This is the only way to modify the map from outside this class => you can't do magic with it ;).
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * */
    char consumePoint(int x, int y)
    {
        char prev = mCells[y][x];
        if(prev == CELL_POINT_SMALL || prev == CELL_POINT_BIG) {
        	--mNumPoints;
            mCells[y][x] = CELL_EMPTY;
            return prev;
        } else {
            return ' ';
        }
    }

    /** Returns a cell, given its coordinates.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @return The value of the cell whose coordinates are (x,y)
     * */
    public char getCell(int x, int y)
    {
        return this.mCells[y][x];
    }
    
    /** Returns if a cell can be considered wall in the current map.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @return If the cell is a wall or not (=> can be trespassed or not)
     * */
    public boolean isWall(int x, int y)
    {
        return IsWall(getCell(x,y));
    }
    
    /** Returns if a cell can be considered wall or the line that closes some boxes, in the current map.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @return If the cell is a wall or line, or not (=> can be trespassed or not)
     * */
    public boolean isWallOrLine(int x, int y)
    {
        return IsWallOrLine(getCell(x,y));
    }
	
    /** Returns if all the points on the current map have been eaten by the Pacmans.
     * @return If there are no more points on the map.
     * */
	public boolean areAllPointsEaten()
	{
		return mNumPoints<=0;
	}
	
	/** Static method that returns if a cell is a wall or not. See wall definitions at the beginning of the file, if you get access to the source code.
     * @param c The value of a cell.
     * @return If the value corresponds to a wall or not.
     * */
    public static boolean IsWall(char c)
    {
        if (c==CELL_EMPTY || c==CELL_POINT_SMALL ||
            c==CELL_POINT_BIG || c==CELL_LINE)
            return false;
        else
            return true;
    }
    
    /** Static method that returns if a cell is a wall or line, or not.
     * @param c The value of a cell.
     * @return If the value corresponds to a wall or a line, or not.
     * */
    public static boolean IsWallOrLine(char c)
    {
        if (c==CELL_EMPTY || c==CELL_POINT_SMALL ||
            c==CELL_POINT_BIG)
            return false;
        else
            return true;
    }
}
