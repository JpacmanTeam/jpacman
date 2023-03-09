package nl.tudelft.jpacman.SmokeTest;

import static org.assertj.core.api.Assertions.assertThat;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test start after game over

 * @author Netchanok Muaengkhot
 */
public class SmokeTest02StartAfterOvergame {
    private Launcher launcher;

    /**
     * Launch the user interface.
     */
    @BeforeEach
    void setUpPacman() {
        launcher = new Launcher();
        launcher.launch();
    }

    /**
     * Quit the user interface when we're done.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }


    @Test
    public void gameIsOpenning() {
        launcher.launch();

        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }
    @Test
    void startAfterOvergame() throws InterruptedException {
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        //simulate playing game
        // start
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        // now moving back does not change the score
        game.move(player, Direction.WEST);
        assertThat(player.getScore()).isEqualTo(10);

        // try to move as far as we can
        move(game, Direction.EAST, 7);
        assertThat(player.getScore()).isEqualTo(60);

        // move towards the monsters
        move(game, Direction.NORTH, 6);
        assertThat(player.getScore()).isEqualTo(120);

        // no more points to earn here.
        move(game, Direction.WEST, 2);
        assertThat(player.getScore()).isEqualTo(120);

        move(game, Direction.NORTH, 2);

        // Sleeping in tests is generally a bad idea.
        // Here we do it just to let the monsters move.
        Thread.sleep(500L);
        // close to monsters, this will get us killed.
        move(game, Direction.WEST, 10);
        move(game, Direction.EAST, 10);
        assertThat(player.isAlive()).isFalse();
        game.stop();
        assertThat(game.isInProgress()).isFalse();


        //restart again
        game.start();
        assertThat(game.isInProgress()).isTrue();


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


    private Game getGame() {
        return launcher.getGame();
    }
}

