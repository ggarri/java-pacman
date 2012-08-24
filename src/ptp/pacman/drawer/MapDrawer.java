package ptp.pacman.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ptp.pacman.base.PacmanMap;

/** Draws a map in a canvas. Uses a cache to avoid redrawing all elements on every frame (regenerates the background only when the cell size changes).
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public final class MapDrawer
{
	private final PacmanMap mMap;
	private int mCellSize;
	private BufferedImage mBackground;
	
    /** Constructor. Takes the map to draw.
     * @param actor The map to be drawn.
     * */
    public MapDrawer(PacmanMap map)
    {
    	mMap = map;
    }
    
    /** Sets the cell size to draw the map. Regenerates the background. 
     * @return cellSize The new cell size.
     * */
    public void setCellSize(int size)
    {
    	mCellSize = size;
    	generateBackground();
    }
    
    private void generateBackground()
    {
        mBackground = new BufferedImage(mCellSize*mMap.getWidth(),
                						mCellSize*mMap.getHeight(),
                						BufferedImage.TYPE_INT_RGB);
        Graphics g = mBackground.getGraphics();
        int ox = 0, oy = 0;
        int xpol5[] = new int[5],
            ypol5[] = new int[5],
            xpol3[] = new int[3],
            ypol3[] = new int[3];
        int c =   mCellSize,
            c12 = mCellSize/2,
            c14 = mCellSize/4,
            c34 = 3*mCellSize/4;
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, mBackground.getWidth(), mBackground.getHeight());
        g.setColor(java.awt.Color.BLACK);
        for(int i=0; i<mMap.getHeight(); ++i) {
            for(int j=0; j<mMap.getWidth(); ++j) {
                char ch = mMap.getCell(j, i);
                switch(ch){
                case PacmanMap.CELL_EMPTY:// do not draw
                   break;
                case PacmanMap.CELL_LINE:
                    g.drawLine(ox, oy+c12, ox+c, oy+c12);
                    break;
                case PacmanMap.CELL_FULL:
                    g.fillRect(ox, oy, c, c);
                    break;
                case PacmanMap.CELL_V_LEFT:
                    g.fillRect(ox, oy, c34, c);
                    break;
                case PacmanMap.CELL_V_RIGHT:
                    g.fillRect(ox+c14, oy, c34, c);
                    break;
                case PacmanMap.CELL_H_TOP:
                    g.fillRect(ox, oy, c, c34);
                    break;
                case PacmanMap.CELL_H_BOTTOM:
                    g.fillRect(ox, oy+c14, c, c34);
                    break;
                case PacmanMap.CELL_DIAG_45_TOP:
                    xpol3[0] = ox;
                    xpol3[1] = ox;
                    xpol3[2] = ox+c34;
                    
                    ypol3[0] = oy;
                    ypol3[1] = oy+c34;
                    ypol3[2] = oy;
                    g.fillPolygon(xpol3, ypol3, 3);
                    break;
                case PacmanMap.CELL_DIAG_45_BOTTOM:
                    xpol5[0] = ox+c14;
                    xpol5[1] = ox+c;
                    xpol5[2] = ox+c;
                    xpol5[3] = ox;
                    xpol5[4] = ox;
                    
                    ypol5[0] = oy;
                    ypol5[1] = oy;
                    ypol5[2] = oy+c;
                    ypol5[3] = oy+c;
                    ypol5[4] = oy+c14;
                    g.fillPolygon(xpol5, ypol5, 5);
                    break;
                case PacmanMap.CELL_DIAG_135_TOP:
                    xpol3[0] = ox+c14;
                    xpol3[1] = ox+c;
                    xpol3[2] = ox+c;
                    
                    ypol3[0] = oy;
                    ypol3[1] = oy;
                    ypol3[2] = oy+c34;
                    g.fillPolygon(xpol3, ypol3, 3);
                    break;
                case PacmanMap.CELL_DIAG_135_BOTTOM:
                    xpol5[0] = ox;
                    xpol5[1] = ox+c34;
                    xpol5[2] = ox+c;
                    xpol5[3] = ox+c;
                    xpol5[4] = ox;
                    
                    ypol5[0] = oy;
                    ypol5[1] = oy;
                    ypol5[2] = oy+c14;
                    ypol5[3] = oy+c;
                    ypol5[4] = oy+c;
                    g.fillPolygon(xpol5, ypol5, 5);
                    break;
                case PacmanMap.CELL_DIAG_225_TOP:
                    xpol5[0] = ox;
                    xpol5[1] = ox+c;
                    xpol5[2] = ox+c;
                    xpol5[3] = ox+c34;
                    xpol5[4] = ox;
                    
                    ypol5[0] = oy;
                    ypol5[1] = oy;
                    ypol5[2] = oy+c34;
                    ypol5[3] = oy+c;
                    ypol5[4] = oy+c;
                    g.fillPolygon(xpol5, ypol5, 5);
                    break;
                case PacmanMap.CELL_DIAG_225_BOTTOM:
                    xpol3[0] = ox + c14;
                    xpol3[1] = ox + c;
                    xpol3[2] = ox + c;
                    ypol3[0] = oy + c;
                    ypol3[1] = oy + c14;
                    ypol3[2] = oy + c;
                    g.fillPolygon(xpol3, ypol3, 3);
                    break;
                case PacmanMap.CELL_DIAG_315_TOP:
                    xpol5[0] = ox;
                    xpol5[1] = ox + c;
                    xpol5[2] = ox + c;
                    xpol5[3] = ox + c14;
                    xpol5[4] = ox;
                    
                    ypol5[0] = oy;
                    ypol5[1] = oy;
                    ypol5[2] = oy + c;
                    ypol5[3] = oy + c;
                    ypol5[4] = oy + c34;
                    g.fillPolygon(xpol5, ypol5, 5);
                    break;
                case PacmanMap.CELL_DIAG_315_BOTTOM:
                    xpol3[0] = ox;
                    xpol3[1] = ox;
                    xpol3[2] = ox + c34;
                    ypol3[0] = oy + c14;
                    ypol3[1] = oy + c;
                    ypol3[2] = oy + c;
                    g.fillPolygon(xpol3, ypol3, 3);
                    break;
                }
                ox += c;

            }
            oy += c;
            ox = 0;
        }
    }
    
    /** Returns the drawing width.
     * @return Its width.
     * */
    public int getWidth()
    {
    	return mBackground.getWidth();
    }
    
    /** Returns the drawing height.
     * @return Its height.
     * */
    public int getHeight()
    {
    	return mBackground.getHeight();
    }
    
    /** Draws the map in the specified position.
     * @param g The canvas where to draw
     * @param offsetX The horizontal offset.
     * @param offsetY The vertical offset.
     * */
    final Color yellowColor = new Color(255, 200, 0);
    public void draw(Graphics g, int offsetX, int offsetY)
    {
    	// Draw background
        g.drawImage(mBackground, offsetX, offsetY, null);

    	// Draw points
        int ox = offsetX, oy = offsetY;
        int c   = mCellSize,
            c12 = mCellSize/2,
            c14 = mCellSize/4,
            c15 = mCellSize/5,
            c25 = mCellSize*2/5;
        
        for(int i=0; i< mMap.getHeight(); ++i) {
            for(int j=0; j<mMap.getWidth(); ++j) {
                char ch = mMap.getCell(j, i);
                switch(ch){
                case PacmanMap.CELL_POINT_BIG:
                    g.setColor(yellowColor);
                    g.fillOval(ox+c14, oy+c14, c12, c12);
                    g.setColor(Color.BLACK);
                    g.drawOval(ox+c14, oy+c14, c12, c12);
                    break;
                case PacmanMap.CELL_POINT_SMALL:
                    g.setColor(yellowColor);
                    g.fillOval(ox+c25, oy+c25, c15, c15);
                    if(c15>2){
	                    g.setColor(Color.BLACK);
	                    g.drawOval(ox+c25, oy+c25, c15, c15);
                    }
                    break;

                }
                ox += c;
            }
            oy += c;
            ox = offsetX;
        }
    }
}
