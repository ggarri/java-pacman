package ptp.pacman.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ptp.pacman.base.Game;
import ptp.pacman.base.Actor.Direction;
import ptp.pacman.base.GameControllers;
import ptp.pacman.controller.KeyboardController;

/** Dialog used to set keys for keyboard controllers.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class ControllersConfigurationDialog extends JDialog
{
	private static final long serialVersionUID = 4816745409879979860L;
	
	private final JLabel waiting;
	private final JButton close;
	private final Game mGame;
	
	/** Constructor.
	 * @param Game the game for which controllers will be configured. Needed to automatically pause the game.
	 * */
	public ControllersConfigurationDialog(Game game)
	{
		mGame = game;
		
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        this.setTitle("Controller configuration");
        waiting = new JLabel("Press a key");
        waiting.setVisible(false);
        
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridwidth = 1; cs.gridx = 0; cs.gridy = 0;
        
        
        for(int player=0 ; player<4 ; player++) {
        	++cs.gridy; cs.gridx=0;
        	panel.add(new JLabel("Player "+String.valueOf(player)+" :   "), cs);
        	for(int direction=0 ; direction<4; direction++){
        		++cs.gridx;
        		switch(direction){
        		case 0: panel.add(new JLabel("  up : "), cs); ++cs.gridx; break;
        		case 1: panel.add(new JLabel("  down : "), cs); ++cs.gridx; break;
        		case 2: panel.add(new JLabel("  left : "), cs); ++cs.gridx; break;
        		case 3: panel.add(new JLabel("  right : "), cs); ++cs.gridx; break;
        		}
        		panel.add(getControllerText(player,direction),cs); ++cs.gridx;
        	}
        }
        ++cs.gridy; cs.gridx = 4;
        panel.add(waiting,cs);
        ++cs.gridy; cs.gridx = 0;
        
        close = new JButton("Close");
        close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				mGame.pause();
			}
        	
        });
        panel.add(close, cs);
        
        addWindowListener(new WindowListener()
        {
            @Override
            public void windowActivated(WindowEvent arg0) {}
            @Override
            public void windowClosed(WindowEvent arg0) {}
            @Override
            public void windowClosing(WindowEvent arg0){ mGame.pause(); }
            @Override
            public void windowDeactivated(WindowEvent arg0) {}
            @Override
            public void windowDeiconified(WindowEvent arg0) {}
            @Override
            public void windowIconified(WindowEvent arg0) {}
            @Override
            public void windowOpened(WindowEvent arg0) { mGame.pause(); }
        });
        
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setResizable(false);
	}

	private JTextField getControllerText(int player, final int direction) 
	{
		final KeyboardController keysController = GameControllers.KeyboardControllers().get(player);
		int oldKeyCode = keysController.getKeyCodeDirection(Direction.values()[direction]);
		final JTextField keyInput = new JTextField(KeyEvent.getKeyText(oldKeyCode),4);
		mGame.pause();
		
		keyInput.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { 
				int keyCode = e.getKeyCode();
				keyInput.setText(KeyEvent.getKeyText(keyCode));
				keysController.setKeyCodeDirection(Direction.values()[direction],keyCode);
			}

			@Override
			public void keyTyped(KeyEvent e) { }
		});
		
		return keyInput;
	}

}
