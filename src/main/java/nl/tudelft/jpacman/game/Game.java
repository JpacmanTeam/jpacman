package nl.tudelft.jpacman.game;

import java.util.List;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Level.LevelObserver;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.ui.Action;

/**
 * A basic implementation of a Pac-Man game.
 *
 * @author Jeroen Roosen
 */
public abstract class Game implements LevelObserver {

    /**
     * <code>true</code> if the game is in progress.
     */
    private boolean inProgress;

    /**
     * Object that locks the start and stop methods.
     */
    private final Object progressLock = new Object();

    /**
     * The algorithm used to calculate the points that
     * they player gets whenever some action happens.
     */
    private final PointCalculator pointCalculator;

    private Action winAction;
    private Action lostAction;
    /**
     * Creates a new game.
     *
     * @param pointCalculator
     *             The way to calculate points upon collisions.
     */
    protected Game(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
        inProgress = false;
    }

    /**
     * Starts or resumes the game.
     */
    public void start() {
        synchronized (progressLock) {
            if (isInProgress()) {
                return;
            }
            if (getLevel().isAnyPlayerAlive() && getLevel().remainingPellets() > 0) {
                inProgress = true;
                getLevel().addObserver(this);
                getLevel().start();
            }
        }
    }

    /**
     * Pauses the game.
     */
    public void stop() {
        synchronized (progressLock) {
            if (!isInProgress()) {
                return;
            }
            inProgress = false;
            getLevel().stop();
        }
    }

    /**
     * @return <code>true</code> iff the game is started and in progress.
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * @return An immutable list of the participants of this game.
     */
    public abstract List<Player> getPlayers();

    /**
     * @return The level currently being played.
     */
    public abstract Level getLevel();

    /**
     * Moves the specified player one square in the given direction.
     *
     * @param player
     *            The player to move.
     * @param direction
     *            The direction to move in.
     */
    public void move(Player player, Direction direction) {
        if (isInProgress()) {
            // execute player move.
            getLevel().move(player, direction);
            pointCalculator.pacmanMoved(player, direction);
        }
    }

    /**
     * Store player's status
     */
    public enum PLAYER_STATUS{
        WIN,
        LOST,
        PLAYING
    }

    /**
     * Default player's status to playing.
     */
    private PLAYER_STATUS playerStatus = PLAYER_STATUS.PLAYING;

    /**
     * @return The current player's status.
     * @return
     */
    public PLAYER_STATUS getPlayerStatus(){
        return playerStatus;
    }

    /**
     * Set player's status.
     * @param ps
     *          Status the player is currently in.
     */
    public void setPlayerStatus(PLAYER_STATUS ps){
        playerStatus = ps;
    }

    @Override
    public void levelWon() {
        setPlayerStatus(PLAYER_STATUS.WIN);
        stop();
        winAction.doAction();
    }

    @Override
    public void levelLost() {
        setPlayerStatus(PLAYER_STATUS.LOST);
        stop();
        lostAction.doAction();
    }

    public void setLostAction(Action lostAction) {
        this.lostAction = lostAction;
    }

    public void setWinAction(Action winAction) {
        this.winAction = winAction;
    }

    /**
     * Returns true if there is a next level available, false otherwise.
     */

    /**
     * Starts the next level.
     */
}
