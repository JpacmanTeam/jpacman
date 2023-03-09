package nl.tudelft.jpacman;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

import javax.swing.Timer;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Launcher {

    private static final PacManSprites SPRITE_STORE = new PacManSprites();

    public static final String DEFAULT_MAP = "/board.txt";
    private String levelMap = DEFAULT_MAP;

    private PacManUI pacManUI;
    private Game game;

    private final int DELAY_MS = 100; // milliseconds between each movement
    private final Timer timer = new Timer(DELAY_MS, null); // create a timer with an empty ActionListener
    private final ActionListener[] currentAction = {null}; // to keep track of the current ActionListener

    /**
     * @return The game object this launcher will start when {@link #launch()}
     *         is called.
     */
    public Game getGame() {
        return game;
    }

    /**
     * The map file used to populate the level.
     *
     * @return The name of the map file.
     */
    protected String getLevelMap() {
        return levelMap;
    }

    /**
     * Set the name of the file containing this level's map.
     *
     * @param fileName
     *            Map to be used.
     * @return Level corresponding to the given map.
     */
    public Launcher withMapFile(String fileName) {
        levelMap = fileName;
        return this;
    }

    /**
     * Creates a new game using the level from {@link #makeLevel()}.
     *
     * @return a new Game.
     */
    public Game makeGame() {
        GameFactory gf = getGameFactory();
        Level level = makeLevel();
        game = gf.createSinglePlayerGame(level, loadPointCalculator());
        return game;
    }

    private PointCalculator loadPointCalculator() {
        return new PointCalculatorLoader().load();
    }

    /**
     * Creates a new level. By default this method will use the map parser to
     * parse the default board stored in the <code>board.txt</code> resource.
     *
     * @return A new level.
     */
    public Level makeLevel() {
        try {
            return getMapParser().parseMap(getLevelMap());
        } catch (IOException e) {
            throw new PacmanConfigurationException(
                    "Unable to create level, name = " + getLevelMap(), e);
        }
    }

    /**
     * @return A new map parser object using the factories from
     *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
     */
    protected MapParser getMapParser() {
        return new MapParser(getLevelFactory(), getBoardFactory());
    }

    /**
     * @return A new board factory using the sprite store from
     *         {@link #getSpriteStore()}.
     */
    protected BoardFactory getBoardFactory() {
        return new BoardFactory(getSpriteStore());
    }

    /**
     * @return The default {@link PacManSprites}.
     */
    protected PacManSprites getSpriteStore() {
        return SPRITE_STORE;
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}
     *         and the ghosts from {@link #getGhostFactory()}.
     */
    protected LevelFactory getLevelFactory() {
        return new LevelFactory(getSpriteStore(), getGhostFactory(), loadPointCalculator());
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}.
     */
    protected GhostFactory getGhostFactory() {
        return new GhostFactory(getSpriteStore());
    }

    /**
     * @return A new factory using the players from {@link #getPlayerFactory()}.
     */
    protected GameFactory getGameFactory() {
        return new GameFactory(getPlayerFactory());
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}.
     */
    protected PlayerFactory getPlayerFactory() {
        return new PlayerFactory(getSpriteStore());
    }

    /**
     * Adds key events UP, DOWN, LEFT, RIGHT, A, S, D and W to a game.
     *
     * @param builder
     *            The {@link PacManUiBuilder} that will provide the UI.
     */
    protected void addSinglePlayerKeys(final PacManUiBuilder builder) {
        final Action stopMoving = () -> { timer.stop(); };
        builder .addKey(KeyEvent.VK_UP, continueMoveTowardsDirection(Direction.NORTH), stopMoving)
                .addKey(KeyEvent.VK_DOWN, continueMoveTowardsDirection(Direction.SOUTH), stopMoving)
                .addKey(KeyEvent.VK_LEFT, continueMoveTowardsDirection(Direction.WEST), stopMoving)
                .addKey(KeyEvent.VK_RIGHT, continueMoveTowardsDirection(Direction.EAST), stopMoving)
                .addKey(KeyEvent.VK_W, continueMoveTowardsDirection(Direction.NORTH), stopMoving)
                .addKey(KeyEvent.VK_S, continueMoveTowardsDirection(Direction.SOUTH), stopMoving)
                .addKey(KeyEvent.VK_A, continueMoveTowardsDirection(Direction.WEST), stopMoving)
                .addKey(KeyEvent.VK_D, continueMoveTowardsDirection(Direction.EAST), stopMoving);
    }


    /**
     * Return moving action when the key is pressed
     *
     * @param direction direction for moving
     * @return {@link Action} to perform
     */
    private Action continueMoveTowardsDirection(Direction direction) {
        return () -> {
            // remove the previous ActionListener before adding a new one
            timer.removeActionListener(currentAction[0]);
            final ActionListener newAction = event -> {
                assert game != null;
                getGame().move(getGame().getPlayers().get(0), direction);
            };
            currentAction[0] = newAction;
            timer.addActionListener(currentAction[0]);
            timer.start();
        };
    }


    private Player getSinglePlayer(final Game game) {
        List<Player> players = game.getPlayers();
        if (players.isEmpty()) {
            throw new IllegalArgumentException("Game has 0 players.");
        }
        return players.get(0);
    }

    /**
     * Creates and starts a JPac-Man game.
     */
    public void launch() {
        makeGame();
        //add Restart button on button panel, additionally from default
        PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons().addButton("Restart",restart());
        addSinglePlayerKeys(builder);
        pacManUI = builder.build(getGame());
        pacManUI.start();
    }

    /**
     * Disposes of the UI. For more information see
     * {@link javax.swing.JFrame#dispose()}.
     *
     * Precondition: The game was launched first.
     */
    public void dispose() {
        assert pacManUI != null;
        pacManUI.dispose();
    }

    /**
     * Restart the game by dispose old game frame and launch new game frame instead
     * @return Action, pack of function that dispose and launch
     */
    public Action restart(){
        return ()->{
            dispose();
            launch();
        };
    }

    /**
     * Main execution method for the Launcher.
     *
     * @param args
     *            The command line arguments - which are ignored.
     * @throws IOException
     *             When a resource could not be read.
     */
    public static void main(String[] args) throws IOException {
        new Launcher().launch();
    }
}
