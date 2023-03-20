package nl.tudelft.jpacman.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter;

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
 * @author Jeroen Roosen 
 *
 */
public class PacManUI extends JFrame {

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
    private final ScorePanel scorePanel;
    private final ScorePanel scorePanel2;

    /**
     * The panel displaying the game.
     */
    private final BoardPanel boardPanel;
    private final BoardPanel boardPanel2;

    private CardLayout cardLayout;

    /**
     * Creates a new UI for a JPacman game.
     *
     * @param game1
     *            The game to play.
     * @param buttons
     *            The map of caption-to-action entries that will appear as
     *            buttons on the interface.
     * @param keyMappings
     *            The map of keyCode-to-action entries that will be added as key
     *            listeners to the interface.
     * @param scoreFormatter
     *            The formatter used to display the current score.
     */
    public PacManUI(final Game game1, final Game game2,final Map<String, Action> buttons,
                    final Map<Integer, Action> keyMappings,
                    ScoreFormatter scoreFormatter) {
        super("JPacman");
        assert game1 != null;
        assert game2 != null;
        assert buttons != null;
        assert keyMappings != null;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        PacKeyListener keys = new PacKeyListener(keyMappings);
        addKeyListener(keys);

        JPanel buttonPanel = new ButtonPanel(buttons, this);

        scorePanel = new ScorePanel(game1.getPlayers());
        if (scoreFormatter != null) {
            scorePanel.setScoreFormatter(scoreFormatter);
        }
        scorePanel2 = new ScorePanel(game2.getPlayers());
        if (scoreFormatter != null) {
            scorePanel2.setScoreFormatter(scoreFormatter);
        }

        boardPanel = new BoardPanel(game1);
        boardPanel2 = new BoardPanel(game2);

        cardLayout = new CardLayout();

        Container contentPanel = getContentPane();
        contentPanel.setLayout(cardLayout);

        //Home Page
        JPanel a = new JPanel();
        JLabel label = new JLabel("Home");
        a.add(label);

        //Container for Board
        JPanel ctn = new JPanel();
        JPanel ctn2 = new JPanel();

        //Add button for accessing board.txt
        JButton testBtn = new JButton("to game");
        //Add button for accessing board2.txt
        JButton testBtn2 = new JButton("Board2");

        //Add button to Home Page
        a.add(testBtn);
        a.add(testBtn2);
        contentPanel.add(a,"test");

        ctn.setLayout(new BorderLayout());
        ctn.add(scorePanel,BorderLayout.NORTH);
        ctn.add(boardPanel,BorderLayout.CENTER);
        ctn.add(buttonPanel,BorderLayout.SOUTH);
        contentPanel.add(ctn,"test2");

        ctn2.setLayout(new BorderLayout());
        ctn2.add(scorePanel2,BorderLayout.NORTH);
        ctn2.add(boardPanel2,BorderLayout.CENTER);
        ctn2.add(buttonPanel,BorderLayout.SOUTH);
        contentPanel.add(ctn2,"test3");

        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(ctn.getParent(),"test2");
            }
        });
        testBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cardLayout.show(ctn2.getParent(),"test3");}
        });


//        contentPanel.setLayout(new BorderLayout());
//        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
//        contentPanel.add(scorePanel, BorderLayout.NORTH);
//        contentPanel.add(boardPanel, BorderLayout.CENTER);

        pack();
    }

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
}
