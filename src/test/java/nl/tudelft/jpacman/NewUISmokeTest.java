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

public class NewUISmokeTest {
    private NewPacManUI pacManUI;
    private static Robot bot;

    @BeforeEach
    void setup(){
        pacManUI = new NewPacManUI();
        pacManUI.start();

        try { bot = new Robot();
        }catch (Exception e){}
    }

    @AfterEach
    void tearDown(){
        pacManUI.dispose();
    }

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

    public static void pressKey(int ...keys){
        for (int key : keys) {
            bot.keyPress(key);
        }
    }

    public static void pressKeyN(int n, int ...keys){
        for (int i = 0; i < n; i++) {
            pressKey(keys);
        }
    }

}
