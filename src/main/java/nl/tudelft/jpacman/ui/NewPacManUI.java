package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.SinglePlayerPacmanFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The default JPacMan UI frame. The PacManUI consists of the following
 * elements:
 *
 * <ul>
 * <li>A score panel at the top, displaying the score of the player(s).
 * <li>A board panel, displaying the current level, i.e. the board and all units
 * on it.
 * <li>A button panel, containing all buttons provided upon creation.
 * </ul>
 *
 * @author Saksit and Anirut
 *
 */
public class NewPacManUI extends JFrame {



    /**
     * Default serialisation UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The desired frame rate interval for the graphics in milliseconds, 40
     * being 25 fps.
     */
    private static final int FRAME_INTERVAL = 40;

    /**
     * The panel displaying the player scores.
     */
    private ScorePanel scorePanel;

    /**
     * The panel displaying the game.
     */
    private BoardPanel boardPanel;

    /**
     * The layout for set the main panel's layout.
     * */
    private final CardLayout cardLayout = new CardLayout();

    private Game currentGame;
    private SinglePlayerPacmanFactory pacmanGameFactory = new SinglePlayerPacmanFactory();
    private JPanel gameContainer;
    private JPanel buttonPanel;
    private StartPanel startPanel;
    private LosePanel losePanel;
    private WinPanel winPanel;
    private Container contentPanel;

    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public JPanel getGameContainer() {
        return gameContainer;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public StartPanel getStartPanel() {
        return startPanel;
    }

    public LosePanel getLosePanel() {
        return losePanel;
    }

    public WinPanel getWinPanel() {
        return winPanel;
    }

    public Container getContentPanel() {
        return contentPanel;
    }

    /**
     * Creates a new UI for a JPacman game.
     */
    public NewPacManUI() {
        super("JPacman");

        currentGame = pacmanGameFactory.createPacmanLevel1();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        bindKeyControllerToCurrentGame();

        gameContainer = new JPanel();
        startPanel = new StartPanel();
        losePanel = new LosePanel();
        winPanel = new WinPanel();

        contentPanel = getContentPane();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(startPanel,"home");
        contentPanel.add(gameContainer,"game");
        contentPanel.add(losePanel,"lose");
        contentPanel.add(winPanel,"win");

        addGameToGameContainer();

        startPanel.getStartButton().addActionListener(e->{
            cardLayout.show(contentPanel,"game");
        });
        losePanel.getHomeButton().addActionListener(e->{
            cardLayout.show(contentPanel,"home");
            removeGameContainer();
            currentGame = pacmanGameFactory.createPacmanLevel1();
            addGameToGameContainer();
        });
        losePanel.getRetryButton().addActionListener(e->{
            cardLayout.show(contentPanel,"game");
            removeGameContainer();
            currentGame = pacmanGameFactory.createPacmanLevel1();
            addGameToGameContainer();
        });
        pack();
    }

    private void addGameToGameContainer(){
        setWinEvent();
        setLoseEvent();
        buttonPanel = getButtonPanelOfGame();
        scorePanel = new ScorePanel(currentGame.getPlayers());
        boardPanel = new BoardPanel(currentGame);
        gameContainer.setLayout(new BorderLayout());
        gameContainer.add(buttonPanel, BorderLayout.SOUTH);
        gameContainer.add(scorePanel, BorderLayout.NORTH);
        gameContainer.add(boardPanel, BorderLayout.CENTER);
    }
    private void removeGameContainer(){gameContainer.removeAll();}

    /**
     * Starts the "engine", the thread that redraws the interface at set
     * intervals.
     */
    public void start() {
        setVisible(true);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::nextFrame, 0, FRAME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     * Draws the next frame, i.e. refreshes the scores and game.
     */
    private void nextFrame() {
        boardPanel.repaint();
        scorePanel.refresh();
    }

    /**
     * Prepare key controller for each level game
     * */
    private void bindKeyControllerToCurrentGame(){
        PacKeyListener keys = getKeysControlOfGame();
        this.setFocusable(true);
        this.requestFocus();
        addKeyListener(keys);
    }

    /**
     * move player's pacman to given direction
     * */
    private Action moveTowardsDirection( Direction direction) {
        return () -> {
            assert currentGame != null;
            currentGame.move(currentGame.getPlayers().get(0), direction);
        };
    }

    /**
     * Map the control key that using in the game
     * */
    private PacKeyListener getKeysControlOfGame(){
        assert currentGame != null;
        Map<Integer,Action> keys = new HashMap<>();
        keys.put(KeyEvent.VK_UP, moveTowardsDirection(Direction.NORTH));
        keys.put(KeyEvent.VK_DOWN, moveTowardsDirection(Direction.SOUTH));
        keys.put(KeyEvent.VK_LEFT, moveTowardsDirection(Direction.WEST));
        keys.put(KeyEvent.VK_RIGHT, moveTowardsDirection(Direction.EAST));
        keys.put(KeyEvent.VK_SPACE, ()->{
            if(gameContainer.isShowing()) {
                if (currentGame.isInProgress()) currentGame.stop();
                else currentGame.start();
            } else if(startPanel.isShowing()){
                startPanel.getStartButton().doClick();
            } else if(losePanel.isShowing()){
                losePanel.getRetryButton().doClick();
            }

        });
        keys.put(KeyEvent.VK_ESCAPE,()->{
           if(losePanel.isShowing()){
               losePanel.getHomeButton().doClick();
           }
        });
        PacKeyListener pacKeyListener = new PacKeyListener(keys);
        return pacKeyListener;
    }

    /**
     * prepare button in button panel for each level game
     * */
    private JPanel getButtonPanelOfGame(){
        assert currentGame != null;
        var button = new HashMap<String,Action>();
        button.put("Start",currentGame::start);
        button.put("Stop",currentGame::stop);
        JPanel buttonPanel = new ButtonPanel(button, this);
        return buttonPanel;
    }

    /**
     * create event when player lose the game
     * */
    private void setLoseEvent(){
        currentGame.setLostAction(()->{
//            System.out.println("lost");
            cardLayout.show(contentPanel,"lose");
//            removeGameContainer();
//            currentGame = pacmanGameFactory.createPacmanLevel2();
//            addGameToGameContainer();
//            currentGame.start();
        });
    }

    /**
     * create event when player win the game
     * */
    private void setWinEvent(){
        currentGame.setWinAction(()->{
            System.out.println("win");
            removeGameContainer();
            currentGame = pacmanGameFactory.createPacmanLevel1();
            addGameToGameContainer();
            currentGame.start();
        });
    }
}
