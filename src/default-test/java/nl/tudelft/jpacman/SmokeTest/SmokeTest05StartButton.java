package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;


import static org.assertj.core.api.Assertions.assertThat;



public class SmokeTest05StartButton {

    private Launcher launcher;


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
    public void testRestartGameAfterFinish() {
        launcher.launch();
        getGame().start();

        //Game needs to finish
        assertThat(getGame().isInProgress()).isFalse();

    }

    private Game getGame() {
        return launcher.getGame();
    }
}









