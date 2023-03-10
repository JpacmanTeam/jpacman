package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.awt.event.InputEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SmokeTest05StartButton {

    private Launcher launcher;
    private Game game;
    private Level level;
    private Robot bot;
    private Player player;
    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    public void before() {
        launcher = new Launcher();


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

    @Test
    public void  TestNoPelletLeft(){

        game.start();
        Level level = game.getLevel();
        level.registerPlayer(player);

        // Collect all pellets in the level
        while (level.remainingPellets() > 0) {
            game.move(player, Direction.EAST);
            game.move(player, Direction.NORTH);
            game.move(player, Direction.WEST);
            game.move(player, Direction.SOUTH);
        }
        // No pellets left = Finnish
        assertThat(level.remainingPellets()).isZero();

        // Verify that the game is in progress and the player has not moved
        assertTrue(game.isInProgress());
        assertFalse(player.isAlive());

        //Restart to play again
        clickStartBtn();

        }

    private Game getGame() {
        return launcher.getGame();
    }

    void clickStartBtn() {
        bot.mouseMove(150, 320);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}











