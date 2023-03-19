package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SinglePlayerPacmanFactoryTest {
    private SinglePlayerPacmanFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new SinglePlayerPacmanFactory();
    }

    @Test
    public void testCreatePacmanFromFile() {
        String map = SinglePlayerPacmanFactory.LEVEL_1;
        assertNotNull(factory.createPacman(map));
    }

    @Test
    public void testCreatePacmanFromCharArray() {
        char[][] map = {{'#','#','#','#'},{'#','P','.','#'},{'#','.','.','#'},{'#','.','.','#'},{'#','#','#','#'}};
        assertNotNull(factory.createPacman(map));
    }

    @Test
    public void testCreatePacmanFromList() {
        List<String> map = Arrays.asList("# # # #","# P . #","# . . #","# . . #","# # # #");
        assertNotNull(factory.createPacman(map));
    }

    @Test
    public void testCreatePacmanLevel1() {
        assertNotNull(factory.createPacmanLevel1());
    }

    @Test
    public void testCreatePacmanLevel2() {
        assertNotNull(factory.createPacmanLevel2());
    }

    @Test
    public void testCreatePacmanLevel3() {
        assertNotNull(factory.createPacmanLevel3());
    }

    @Test
    public void testCreatePacmanLevel4() {
        assertNotNull(factory.createPacmanLevel4());
    }

    @Test
    public void testCreatePacmanLevel5() {
        assertNotNull(factory.createPacmanLevel5());
    }

    @Test
    public void testConstructorWithDependencies() {
        GameFactory gameFactory = new GameFactory(new PlayerFactory(new PacManSprites()));
        MapParser mapParser = new MapParser(new LevelFactory(new PacManSprites(), new GhostFactory(new PacManSprites()), new PointCalculatorLoader().load()), new BoardFactory(new PacManSprites()));
        PointCalculator pointCalculator = new PointCalculatorLoader().load();
        SinglePlayerPacmanFactory factoryWithDependencies = new SinglePlayerPacmanFactory(gameFactory, mapParser, pointCalculator);
        assertNotNull(factoryWithDependencies);
    }
}
