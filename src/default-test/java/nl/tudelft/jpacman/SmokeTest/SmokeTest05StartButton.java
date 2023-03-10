package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.awt.event.InputEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class SmokeTest05StartButton {

    private Launcher launcher;
    private Game game;
    private Level level;
    private Robot bot;
    private Player player;
    private GameFactory gameFactory;
    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    void setUp() throws AWTException{
        launcher = new Launcher();
        bot = new Robot();
    }
    /**
     * Close the user interface.
     */

   @AfterEach
    public void after() {
        launcher.dispose();
    }

    @Test
    public void OpenGame(){
        launcher.launch();
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
    }

    private Game getGame() {
        return launcher.getGame();
    }

    void clickStartBtn() {
        bot.mouseMove(150, 320);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    @Test
    public void testAllPelletsCollected() {
        Launcher launcher = new Launcher();
        launcher.withMapFile("/board.txt");
        Game game = launcher.makeGame();
        launcher.launch();
        assertThat(getGame().isInProgress()).isTrue();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Player player = game.getPlayers().get(0);
        while (game.isInProgress() && level.remainingPellets() > 0) {
            player.setDirection(Direction.values()[(int) (Math.random() * Direction.values().length)]);
            game.move(player, player.getDirection());
        }

        // No pellets left = Finnish
        assertThat(level.remainingPellets()).isZero();

        // Verify that the game is in progress and the player has not moved
        assertTrue(game.isInProgress());
        assertFalse(player.isAlive());

        //Restart to play again
        clickStartBtn();
        assertThat(getGame().isInProgress()).isTrue();

    }




}











