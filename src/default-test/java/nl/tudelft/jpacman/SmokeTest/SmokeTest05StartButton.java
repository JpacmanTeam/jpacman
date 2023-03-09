package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SmokeTest05StartButton {



    private Launcher launcher;
    /**
     * The robot for making mouse and press event
     */
    private Robot bot;
    /**
     * Open the game
     */
    @BeforeEach
    void setUp() throws AWTException {
        launcher = new Launcher();
        bot = new Robot();
    }



    /**
     * Close the game
     */
    @AfterEach
    void tearDown(){
        launcher.dispose();
    }
    @Mock
    private Level level;

    @Mock
    private Object progressLock;

    private Game game;



    @Test
    public void testRestartGameAfterFinish() {
        // Set up the level to return 0 pellets and no alive players
        when(level.remainingPellets()).thenReturn(0);
        when(level.isAnyPlayerAlive()).thenReturn(true);

        // Start the game
        game.start();

        // Verify that the game is no longer in progress
        assertFalse(game.isInProgress());

        // Restart the game
        game.start();

        // Verify that the level's addObserver() and start() methods are called
        verify(level).addObserver(game);
        verify(level).start();

        // Verify that the game is now in progress
        assertTrue(game.isInProgress());
    }

}
