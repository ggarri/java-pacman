package ptp.pacman.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import ptp.pacman.base.Game;
import ptp.pacman.base.GameRepresentationListener;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.Pacman;
import ptp.pacman.base.PacmanMap;
import ptp.pacman.drawer.GhostDrawer;
import ptp.pacman.drawer.MapDrawer;
import ptp.pacman.drawer.PacmanDrawer;

/** This JComponent subclass is the panel that draws the current game.
 * Given a game, it will automatically create the map, Pacman and ghost drawers to represent the state of the current game.
 *  @see ptp.pacman.controller.Dijkstra2GhostController
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class GamePanel extends JComponent implements GameRepresentationListener
{
    private static final long serialVersionUID = -5727379450216005804L;
    
    private Game mGame;
    private int mCellSize;
    private MapDrawer mMapDrawer;
    private final List<PacmanDrawer> mPacmanDrawers = new ArrayList<PacmanDrawer>();
    private final List<GhostDrawer> mGhostDrawers = new ArrayList<GhostDrawer>();
    private int mOldWidth = -1, mOldHeight = -1;
    
    /** Constructor. Sets some size limits and its layout.
     * */
    public GamePanel()
    {
        super();
        setPreferredSize(new Dimension(300,300));
        setMinimumSize(new Dimension(300,300));
        setLayout(new BorderLayout());
    }
    
    /** Sets the current game for this panel.
     * @param game The game to be drawn.
     * */
    public void setGame(Game game)
    {
        if(mGame != null)
            mGame.removeRepresentationListener(this);
        mGame = game;
        mMapDrawer = new MapDrawer(mGame.getMap());
        for(Pacman p : mGame.getPacmans()) {
            PacmanDrawer d = new PacmanDrawer(p, 0);
            mPacmanDrawers.add(d);
        }
        for(Ghost g : mGame.getGhosts()) {
            GhostDrawer d = new GhostDrawer(g, 0);
            mGhostDrawers.add(d);
        }
        mGame.setRepresentationListener(this);
    }
    
    /** Sets the size for the current panel. Resizes the panel so that the cells keep their proportion.
     * @param width The new width.
     * @param height The new height.
     * */
    @Override
    public void setSize(int width, int height)
    {
        super.setSize(width, height);
        if(height > 200 && width > 200) {
            // Get image properties
            PacmanMap map = mGame.getMap();
            int prop1 = getWidth() / (int) map.getWidth(),
                 prop2 = getHeight() / (int) map.getHeight();
        
            int newCellSize = prop1 < prop2 ? prop1 : prop2;
            while(newCellSize%4!=0)
                newCellSize-=1;
            
            if(newCellSize != mCellSize) {
                //System.out.println("New dimensions: " + width + " " + height);
                mMapDrawer.setCellSize(newCellSize);
                for(PacmanDrawer pd : mPacmanDrawers) {
                    pd.setCellSize(newCellSize);
                }
                for(GhostDrawer gd : mGhostDrawers) {
                    gd.setCellSize(newCellSize);
                }

            }
            refresh(mGame);
        }
    }
    
    /** Refreshes the panel.
     * @g The game to be drawn.
     * */
    @Override
    public void refresh(Game g)
    {
        if(mGame == g) {
            repaint();
        } else {
            setGame(g);
        }
    }
    
    /** Paints this component. Calls the drawing methods of the *Drawer classes.
     * @param g The graphics canvas where the game is about to be drawn.
     * */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        int width = this.getWidth(),
            height = this.getHeight();
        
        if(width != mOldWidth || height != mOldHeight) {
            setSize(width, height);
        }
        
        // Draw background
        long timeMillis = System.currentTimeMillis();
        final int offX = (width - mMapDrawer.getWidth())/2,
            	   offY = (height - mMapDrawer.getHeight())/2;
        
        // Draw map
        mMapDrawer.draw(g, offX, offY);
        
        // Draw Pacmans
        for(PacmanDrawer pd : mPacmanDrawers) {
            if(!pd.isAnimationRunning())
                pd.startAnimation(timeMillis);
            pd.draw(g, offX, offY, timeMillis);
        }
        
        // Draw ghosts
        for(GhostDrawer gd : mGhostDrawers) {
            gd.draw(g, offX, offY, timeMillis);
        }
    }
}
