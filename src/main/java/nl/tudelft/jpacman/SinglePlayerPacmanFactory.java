package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.game.SinglePlayerGame;

import java.io.IOException;
import java.util.List;

/**
 * Factory that create Pacman {@link SinglePlayerGame}
 *
 * @author Anirut Chaogla
 */
public class SinglePlayerPacmanFactory {
    public static String LEVEL_1 = "/board1.txt";
    public static String LEVEL_2 = "/board2.txt";
    public static String LEVEL_3 = "/board3.txt";
    public static String LEVEL_4 = "/board4.txt";
    public static String LEVEL_5 = "/board5.txt";

    private final GameFactory gameFactory;
    private final MapParser mapParser;
    private final PointCalculator pointCalculator;

    /**
     * Create a new {@link SinglePlayerPacmanFactory} with default dependency
     */
    public SinglePlayerPacmanFactory() {
        pointCalculator = new PointCalculatorLoader().load();
        PacManSprites SPRITE_STORE = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(SPRITE_STORE);
        PlayerFactory playerFactory = new PlayerFactory(SPRITE_STORE);
        BoardFactory boardFactory = new BoardFactory(SPRITE_STORE);
        LevelFactory levelFactory = new LevelFactory(SPRITE_STORE,ghostFactory,pointCalculator);
        gameFactory = new GameFactory(playerFactory);
        mapParser = new MapParser(levelFactory,boardFactory);
    }

    /**
     * Create a new {@link SinglePlayerPacmanFactory} with dependency
     *
     * @param gameFactory The factory providing a game
     * @param mapParser The parser map to create a {@link Level}
     * @param pointCalculator Point Calculator for game
     */
    public SinglePlayerPacmanFactory( GameFactory gameFactory, MapParser mapParser,
                                    PointCalculator pointCalculator) {
        this.gameFactory = gameFactory;
        this.mapParser = mapParser;
        this.pointCalculator = pointCalculator;
    }

    /**
     * Create Pacman game with map from file map.txt
     * @param map Path to file map
     * @return a game that created by {@code  map}
     */
    public Game createPacman(String map){
        try {
            Level level = mapParser.parseMap(map);
            return gameFactory.createSinglePlayerGame(level,pointCalculator);
        } catch (Exception e) {
            throw new PacmanConfigurationException(
                "Unable to create level, name = " + LEVEL_1, e);
        }
    }

    /**
     * Create Pacman game with map from {@code char[][] map}
     * @param map map as {@code char[][]}
     * @return a game that created by {@code  map}
     */
    public Game createPacman(char[][] map){
        return gameFactory.createSinglePlayerGame(mapParser.parseMap(map), pointCalculator);
    }


    /**
     * Create Pacman game with map from {@code List<String> map}
     * @param map map as {@code List<String>}
     * @return a game that created by {@code  map}
     */
    public Game createPacman(List<String> map){
        return gameFactory.createSinglePlayerGame(mapParser.parseMap(map), pointCalculator);
    }

    /**
     * Create new Pacman game level 1
     * @return
     */
    public Game createPacmanLevel1(){return createPacman(LEVEL_1);}
    /**
     * Create new Pacman game level 2
     * @return
     */
    public Game createPacmanLevel2(){return createPacman(LEVEL_2);}
    /**
     * Create new Pacman game level 3
     * @return
     */
    public Game createPacmanLevel3(){return createPacman(LEVEL_3);}
    /**
     * Create new Pacman game level 4
     * @return
     */
    public Game createPacmanLevel4(){return createPacman(LEVEL_4);}
    /**
     * Create new Pacman game level 5
     * @return
     */
    public Game createPacmanLevel5(){return createPacman(LEVEL_5);}
}
