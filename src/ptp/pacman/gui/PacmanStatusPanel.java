package ptp.pacman.gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ptp.pacman.Util;
import ptp.pacman.base.Actor;
import ptp.pacman.base.Pacman;
import ptp.pacman.base.PacmanStatusListener;

/** Class that represents a Pacman status panel.
 * Implements the interface PacmanStatusListener to show changes for the current Pacman.
 *  @see ptp.pacman.gui.ActorPanel
 *  @see ptp.pacman.base.GhostStatusListener
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public final class PacmanStatusPanel extends ActorPanel
    implements PacmanStatusListener
{
    private static final long serialVersionUID = -302618902548179160L;

    private JLabel mLifesLabel;
    private JLabel mPointsLabel;
    
    /** Constructor.
     * @param p The Pacman whose information will be represented.
     * */
    public PacmanStatusPanel(Pacman p)
    {
        super(p);
        p.setStatusListener(this);
    }
    
    /** @see ptp.pacman.base.PacmanStatusListener#setLives(int)
     * */
    @Override
    public void setLives(int lifes)
    {
        mLifesLabel.setText(String.valueOf(lifes));
    }
    
    /** @see ptp.pacman.base.PacmanStatusListener#setPoints(int)
     * */
    @Override
    public void setPoints(int points)
    {
        mPointsLabel.setText(String.valueOf(points));
    }

    /** @see ptp.pacman.base.PacmanStatusListener#setEater(boolean)
     * */
    @Override
    public void setEater(boolean isEater)
    {
        if(isEater) {
            mActorLabel.setIcon(Util.IconWithName("pacman_eater.png"));
        } else {
            mActorLabel.setIcon(Util.IconWithName("pacman.png"));
        }
    }
    
    /** @see ptp.pacman.gui.ActorPanel#initActorDependantPanel(Actor)
     * */
    @Override
    protected void initActorDependantPanel(Actor a)
    {
        JPanel pacmanPanel = new JPanel();
        pacmanPanel.setLayout(new BoxLayout(pacmanPanel, BoxLayout.Y_AXIS));
        
        // Lifes
        JPanel lifesPanel = new JPanel();
        lifesPanel.setLayout(new BorderLayout());
        lifesPanel.add(new JLabel(Util.IconWithName("life.png")), 
                BorderLayout.WEST);
        lifesPanel.add(new JLabel("Lifes: "), BorderLayout.CENTER);
        mLifesLabel = new JLabel("-");
        lifesPanel.add(mLifesLabel, BorderLayout.EAST);
        pacmanPanel.add(lifesPanel);
        
        // Points
        JPanel pointsPanel = new JPanel();
        pointsPanel.setLayout(new BorderLayout());
        pointsPanel.add(new JLabel(Util.IconWithName("coin.png")), 
                BorderLayout.WEST);
        pointsPanel.add(new JLabel("Points: "), BorderLayout.CENTER);
        mPointsLabel = new JLabel("-");
        pointsPanel.add(mPointsLabel, BorderLayout.EAST);
        pacmanPanel.add(pointsPanel);
        
        this.add(pacmanPanel, BorderLayout.CENTER);
    }
}
