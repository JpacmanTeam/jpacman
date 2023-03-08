package nl.tudelft.jpacman.uat;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
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
import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-To-End Test how to control pacman direction
 * by using arrow btn and A, S, D, and W on keyboard
 *
 * @author Anirut Chaogla
 */
public class UAT03MovementTest {
    private Launcher launcher;
    private Robot bot;

    /**
     * Open the game
     */
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

    @ParameterizedTest(name = "TC{index} {0} ")
    @CsvSource({
        "/testBoard/boardMoveUp.txt," + KeyEvent.VK_UP + ", 60",
        "/testBoard/boardMoveDown.txt," + KeyEvent.VK_DOWN + ", 60",
        "/testBoard/boardMoveLeft.txt," + KeyEvent.VK_LEFT + ", 100",
        "/testBoard/boardMoveRight.txt," + KeyEvent.VK_RIGHT + ", 100"
    })
    void turnNorth(String source, int key, int expectedScore) throws InterruptedException, AWTException{
        launcher
            .withMapFile(source)
            .launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        clickStartBtn();
        enterKey(key);

        Thread.sleep(5000);
        assertThat(player.getScore()).isEqualTo(expectedScore);
    }

    void clickStartBtn(){
        bot.mouseMove(150,320);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    void enterKey(int key){
        bot.keyPress(key);
        bot.keyRelease(key);
    }
}
