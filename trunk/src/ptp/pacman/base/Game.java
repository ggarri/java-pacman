package ptp.pacman.base;

import java.util.ArrayList;
import java.util.List;

import ptp.pacman.base.Actor.Speed;
import ptp.pacman.controller.AbstractGhostController;
import ptp.pacman.controller.KeyboardController;

/** This is the Game main class, where action happens
 * Given a GameStatusListener and a GameRepresentationListener, we can have multiple
 * front-ends for our game.
 * */
public class Game
{
    public static int SLEEP_MILLIS = 33; // 30 fps, aprox
    private static final int GAME_TIME_SECONDS = 180; // 180
    
    private static PacmanMap mMap;
	private static PacmanMap mOrigMap;
	
    private final List<Pacman> mPacmans = new ArrayList<Pacman>();
    private final List<Ghost> mGhosts = new ArrayList<Ghost>();
    
    private GameStatusListener mStatusListener;
    private GameRepresentationListener mRepresentationListener;
    private boolean mPause, mRunning;
    private long mStartTimeMillis, mStartTimePauseMillis;
    
    /** Constructor. 
     * @param mapPath File path from where load a map.
     * @param pacmans Number of Pacmans that will try to eat all points on screen.
     * @param ghosts Number of ghosts that will appear on screen.
     * */
    public Game(String mapPath, int pacmans, int ghosts, int humanGhosts, int level)
    {
        mMap = new PacmanMap(mapPath);
        mOrigMap = new PacmanMap(mMap);
        mPause = false;

        GameControllers.InitGhostControllers(this);

        System.out.println("HumanGhost : " + humanGhosts);
    	System.out.println("Pacmans : " + pacmans);
        // Add some KeyWord Controllers
        for(int i=0; i<pacmans+humanGhosts && i<GameControllers.KeyboardControllers().size(); i++) {
        	// Add PacMan Human
        	if(i<pacmans)
        		addPacman( GameControllers.KeyboardControllers().get(i));
        	// Add Ghost Human
        	else{
        		addGhost( GameControllers.KeyboardControllers().get(i));
        	}
        }
        
        // Add some Ghost with Abstract Controllers
        for(int i=humanGhosts; i<ghosts; i++) {
        	addGhost( GameControllers.GhostControllers().get(i));
        }
        setLevel(level);
    }
    
    /** Sets the level of the game. 
     * @param level The higher the level, the more difficult it is to play. 
     * */
    private void setLevel(int level)
    {
    	Speed mSpeed= Speed.NORMAL;
    	switch(level){
    	case 1: mSpeed = Speed.SLOW; break;
    	case 2: mSpeed = Speed.NORMAL; break;
    	case 3: mSpeed = Speed.FAST; break;
    	case 4: mSpeed = Speed.VERY_FAST; break;
    	}
    	for(Ghost g: mGhosts)
    		g.setSpeed(mSpeed);
    }
    
    /** Returns the current map of the game.
     * @return The current map.
     * */
    public PacmanMap getMap()
    {
        return mMap;
    }
    
    /** Returns the list of Pacmans that play in the game.
     * @return The list of Pacmans.
     * */
    public List<Pacman> getPacmans()
    {
        return mPacmans;
    }
    
    /** Returns the current game status (pause/running).
     * @return True for pause, false for running.
     * */
    public boolean isPaused()
    {
    	return mPause;
    }
    
    /** Adds a Pacman to the game, given a keyboard controller.
     * @param kc The keyboard controller that will rule the Pacman's movements.
     * @return If this Pacman is the last one that can be added.
     * */
    public boolean addPacman(KeyboardController kc)
    {
        int index = mPacmans.size();
        int pacmanPositions = mMap.getNumberOfPacmanStartPositions();
        
        Pacman p = new Pacman(mMap.getPacmanStart(index), Speed.VERY_FAST, mMap, 1, kc);
        mPacmans.add(p);
        
        return index+1 < pacmanPositions; 
    }
    
    /** Adds a ghost to the game, given a controller (can be human or IA-ruled)
     *  Sets a random position in the list of ghost positions.
     * @param c The controller that will rule the ghost's movements.
     * */
    public void addGhost (ActorController c)
    {
        Ghost g = new Ghost(mMap.getRandomGhostStart(), Speed.NORMAL, mMap, c);
        if(AbstractGhostController.class.isAssignableFrom(c.getClass())) {
        	((AbstractGhostController)c).setGhost(g);
        }
        mGhosts.add(g);
    }
    
    /** Returns the ghost that are in this game.
     * @return A list with the ghosts that exist in the current game.
     * */
    public List<Ghost> getGhosts()
    {
        return mGhosts;
    }
    
    /** Sets the game status listener
     * @param l The new status listener.
     * */
    public void setStatusListener(GameStatusListener l) 
    {
        mStatusListener = l;
        l.setMapName(mMap.getName());
    }
    
    /** Sets the game representation listener
     * @param l The new representation listener.
     * */
    public void setRepresentationListener(GameRepresentationListener l) 
    {
        mRepresentationListener = l;
    }
    
    /** Removes the given status listener.
     * @param l The status listener, if it's previously assigned.
     * */
    public void removeStatusListener(GameStatusListener l) 
    {
        if(mStatusListener == l)
            mStatusListener = null;
    }
    
    /** Removes the given representation listener.
     * @param l The representation listener, if it's previously assigned.
     * */
    public void removeRepresentationListener(GameRepresentationListener l) 
    {
        if (mRepresentationListener == l)
            mRepresentationListener = null;
    }
    
    /** Restarts the game, returning everything to its initial state.
     * */
    public void restart()
    {
    	restartTime();
    	mapRestart();
    	restartGhosts();
    	restartPacmans();
    	mainLoop();
    }
    
    /** Pauses the current game
     * */
    public synchronized void pause()
    {
    	if(!mPause) {
    		mStartTimePauseMillis = System.currentTimeMillis(); 
    		mPause = true;
    	} else {
    		long currentTimeMillis = System.currentTimeMillis();
    		mStartTimeMillis -= mStartTimePauseMillis - currentTimeMillis;
    		mPause = false;
    	}
    }
    
    /** Destroys the current game and stops the main loop.
     * */
    public synchronized void destroy()
    {
    	mRunning = false;
    }
    
    /** Revives all the Pacmans in the game.
     * @see ptp.pacman.base.Pacman#revive()
     * */
    public void revivePacmans()
    {
    	for(Pacman p: mPacmans)
    		p.revive();
    }
    
    private void restartPacmans()
    {
    	for(Pacman p: mPacmans)
    		p.restart();
    }
    
    private void restartGhosts()
    {
    	for(Ghost g: mGhosts)
    		g.restart();
    }
    
    private void restartTime()
    {
    	mStartTimeMillis = System.currentTimeMillis();
    }
    
    private void mapRestart()
    {
    	mMap = mOrigMap;
    	mOrigMap = new PacmanMap(mMap);
    }
    
    /** Game loop. Call this method to start the game in a separate thread.
     * @see ptp.pacman.base.Game#destroy()
     * */
    public void mainLoop()
    {
    	mRunning = true;
    	
        Thread t = new Thread() {
            public void run()
            {
            	boolean hasMathFinished, hasGameFinished; 
                mStartTimeMillis = System.currentTimeMillis();
                while(mRunning) {
                    try {
                        Thread.sleep(SLEEP_MILLIS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    long timeMillis = System.currentTimeMillis();
                    // Move stuff
                    
                    
                    if (!mPause) {
	                    for(Pacman p : mPacmans) {
	                    	if (p.isAlive() && !p.isDead())
	                    		p.move(timeMillis);
	                    }
	                    
	                    for(Ghost g : mGhosts) {
	                        g.move();
	                    }
                    }
                    
                    // Check interactions between actors
                    for(Pacman p: mPacmans) {
                        for(Ghost g: mGhosts) {
                            if(p.collides(g) && !p.isDead()) {
                            	if (p.getState() == Pacman.State.NORMAL) {
                            		g.capture();
                            		p.die();
                            	}
                            	else {
                            		p.eat();
                            		g.die();
                            	}
                            }
                        }
                    }
                    
                    long thinkTime = System.currentTimeMillis() - timeMillis;

                    
                    // Update game listeners
                    if(mRepresentationListener != null)
                        mRepresentationListener.refresh(Game.this);
                    
                    if(mStatusListener != null) {
                        int seconds = (int)(timeMillis - mStartTimeMillis)/1000;
                        int secondsRemaining = GAME_TIME_SECONDS - seconds;
                        if (!mPause)
                        	mStatusListener.setTime(seconds, secondsRemaining);
                        // IF TIME OVER THEN ALL PACMANs DIE
                        if (secondsRemaining<0){
                        	for(Pacman p: mPacmans)
                        		p.die();
                        }
                    }
                    
                    hasMathFinished = true;
                    hasGameFinished = true;
                    for(Pacman p: mPacmans) {
                    	if (!p.isDead())  hasMathFinished = false;
                    	if (p.isAlive()) hasGameFinished = false;
                    }
                    
                    long fullTime = System.currentTimeMillis() - timeMillis;

                    // The PacMan WIN
                    if(mMap.areAllPointsEaten()){
                    	mStatusListener.pacmansWin();
                    	mRunning = false;
                    }
                    // The Game Finishes
                    else if(hasGameFinished){
                    	mStatusListener.gameOver();
                    	mRunning = false;
                    }
                    // The Next Math                    
                    else if(hasMathFinished){
                    	revivePacmans();
                    	restartGhosts();
                    	restartTime();
                    }
                    
                  //System.out.println("Think: "+thinkTime + " - Everything: "+fullTime);
                }
            }
        };
        
        t.start();
    }
}
