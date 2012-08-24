package ptp.pacman.controller;

import java.util.ArrayList;

import ptp.pacman.Vec2i;
import ptp.pacman.base.Actor.Direction;
import ptp.pacman.base.Pacman.State;
import ptp.pacman.base.Game;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.Pacman;
import ptp.pacman.base.PacmanMap;

/** Ghost controller that uses a Dijkstra algorithm to follow Pacmans and escape from them when they are in state eater.
 * This is just a reference implementation and is not used, please see Dijkstra2GhostController.
 *  @see ptp.pacman.controller.Dijkstra2GhostController
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class DijkstraGhostController extends AbstractGhostController
{
    static int sIndex = 1;
    static int MAX_COST = 99;
    
    private final Vec2i mLastPacmanPosition = new Vec2i(-1,-1);
    private final ArrayList<Vec2i> mPath = new ArrayList<Vec2i>();
    private final Vec2i mNextCell = new Vec2i(-1,-1);
    private Direction mLastDirection = Direction.STOPPED;
    
    /** Constructor.
     * @param g The current game
     * */
    public DijkstraGhostController(Game g)
    {
        super(g);
        mControllerName = "Dijkstra #"+sIndex;
        ++sIndex;
    }
    
    /** Returns the next direction depending on the closest Pacman's state (will follow it if its state is "normal",
     * and will escape from it if its state is "eater"), using Dijkstra's algorithm.
     * @see http://en.wikipedia.org/wiki/Dijkstra's_algorithm
     * */
    @Override
    public Direction getNextDirection()
    {
    	Pacman closestPacman = closestPacman();
        Vec2i closestPacmanCell = closestPacman.getCell();
       
        if (closestPacman.getState() == Pacman.State.EATER) {
        	mGhost.setState(Ghost.State.SCARED);
        }
        else {
        	mGhost.setState(Ghost.State.ATTACKING);
        }
        
        if(closestPacmanCell.compareTo(mLastPacmanPosition) != 0) {
            calculatePath();
            mNextCell.set( mGhost.getCell() );
        }
        
        if(mPath.size()>0) {
            if(mNextCell.compareTo(mGhost.getCell()) == 0) {
                mNextCell.set( mPath.remove(mPath.size()-1) );
                
                int gx = mGhost.getCell().x,
                    gy = mGhost.getCell().y;
                
                if(mNextCell.x < gx)
                    mLastDirection = Direction.LEFT;
                else if (mNextCell.x > gx)
                    mLastDirection = Direction.RIGHT;
                else if(mNextCell.y < gy)
                    mLastDirection = Direction.UP;
                else if (mNextCell.y > gy)
                    mLastDirection = Direction.DOWN;
            }
            
            return mLastDirection;
        } else {
            return Direction.STOPPED;
        }
    }
    
    /** Calculates the path to the closest Pacman.
     * */
    // TODO: this could be optimized (for example, not creating the whole matrix)
    private void calculatePath()
    {
        PacmanMap map = mGame.getMap();
        Vec2i ghostPos = mGhost.getCell();
        Pacman closestPacman = closestPacman();
        mLastPacmanPosition.set(closestPacman.getCell());
        
        int mapHeight = map.getDimensions().y,
            mapWidth  = map.getDimensions().x;
        final Vec2i aux = new Vec2i(0,0);
        ArrayList<ArrayList<MatrixCell>> matrix = new ArrayList<ArrayList<MatrixCell>>();
        for(int i=0; i<mapHeight; ++i){
            ArrayList<MatrixCell> row = new ArrayList<MatrixCell>(mapWidth);
            aux.y = i;
            for(int j=0; j<mapWidth; ++j) {
                aux.x = j;
                
                int distanceValue;
                if(map.isWall(j, i)) {
                	distanceValue = MAX_COST;
                	
                } else {
                    if(closestPacman.getState() == State.NORMAL)
                    	distanceValue = aux.manhattanLength(mLastPacmanPosition);
                    else
                    	distanceValue = MAX_COST - aux.manhattanLength(mLastPacmanPosition);
                }
                
                row.add(new MatrixCell(j, i, distanceValue));
            }
            matrix.add(row);
        }
        
//        if(closestPacman.getState() == State.EATER){
//        	System.out.println(closestPacman.getState());
//	        for(int i=0; i<mapHeight; ++i){
//	            for(int j=0; j<mapWidth; ++j) {
//	            	if(closestPacman.getCell().compareTo(new Vec2i(j,i))==0)
//	            		System.out.print("PP" + " ");
//	            	else if (mGhost.getCell().compareTo(new Vec2i(j,i))==0)
//	            		System.out.print("GG" + " ");
//	            	else if (map.isWall(j, i))
//	            		System.out.print("  " + " ");
//	            	else
//	            		System.out.print(matrix.get(i).get(j).distance + " ");
//	            }
//	            System.out.println();
//	        }
//	        
//	        @SuppressWarnings("unused")
//			int i = 5;
//        }

        
        MatrixCell current = matrix.get(ghostPos.y).get(ghostPos.x);
        current.cost = 0;
        
        PriorityQueue queue = new PriorityQueue();
        queue.update(current);
        
        while(!queue.isEmpty() && current.compareTo(mLastPacmanPosition)!=0) {
            current = queue.pop();
            ArrayList<MatrixCell> neighbours = neighbours(current.x, current.y, matrix, map);
            for(MatrixCell mc : neighbours){
                int cost = current.cost+1 + mc.distance;
                if (cost < mc.cost) {
                	mc.cost = current.cost+1;
                    mc.parent = current;
                    queue.update(mc);
                }
            }
        }
        
        mPath.clear();
        if(current != null) {
            while(current.parent != null) {
                mPath.add(current);
                current = current.parent;
//                if(closestPacman.getState() == State.EATER)
//                	System.out.println(current.x + "," + current.y + "->" + current.distance + "," + current.cost);
            }
        }
        
//        if(closestPacman.getState() == State.EATER){
//        	@SuppressWarnings("unused")
//			int i = 5;
//        }
    }
    
    private final ArrayList<MatrixCell> neighbours = new ArrayList<MatrixCell>(4);
    private final ArrayList<MatrixCell> neighbours(int x, int y, 
            ArrayList<ArrayList<MatrixCell>> matrix, PacmanMap map)
    {
        neighbours.clear();
        final int mapHeight = map.getDimensions().y,
                   mapWidth  = map.getDimensions().x;
        
        if(x>0 && !map.isWall(x-1, y))
            neighbours.add(matrix.get(y).get(x-1));
        if(x<mapWidth-1 && !map.isWall(x+1, y))
            neighbours.add(matrix.get(y).get(x+1));
        
        if(y>0 && !map.isWall(x, y-1))
            neighbours.add(matrix.get(y-1).get(x));
        if(y<mapHeight-1 && !map.isWall(x, y+1))
            neighbours.add(matrix.get(y+1).get(x));
        
        return neighbours;
    }
    
    private static final class MatrixCell extends Vec2i
    {
        MatrixCell parent;
        int cost;
        final int distance;
        
        public MatrixCell(int x, int y, int distance)
        {
            super(x,y);
            this.distance = distance;
            this.cost = MAX_COST;
        }
    }
    
    private static final class PriorityQueue
    {
        // Ordered from max to min cost
        private final ArrayList<MatrixCell> cells = new ArrayList<MatrixCell>();
        
        public MatrixCell pop()
        {
            MatrixCell mc = cells.get(cells.size()-1);
            cells.remove(cells.size()-1);
            return mc;
        }
        
        public void update(MatrixCell c)
        {
            cells.remove(c);
            int i = 0;
            for(; i<cells.size(); ++i) {
                MatrixCell cell = cells.get(i);
                if(cell.cost+cell.distance<c.cost+c.distance)
                    break;
            }
            cells.add(i, c);
        }
        
        public boolean isEmpty()
        {
            return cells.isEmpty();
        }
    }
    
}
