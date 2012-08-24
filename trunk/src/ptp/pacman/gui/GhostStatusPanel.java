package ptp.pacman.gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ptp.pacman.Util;
import ptp.pacman.base.Actor;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.GhostStatusListener;

/** Class that represents a ghost status panel. Implements the interface GhostStatusListener to show changes for the current ghost.
 *  @see ptp.pacman.gui.ActorPanel
 *  @see ptp.pacman.base.GhostStatusListener
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class GhostStatusPanel extends ActorPanel
    implements GhostStatusListener
{
    private static final long serialVersionUID = 8405840611789568392L;

    private JLabel mPacmanEatenLabel;
    private JLabel mEatenByPacmanLabel;
    
    /** Constructor.
     * @param g The ghost whose information will be represented.
     * */
    public GhostStatusPanel(Ghost g)
    {
        super(g);
        g.setStatusListener(this);
    }
    
    /** @see ptp.pacman.base.GhostStatusListener#setTimesPacmanEaten(int)
     * */
    @Override
    public void setTimesPacmanEaten(int i)
    {
        mPacmanEatenLabel.setText(String.valueOf(i));
    }

    /** @see ptp.pacman.base.GhostStatusListener#setTimesEatenByPacman(int)
     * */
    @Override
    public void setTimesEatenByPacman(int i)
    {
        mEatenByPacmanLabel.setText(String.valueOf(i));
    }
    
    /** @see ptp.pacman.gui.ActorPanel#initActorDependantPanel(Actor)
     * */
    @Override
    protected void initActorDependantPanel(Actor a)
    {
        JPanel ghostPanel = new JPanel();
        ghostPanel.setLayout(new BoxLayout(ghostPanel, BoxLayout.Y_AXIS));
        
        // Times that I have eaten a Pacman
        JPanel pacmanEatenPanel = new JPanel();
        pacmanEatenPanel.setLayout(new BorderLayout());
        pacmanEatenPanel.add(new JLabel(Util.IconWithName("pacman_eaten.png")), 
                BorderLayout.WEST);
        pacmanEatenPanel.add(new JLabel("Pacman eaten: "), BorderLayout.CENTER);
        mPacmanEatenLabel = new JLabel("-");
        pacmanEatenPanel.add(mPacmanEatenLabel, BorderLayout.EAST);
        ghostPanel.add(pacmanEatenPanel);
        
        // Times that I have been eaten by Pacman
        JPanel eatenByPacmanPanel = new JPanel();
        eatenByPacmanPanel.setLayout(new BorderLayout());
        eatenByPacmanPanel.add(new JLabel(Util.IconWithName("eaten_by_pacman.png")), 
                BorderLayout.WEST);
        eatenByPacmanPanel.add(new JLabel("Eaten by Pacman: "), BorderLayout.CENTER);
        mEatenByPacmanLabel = new JLabel("-");
        eatenByPacmanPanel.add(mEatenByPacmanLabel, BorderLayout.EAST);
        ghostPanel.add(eatenByPacmanPanel);
        
        this.add(ghostPanel, BorderLayout.CENTER);
    }
}
