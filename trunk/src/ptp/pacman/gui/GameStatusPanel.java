package ptp.pacman.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import ptp.pacman.Util;
import ptp.pacman.base.Game;
import ptp.pacman.base.GameStatusListener;

/**
 * Class that represents a game status panel. Implements the interface GameStatusListener to show changes in the game.
 *  @see ptp.pacman.base.GameStatusListener
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public final class GameStatusPanel extends JPanel implements GameStatusListener
{
    private static final long serialVersionUID = 7884124592184309021L;
    
    // Interface elements
    private final JLabel mMapLabel, mTimeLabel, mTimeLeftLabel;
    private final JButton mNewGame,mPause;
    private final MainWindow mMainWindow;
    private final Game mGame;
    
    /** Default constructor. Takes the game whose changes we are representing, and the main window that contains it.
     * Initializes all subcomponents as well.
     * @param game The current game.
     * @param mw The main window that contains this panel.
     * */
    public GameStatusPanel(Game game, MainWindow mw)
    {
        super();
        this.mGame = game;
        this.setLayout(new BorderLayout());
        this.mMainWindow = mw;
        
        // Left panel: map info
        JPanel westPanel = new JPanel();
        this.add(westPanel, BorderLayout.WEST);
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));
        {
            westPanel.add(new JLabel("Map: "));
            mMapLabel = new JLabel(" ----- ");
            westPanel.add(mMapLabel);
        }
        
        // Center panel: time info
        JPanel centerPanel = new JPanel();
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(new JSeparator(JSeparator.VERTICAL));
        {
            ImageIcon sectionIcon = Util.IconWithName("time.png");
            centerPanel.add(new JLabel(sectionIcon));
            centerPanel.add(new JLabel("Time: "));
            mTimeLabel = new JLabel("-:--");
            centerPanel.add(mTimeLabel);
            centerPanel.add(new JLabel("Remaining: "));
            mTimeLeftLabel = new JLabel("-:--");
            centerPanel.add(mTimeLeftLabel);
        }
        
        // Right panel: pause/continue button
        
        JPanel eastPanel = new JPanel();
        this.add(eastPanel, BorderLayout.EAST);
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
        
        mNewGame = new JButton("New game", Util.IconWithName("new.png"));
        mNewGame.setEnabled(true);
        mNewGame.setFocusable(false);
		mNewGame.addActionListener(new NewGame());
        eastPanel.add(mNewGame);
        
        mPause = new JButton("Pause", Util.IconWithName("pause.png"));
        mPause.setEnabled(true);
        mPause.setFocusable(false);
		mPause.addActionListener(new PauseGame());
        eastPanel.add(mPause);
    }

    /** Triggered when the pause/continue button is pressed.
     * */
    public void setGameRunning()
    {
        if (!mGame.isPaused()) {
            mPause.setText("Pause");
            mPause.setIcon(Util.IconWithName("pause.png"));
        } else {
            mPause.setText("Continue");
            mPause.setIcon(Util.IconWithName("continue.png"));
        }
    }
    
    private static final String StringForSeconds(int seconds)
    {
        if(seconds >= 10)
            return String.valueOf(seconds);
        else
            return "0"+String.valueOf(seconds);
    }
    
    /** @see ptp.pacman.base.GameStatusListener#setTime(int, int)
     * */
    @Override
    public void setTime(int current, int remaining)
    {
        mTimeLabel.setText(String.valueOf(current/60) + ":" +
                 StringForSeconds(current%60));
        mTimeLeftLabel.setText(String.valueOf(remaining/60) +  ":" +
                 StringForSeconds(remaining%60));
        if (remaining < 30)
        	mTimeLeftLabel.setBackground(Color.RED);
        else
        	mTimeLeftLabel.setBackground(Color.BLACK);
    }

    /** @see ptp.pacman.base.GameStatusListener#setMapName(String)
     * */
    @Override
    public void setMapName(String name)
    {
        mMapLabel.setText(name);
    }

    /** @see ptp.pacman.base.GameStatusListener#gameStarted()
     * */
    @Override
    public void gameStarted()
    {
        setGameRunning();
    }
    
    /** @see ptp.pacman.base.GameStatusListener#gameOver()
     * */
    @Override
    public void gameOver()
    {        
        JOptionPane.showMessageDialog(this.getParent(),
        		"Pacmans lose / Ghosts win",
        		"    GAME OVER",
        		JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    /** @see ptp.pacman.base.GameStatusListener#pacmansWin()
     * */
    @Override
    public void pacmansWin()
    {
        JOptionPane.showMessageDialog(this.getParent(),
        		"Pacmans win / Ghosts lose",
        		"    GAME OVER",
        		JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    private final class NewGame implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mMainWindow.restart();
		}
    }
    
    private final class PauseGame implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mGame.pause();
			setGameRunning();
		}
    }

}
