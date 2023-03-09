package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

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


   /* @Test
    public void testActionAfterGameFinish() {
        // Create a mock game object
        Game game = Mockito.mock(Game.class);

        // Mock the game to finish playing
        Mockito.doAnswer(invocation -> {
            game.finish();
            return null;
        }).when(game).play();

        // Call the method being tested after the game is finished
        // In this example, the method is called "doSomethingAfterGameFinish"
        game.play();
        Mockito.verify(game).doSomethingAfterGameFinish();
    } */

}
