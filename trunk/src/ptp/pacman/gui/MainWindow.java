package ptp.pacman.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import ptp.pacman.base.Game;
import ptp.pacman.base.GameControllers;
import ptp.pacman.base.Ghost;
import ptp.pacman.base.Pacman;
import ptp.pacman.controller.KeyboardController;

/**
 * Class that represents the main window of the game. The only public interesting method is the constructor.
 *  @author Jose Alcalá Correa, Gabriel Garrido Calvo
 *  @version 1.0
 * */
public class MainWindow extends JFrame
{
    private static final long serialVersionUID = -3756702985198736401L;
    
    private Game mGame;
    private GamePanel mGamePanel;
    private GameStatusPanel mGameStatusPanel;
    private int n_pacmans = 1,n_ghosts = 4,n_ghost_human=0,n_players=1;
    private int mLevel = 2;
    private String mapName = "map1.map";
    private JPanel mActorsPanel;
	JMenuBar menuBar;
	JMenu menuGame,menuLevel,menuPlayer,menuGhostPlayer,menuAbout;
	JMenuItem menuItemMap, menuItemExit;
	JMenuItem menuItemPlayers1,menuItemPlayers2,menuItemPlayers3,menuItemPlayers4,menuItemControllers;
	JMenuItem menuItemGhostPlayers0,menuItemGhostPlayers1,menuItemGhostPlayers2,menuItemGhostPlayers3;
	ButtonGroup groupLevel = new ButtonGroup();
	JRadioButtonMenuItem rbMenuItemLevel1,rbMenuItemLevel2,rbMenuItemLevel3,rbMenuItemLevel4;
	
    /** Constructors. Inits the game and the keyboard controllers.
     * By default, the map is the default one, there is a Pacman controlled by the user and four ghosts controlled by the AI.
     * */
    public MainWindow()
    {
        // Instance variables initialization
        mGame = new Game(mapName, n_pacmans, n_ghosts, n_ghost_human, mLevel);
        initInterface();
        mGame.mainLoop();
    }
    
    /** Inits the user interface, adding menus and so.
     * */
    private void initInterface()
    {
        this.setTitle("Pacman devora fantasmicas");
        
        addWindowListener(new WindowListener()
        {
            @Override
            public void windowActivated(WindowEvent arg0) {}
            @Override
            public void windowClosed(WindowEvent arg0) {}

            @Override
            public void windowClosing(WindowEvent arg0)
            {
                dispose();
                System.exit(0); // and exit.
            }

            @Override
            public void windowDeactivated(WindowEvent arg0) {}
            @Override
            public void windowDeiconified(WindowEvent arg0) {}
            @Override
            public void windowIconified(WindowEvent arg0) {}
            @Override
            public void windowOpened(WindowEvent arg0) {}
        });
        
        // Bar Options Menu 
        menuBar = new JMenuBar();
        addMenuGame();
        addMenuPlayers();
        addGhostPlayers();

        // Add all the Panels   
        addPanels();
        
        // Add information Dialog
        addAbout();
        
        // Configure window
        pack();
        setMinimumSize(new Dimension(700, 700));
    }
    
    /** Restarts the components in the main window, reflecting changes in the game.
     * */
    public void restart()
    {
    	this.remove(mGameStatusPanel);
    	this.remove(mGamePanel);
    	this.remove(mActorsPanel);
    	
    	mGame.destroy();
    	mGame = new Game(mapName, n_pacmans, n_ghosts,n_ghost_human,mLevel);
    	
    	
    	this.validate();
    	this.repaint();
    	
    	this.addPanels();
    	
    	mGame.mainLoop();
    }
    
    private void addAbout()
    {
    	menuAbout = new JMenu("About");
    	menuAbout.setMnemonic(KeyEvent.VK_A);
    	menuAbout.getAccessibleContext().setAccessibleDescription(
    	        "Project Information");
    	
    	menuAbout.addMenuListener(new MenuListener(){
			@Override
			public void menuCanceled(MenuEvent arg0) {	}
			@Override
			public void menuDeselected(MenuEvent arg0) { }
			@Override
			public void menuSelected(MenuEvent arg0) { 	
					JDialog dialog = new JDialog();
			        dialog.setTitle("About");
			        
			        JLabel authors = new JLabel("Gabriel Garrido Calvo & Jose Alcala Correa");
			        JLabel module  = new JLabel("Programmiertechnisches Praktikum (SoSe 2011)");
			        JLabel version = new JLabel("PacMan Version 0.9");
			        
			        dialog.setLayout(new BorderLayout());
			        dialog.add(authors,BorderLayout.NORTH);
			        dialog.add(module,BorderLayout.CENTER);
			        dialog.add(version,BorderLayout.SOUTH);
			        
			        dialog.pack();
			        dialog.setVisible(true);
			}
    	});
    	
    	menuBar.add(menuAbout);
    }
    
    private void addPanels()
    {
    	// Main window layout
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        
    	// Information panel
        mGameStatusPanel = new GameStatusPanel(mGame,this);
        add(mGameStatusPanel, BorderLayout.NORTH);
        mGame.setStatusListener(mGameStatusPanel);
        
        
        // Game panel
        mGamePanel = new GamePanel();
        mGamePanel.setGame(mGame);
        add(mGamePanel, BorderLayout.CENTER);
        
        for(KeyboardController kc : GameControllers.KeyboardControllers()) {
            addKeyListener(kc);
        }
        
        // Player panels
        mActorsPanel = new JPanel();
        mActorsPanel.setLayout(new BoxLayout(mActorsPanel, BoxLayout.Y_AXIS));
        for(Pacman p : mGame.getPacmans())
            mActorsPanel.add(new PacmanStatusPanel(p));
        for(Ghost g : mGame.getGhosts())
            mActorsPanel.add(new GhostStatusPanel(g));
        
        add(mActorsPanel, BorderLayout.EAST);
    }
    
    
    
    private void addGhostPlayers()
    {
    	menuGhostPlayer = new JMenu("Ghost");
    	menuGhostPlayer.setMnemonic(KeyEvent.VK_G);
    	menuGhostPlayer.getAccessibleContext().setAccessibleDescription(
    	        "Ghost Players Settings");
    	
    	menuItemGhostPlayers0 = new JMenuItem("0 humans");
    	menuItemGhostPlayers0.setEnabled(false);
    	menuItemGhostPlayers1 = new JMenuItem("1 human");
    	menuItemGhostPlayers1.setEnabled(false);
    	menuItemGhostPlayers2 = new JMenuItem("2 humans");
    	menuItemGhostPlayers2.setEnabled(false);
    	menuItemGhostPlayers3 = new JMenuItem("3 humans");
    	menuItemGhostPlayers3.setEnabled(false);
    	
    	menuItemGhostPlayers0.addActionListener(new GhostPlayersListener(0));
    	menuItemGhostPlayers1.addActionListener(new GhostPlayersListener(1));
    	menuItemGhostPlayers2.addActionListener(new GhostPlayersListener(2));
    	menuItemGhostPlayers3.addActionListener(new GhostPlayersListener(3));
    	
    	
    	menuGhostPlayer.add(menuItemGhostPlayers0);
    	menuGhostPlayer.add(menuItemGhostPlayers1);
    	menuGhostPlayer.add(menuItemGhostPlayers2);
    	menuGhostPlayer.add(menuItemGhostPlayers3);
    	menuBar.add(menuGhostPlayer);
	}
    
    private void addMenuPlayers() {
    	menuPlayer = new JMenu("Player");
    	menuPlayer.setMnemonic(KeyEvent.VK_P);
    	menuPlayer.getAccessibleContext().setAccessibleDescription(
    	        "Players Settings");
    	
    	
    	menuItemPlayers1 = new JMenuItem("1 player");
    	menuItemPlayers1.setEnabled(false);
    	menuItemPlayers2 = new JMenuItem("2 player");
    	menuItemPlayers3 = new JMenuItem("3 player");
    	menuItemPlayers4 = new JMenuItem("4 player");
    	
    	menuItemPlayers1.addActionListener(new PlayersListener(1));
    	menuItemPlayers2.addActionListener(new PlayersListener(2));
    	menuItemPlayers3.addActionListener(new PlayersListener(3));
    	menuItemPlayers4.addActionListener(new PlayersListener(4));
    	
    	
    	menuItemControllers = new JMenuItem("SETTINGS");
    	
    	menuItemControllers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new ControllersConfigurationDialog(mGame);
				dialog.setVisible(true);
			}});
    	
    	menuPlayer.add(menuItemPlayers1);
    	menuPlayer.add(menuItemPlayers2);
    	menuPlayer.add(menuItemPlayers3);
    	menuPlayer.add(menuItemPlayers4);
    	menuPlayer.addSeparator();
    	menuPlayer.add(menuItemControllers);
    	menuBar.add(menuPlayer);
	}
    

	private void addMenuGame()
    {	
    	//Build the Main Menu.
    	menuGame = new JMenu("Game");
    	menuGame.setMnemonic(KeyEvent.VK_G);
    	menuGame.getAccessibleContext().setAccessibleDescription(
    	        "Option of the Game");
    	menuBar.add(menuGame);
    	
    	menuItemMap = new JMenuItem("Open map");
    	menuItemMap.setMnemonic(KeyEvent.VK_M);
    	menuItemMap.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openMapFile();
			}});
    	menuGame.add(menuItemMap);
    	menuGame.addSeparator();
    	
    	// Build the menu level
    	addMenuLevel();
    	menuGame.add(menuLevel);
    	menuGame.addSeparator();
    	
    	// Build the Exit
    	menuItemExit = new JMenuItem("Exit");
    	menuItemExit.setMnemonic(KeyEvent.VK_X);
    	menuItemExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
                System.exit(0); // and exit.
			}});
    	menuGame.add(menuItemExit);
    	
    	
    	// Add the Main Bar within MainWindow
    	setJMenuBar(menuBar);
    }
    
    private void addMenuLevel() {
    	//Build the Level SubMenu.
    	menuLevel = new JMenu("Ghost Level");
    	
    	rbMenuItemLevel1 = new JRadioButtonMenuItem("Level 1");
    	rbMenuItemLevel1.addItemListener(new LevelListener(1));
    	groupLevel.add(rbMenuItemLevel1);
    	menuLevel.add(rbMenuItemLevel1);
    	
    	rbMenuItemLevel2 = new JRadioButtonMenuItem("Level 2");
    	rbMenuItemLevel2.setSelected(true);
    	rbMenuItemLevel2.addItemListener(new LevelListener(2));
    	groupLevel.add(rbMenuItemLevel2);
    	menuLevel.add(rbMenuItemLevel2);
    	
    	rbMenuItemLevel3 = new JRadioButtonMenuItem("Level 3");
    	rbMenuItemLevel3.addItemListener(new LevelListener(3));
    	groupLevel.add(rbMenuItemLevel3);
    	menuLevel.add(rbMenuItemLevel3);
    	
    	rbMenuItemLevel4 = new JRadioButtonMenuItem("Level 4");
    	rbMenuItemLevel4.addItemListener(new LevelListener(4));
    	groupLevel.add(rbMenuItemLevel4);
    	menuLevel.add(rbMenuItemLevel4);
    	
	}

    
    
 ////////////////////////////////////////////////////////////////////////////
 //			CHANGE TO OTHER CLASS
 //////////////////////////////////////////////////////////////////////////
    
    
	private void openMapFile()
    {
    	JFileChooser dlg = new JFileChooser("./res/maps");
        dlg.setFileFilter(new ExtensionFileMap());
        int resp = dlg.showOpenDialog(dlg);
        if(resp == JFileChooser.APPROVE_OPTION){
            try{
            	File f = dlg.getSelectedFile();
            	mapName = f.getName();            	
            } catch(Exception e) {
                JOptionPane.showMessageDialog(this, "Wrong file.");
            }
            
            restart();
        }
    }
    
    
    
    public class LevelListener implements ItemListener {

    	private int level;
    	public LevelListener(int n) { this.level = n; } 
		@Override
		public void itemStateChanged(ItemEvent item) 
		{
			if(item.getStateChange()==1){
				mLevel = this.level;
				restart();
			}
		}
    }

    public class PlayersListener implements ActionListener {

    	private int players;
    	
    	public PlayersListener(int n) { this.players = n; } 
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			switch(n_pacmans){
			case(1):menuItemPlayers1.setEnabled(true); break;
			case(2):menuItemPlayers2.setEnabled(true); break;
			case(3):menuItemPlayers3.setEnabled(true); break;
			case(4):menuItemPlayers4.setEnabled(true); break;
			}
			
			n_players = players;
			n_pacmans = players;
			
			switch(n_players){
			case(1):menuItemPlayers1.setEnabled(false); break;
			case(2):menuItemPlayers2.setEnabled(false); break;
			case(3):menuItemPlayers3.setEnabled(false); break;
			case(4):menuItemPlayers4.setEnabled(false); break;
			}
			
			activeGhostHumanPlayers();
            restart();
		}
		
		private void activeGhostHumanPlayers(){
			
			menuItemGhostPlayers0.setEnabled(true);
			
			if(n_players>1) menuItemGhostPlayers1.setEnabled(true);
			else menuItemGhostPlayers1.setEnabled(false);
			
			if(n_players>2) menuItemGhostPlayers2.setEnabled(true);
			else menuItemGhostPlayers2.setEnabled(false);
			
			if(n_players>3) menuItemGhostPlayers3.setEnabled(true);
			else menuItemGhostPlayers3.setEnabled(false);
		}
    }
    
    
    
    public class GhostPlayersListener implements ActionListener {

    	private int ghostPlayers;
    	
    	public GhostPlayersListener(int n) { this.ghostPlayers = n; } 
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			n_ghost_human = ghostPlayers;
			n_pacmans = n_players - n_ghost_human;
			
			switch(n_ghost_human){
			case(0):menuItemGhostPlayers0.setEnabled(false); break;
			case(1):menuItemGhostPlayers1.setEnabled(false); break;
			case(2):menuItemGhostPlayers2.setEnabled(false); break;
			case(3):menuItemGhostPlayers3.setEnabled(false); break;
			}
            restart();
		}
    }
    
    
    public static void main(String [] args)
    {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
