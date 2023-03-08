package nl.tudelft.jpacman.SmokeTest;

import jdk.jfr.Percentage;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmokeTest04PacmanSpeed {
    private Launcher launcher;
    private Robot bot;
    private long intervalTime = 500;
    private int scoreInIntervalTime = 50;
    private long delayAfterKeyup = 2000;
    private int scoreEpsilon = 30;
    @BeforeEach
    void setUp() throws AWTException{
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
    @Test
    void pacmanSpeedTest()
        throws InterruptedException{
        launcher.withMapFile("/testBoard/board.txt").launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        clickStartBtn();
        enterKey(KeyEvent.VK_RIGHT,delayAfterKeyup);

        assertEquals(scoreInIntervalTime,player.getScore(),scoreEpsilon);
    }
    @Test
    void pacmanSpeedTestInterval() throws InterruptedException{
        launcher.withMapFile("/testBoard/board.txt").launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        clickStartBtn();
        enterKeyInterval(KeyEvent.VK_RIGHT,delayAfterKeyup);

        assertEquals(scoreInIntervalTime,player.getScore(),scoreEpsilon);
    }

    /**
     * Move mouse to start btn then click
     */
    void clickStartBtn(){
        bot.mouseMove(   600,120);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * Simulate pressing key on keyboard
     *
     * @param key {@link KeyEvent} to simulate
     * @param delayAfter delay after press key
     * @throws InterruptedException
     */
    void enterKeyInterval(int key,long delayAfter) throws InterruptedException{
        long a = System.currentTimeMillis() + intervalTime;
        while(System.currentTimeMillis() < a){
            bot.keyPress(key);
        }
        bot.keyRelease(key);
        Thread.sleep(delayAfter);
    }

    void enterKey(int key,long delayAfter) throws InterruptedException{
        bot.keyPress(key);
        bot.keyRelease(key);
        Thread.sleep(delayAfter);
    }
}
