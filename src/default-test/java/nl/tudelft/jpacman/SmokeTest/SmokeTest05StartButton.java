package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;



public class SmokeTest05StartButton {

    private Launcher launcher;
    private Game game;
    private Level level;
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
    public void  TestNoPelletLeft() {
        // check that there are no pellets left
        // No pellets left = Finnish
        assertThat(level.remainingPellets()).isZero();
        //Game needs to finish
        assertThat(getGame().isInProgress()).isFalse();
        //Restart to play again
        getGame().start();

    }

    private Game getGame() {
        return launcher.getGame();
    }

}









