package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.ui.NewPacManUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_SPACE;
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
    private Long DELAY_TIME = 1000L;

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
    void showStartMenuWhenOpened()  {
        waitForUserSee();
        assertTrue(pacManUI.getStartPanel().isShowing());
    }

    @Test
    void showGameWhenPressStart(){
        showStartMenuWhenOpened();

        pacManUI.getStartPanel().getStartButton().doClick();
        waitForUserSee();

        assertTrue(pacManUI.getGameContainer().isShowing());
    }

    @Test
    void startGame(){
        showGameWhenPressStart();
        var game = pacManUI.getCurrentGame();

        assertFalse(game.isInProgress());
        pressKey(VK_SPACE);
        waitForUserSee(0.5);
        assertTrue(game.isInProgress());
    }
    @Test
    void pauseGame(){
        startGame();
        var game = pacManUI.getCurrentGame();

        assertTrue(game.isInProgress());
        pressKey(VK_SPACE);
        waitForUserSee(0.5);
        assertFalse(game.isInProgress());
    }
    @Test
    void resumeGame(){
        pauseGame();
        var game = pacManUI.getCurrentGame();

        assertFalse(game.isInProgress());
        pressKey(VK_SPACE);
        waitForUserSee(0.5);
        assertTrue(game.isInProgress());
    }

    @Test
    void showLosePageWhenCollisionWithGhost(){
        startGame();

        pressKeyN(20,VK_DOWN);
        waitForUserSee();
        assertTrue(pacManUI.getLosePanel().isShowing());
    }

    @Test
    void showStartMenuWhenClickBackHomeOnLosePage(){
        showLosePageWhenCollisionWithGhost();

        pacManUI.getLosePanel().getHomeButton().doClick();
        waitForUserSee();
        assertTrue(pacManUI.getStartPanel().isShowing());
    }
    @Test
    void showGameWhenClickRetryOnLosePage(){
        showLosePageWhenCollisionWithGhost();

        pacManUI.getLosePanel().getRetryButton().doClick();
        waitForUserSee();
        assertTrue(pacManUI.getGameContainer().isShowing());
    }

    @Test
    void showWinPageWhenYouWin(){
        startGame();
        pressKeyN(10,VK_LEFT);
        waitForUserSee();

        assertTrue(pacManUI.getWinPanel().isShowing());
    }
    @Test
    void showStartMenuWhenClickBackHomeOnWinPage() {
        showWinPageWhenYouWin();

        pacManUI.getWinPanel().getHomeButton().doClick();
        waitForUserSee();
        assertTrue(pacManUI.getStartPanel().isShowing());
    }
    @Test
    void showGameWhenClickRetryOnWinPage(){
        showWinPageWhenYouWin();

        pacManUI.getWinPanel().getRetryButton().doClick();
        waitForUserSee();
        assertTrue(pacManUI.getGameContainer().isShowing());
    }
    @Test
    void showNewGameWhenClickNextOnWinPage(){
        showWinPageWhenYouWin();

        pacManUI.getWinPanel().getNextButton().doClick();
        waitForUserSee();
        assertTrue(pacManUI.getGameContainer().isShowing());
    }

    @Test
    void showEndGameWhenPassFiveLevel(){
        startGame();

        for (int i = 0; i < 5; i++) {
            pacManUI.getCurrentGame().start();
            assertTrue(pacManUI.getGameContainer().isShowing());
            pressKeyN(10,VK_LEFT);
            waitForUserSee(0.5);
            assertTrue(pacManUI.getWinPanel().isShowing());
            pacManUI.getWinPanel().getNextButton().doClick();
            waitForUserSee(0.5);
        }

        waitForUserSee(1);
        assertTrue(pacManUI.getEndAllPanel().isShowing());
    }

    @Test
    void showStartMenuWhenClickHomeOnEndgame() throws InterruptedException {
        showEndGameWhenPassFiveLevel();

        pacManUI.getEndAllPanel().getHomeButton().doClick();
        waitForUserSee();

        assertTrue(pacManUI.getStartPanel().isShowing());
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

    private void waitForUserSee(){
        waitForUserSee(1);
    }
    private void waitForUserSee(double percent){
        assert percent >= 0;
        assert percent <= 1;

        try {
            Thread.sleep((long) (DELAY_TIME * percent));
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }

}
