package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.SinglePlayerGame;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test of {@link SinglePlayerPacmanFactory}
 *
 * @author Anirut
 */
public class SinglePlayerPacmanFactoryTest {
    private SinglePlayerPacmanFactory factory;

    /**
     * Create new {@link SinglePlayerPacmanFactory} before start a test
     */
    @BeforeEach
    public void setUp() {
        factory = new SinglePlayerPacmanFactory();
    }

    /**
     * Test creating a game from map file.txt
     */
    @Test
    public void testCreatePacmanFromFile() {
        String map = SinglePlayerPacmanFactory.LEVEL_1;

        Game game = factory.createPacman(map);

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * test create game from not existed file map
     */
    @Test
    public void testCreatePacmanFromNotExistedFile() {
        String map = "notExistedFile";

        assertThrows(PacmanConfigurationException.class, ()->{
            factory.createPacman("");
        });
    }

    /**
     * Test creating a game from Char Array
     */
    @Test
    public void testCreatePacmanFromCharArray() {
        char[][] map = {
            {'#','#','#','#'},
            {'#','P','.','#'},
            {'#','.','.','#'},
            {'#','.','.','#'},
            {'#','#','#','#'}};

        Game game = factory.createPacman(map);

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * Test creating a game from List of String
     */
    @Test
    public void testCreatePacmanFromList() {
        List<String> map = Arrays.asList(
            "# # # #",
            "# P . #",
            "# . . #",
            "# . . #",
            "# # # #");

        Game game = factory.createPacman(map);

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * Test creating a game with {@link SinglePlayerPacmanFactory#createPacmanLevel1()} should return a new {@link Game}
     */
    @Test
    public void testCreatePacmanLevel1() {
        Game game = factory.createPacmanLevel1();

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * Test creating a game with {@link SinglePlayerPacmanFactory#createPacmanLevel2()} should return a new {@link Game}
     */
    @Test
    public void testCreatePacmanLevel2() {
        Game game = factory.createPacmanLevel2();

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * Test creating a game with {@link SinglePlayerPacmanFactory#createPacmanLevel3()} should return a new {@link Game}
     */
    @Test
    public void testCreatePacmanLevel3() {
        Game game = factory.createPacmanLevel3();

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * Test creating a game with {@link SinglePlayerPacmanFactory#createPacmanLevel4()} should return a new {@link Game}
     */
    @Test
    public void testCreatePacmanLevel4() {
        Game game = factory.createPacmanLevel4();

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * Test creating a game with {@link SinglePlayerPacmanFactory#createPacmanLevel5()} should return a new {@link Game}
     */
    @Test
    public void testCreatePacmanLevel5() {
        Game game = factory.createPacmanLevel5();

        assertNotNull(game);
        assertTrue(game instanceof SinglePlayerGame);
        assertFalse(game.isInProgress());
    }

    /**
     * Test {@link SinglePlayerPacmanFactory} dependency injection
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testConstructorWithDependencies() throws NoSuchFieldException, IllegalAccessException {
        PointCalculator pointCalculator = new PointCalculatorLoader().load();
        PacManSprites SPRITE_STORE = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(SPRITE_STORE);
        PlayerFactory playerFactory = new PlayerFactory(SPRITE_STORE);
        BoardFactory boardFactory = new BoardFactory(SPRITE_STORE);
        LevelFactory levelFactory = new LevelFactory(SPRITE_STORE,ghostFactory,pointCalculator);
        GameFactory gameFactory = new GameFactory(playerFactory);
        MapParser mapParser = new MapParser(levelFactory,boardFactory);

        var singlePlayerPacmanFactory = new SinglePlayerPacmanFactory(gameFactory,mapParser,pointCalculator);
        GameFactory gf = (GameFactory) getField("gameFactory", singlePlayerPacmanFactory);
        MapParser mp = (MapParser) getField("mapParser",singlePlayerPacmanFactory);
        PointCalculator pc = (PointCalculator) getField("pointCalculator",singlePlayerPacmanFactory);

        assertEquals(gameFactory,gf);
        assertEquals(mapParser,mp);
        assertEquals(pointCalculator,pc);
    }

    /**
     * Function that help to getting a private field
     *
     * @param name name of field that want to get
     * @param obj object that want to get field from it
     *
     * @return a {@code name} field of {@code obj}
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object getField(String name, SinglePlayerPacmanFactory obj)
                            throws NoSuchFieldException, IllegalAccessException {
        Field field = SinglePlayerPacmanFactory.class.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
    }
}
