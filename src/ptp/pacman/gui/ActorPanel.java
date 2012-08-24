package ptp.pacman.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import ptp.pacman.Util;
import ptp.pacman.base.Actor;
import ptp.pacman.base.ActorController;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.Pacman;
import ptp.pacman.controller.DijkstraGhostController;
import ptp.pacman.controller.DumbGhostController;
import ptp.pacman.controller.KeyboardController;
import ptp.pacman.controller.ManhattanGhostController;

/** Base class for actor panel. Shows relevant information about an actor and
 *  the controller that supports it. Depending on the kind of actor (Ghost or
 *  Pacman), the information will have a different format. The panel shows an
 *  icon for every controller type.
 *  
 *  Common information format:
 *  Actor type (icon) - Controller/Player name - Controller type
 *  The actor-dependant info will depend on the actor type.
 *  
 *  @See ptp.pacman.gui.PacmanPanel
 *  @See ptp.pacman.gui.GhostPanel
 * */
public abstract class ActorPanel extends JPanel
{
    private static final long serialVersionUID = 4394044399412378717L;
    
    protected JPanel mCommonInfoPanel = new JPanel();
    protected JPanel mActorDependantPanel = new JPanel();
    protected JLabel mActorLabel = new JLabel(" ");
    
    /** Constructor.
     * @param a The actor whose information will be represented.
     * */
    public ActorPanel(Actor a)
    {
        super();
        this.setLayout(new BorderLayout());
        
        initCommonInfoPanel(a);
        initActorDependantPanel(a);
        
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setMinimumSize(new Dimension(300, 20));
        setMaximumSize(new Dimension(500, 80));
    }
    
    // TODO: add NetworkController icon
    private void initCommonInfoPanel(Actor a)
    {
        ActorController controller = a.getActorController();
        
        mCommonInfoPanel.setLayout(new BorderLayout());
        
        // 1. Add icon to the left
        // TODO: get the real color, it's cool ;)
        if(a.getClass() == Pacman.class) {
            mActorLabel.setIcon(Util.IconWithName("pacman.png"));
        } else if (a.getClass() == Ghost.class) {
            mActorLabel.setIcon(Util.IconWithName("ghost.png"));
        }
        mCommonInfoPanel.add(mActorLabel, BorderLayout.WEST);
        
        // 2. Add controller name in the center
        JLabel playerLabel = new JLabel(controller.getName());
        mCommonInfoPanel.add(playerLabel, BorderLayout.CENTER);
        
        // 3. Add controller type icon to the right
        JLabel controllerLabel = new JLabel(" ");
        Class<? extends ActorController> ctrlClass = controller.getClass();
        Icon icon = null;
        if(ctrlClass == KeyboardController.class) {
            icon = Util.IconWithName("controller_kb.png");
        } else if(ctrlClass == DijkstraGhostController.class) {
            icon = Util.IconWithName("controller_dijkstra.png");
        } else if(ctrlClass == DumbGhostController.class) {
            icon = Util.IconWithName("controller_dumb.png");
        } else if(ctrlClass == ManhattanGhostController.class) {
            icon = Util.IconWithName("controller_manhattan.png");
        }
        controllerLabel.setIcon(icon);
        mCommonInfoPanel.add(controllerLabel, BorderLayout.EAST);
        
        this.add(mCommonInfoPanel, BorderLayout.NORTH);
    }
    
    /** Abstract method that is called in the constructor and adds
     * actor-dependent elements to show information for different kinds
     * of actors.
     * @param a The actor whose information will be represented.
     * */
    protected abstract void initActorDependantPanel(Actor a);
}
