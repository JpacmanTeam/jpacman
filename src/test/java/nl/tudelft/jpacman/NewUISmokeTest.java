package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.ui.NewPacManUI;
import nl.tudelft.jpacman.ui.StartPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoke test launching the full game,
 * and attempting to make a number of typical moves.
 *
 * This is <strong>not</strong> a <em>unit</em> test -- it is an end-to-end test
 * trying to execute a large portion of the system's behavior directly from the
 * user interface. It uses the actual sprites and monster AI, and hence
 * has little control over what is happening in the game.
 *
 * Because it is an end-to-end test, it is somewhat longer
 * and has more assert statements than what would be good
 * for a small and focused <em>unit</em> test.
 *
 * @author Anirut Chaogla
 */

public class NewUISmokeTest {
    private NewPacManUI pacManUI;
    private static Robot bot;

    /**
     * create {@link #pacManUI} instant then start it
     * and create new Robot
     */
    @BeforeEach
    void setup(){
        pacManUI = new NewPacManUI();
        pacManUI.start();

        try { bot = new Robot();
        }catch (Exception e){}
    }

    /**
     * dispose {@link #pacManUI}
     */
    @AfterEach
    void tearDown(){
        pacManUI.dispose();
    }

    /**
     * << Smoke test | UAT >>
     *
     * @throws InterruptedException
     */
    @Test
    void smokeTest() throws InterruptedException {

        // Showing Home
        assertTrue(pacManUI.getStartPanel().isShowing());
        Thread.sleep(500L);

        // Showing game after click start btn
        pacManUI.getStartPanel().getStartButton().doClick();
        assertTrue(pacManUI.getGameContainer().isShowing());
        Thread.sleep(500L);

        // start game
        pressKey(KeyEvent.VK_SPACE);
        Thread.sleep(500L);
        assertTrue(pacManUI.getCurrentGame().isInProgress());

        // stop game
        pressKey(KeyEvent.VK_SPACE);
        Thread.sleep(500L);
        assertFalse(pacManUI.getCurrentGame().isInProgress());

        // Showing Lose when lose
        pressKey(KeyEvent.VK_SPACE);
        pressKeyN(10,KeyEvent.VK_DOWN);
        Thread.sleep(500L);
        assertTrue(pacManUI.getLosePanel().isShowing());
        Thread.sleep(500L);

        // Showing game when press retry
        pacManUI.getLosePanel().getRetryButton().doClick();
        pressKey(KeyEvent.VK_SPACE);
        pressKeyN(10,KeyEvent.VK_DOWN);
        Thread.sleep(500L);
        assertTrue(pacManUI.getLosePanel().isShowing());
        Thread.sleep(500L);

        // Showing Home when press home
        pacManUI.getLosePanel().getHomeButton().doClick();
        assertTrue(pacManUI.getStartPanel().isShowing());
        Thread.sleep(500L);

    }



    /**
     * Make number of moves in given direction.
     *
     * @param game The game we're playing
     * @param dir The direction to be taken
     * @param numSteps The number of steps to take
     */
    public static void move(Game game, Direction dir, int numSteps) {
        Player player = game.getPlayers().get(0);
        for (int i = 0; i < numSteps; i++) {
            game.move(player, dir);
        }
    }

    /**
     * virtualize pressing key
     *
     * @param keys key to virtualizing
     */
    public static void pressKey(int ...keys){
        for (int key : keys) {
            bot.keyPress(key);
        }
    }

    /**
     * virtualize pressing key with repeat it
     *
     * @param n number to repeat virtualizing
     * @param keys key to virtualizing
     */
    public static void pressKeyN(int n, int ...keys){
        for (int i = 0; i < n; i++) {
            pressKey(keys);
        }
    }

}
