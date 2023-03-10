package nl.tudelft.jpacman.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("UC001 PacmanUIBuilder")
public class PacmanUiBuilderTest {

    private PacManUiBuilder builder;
    Field keyMappingsFieldHelper = PacManUiBuilder.class.getDeclaredField("keyMappings");
    public PacmanUiBuilderTest() throws NoSuchFieldException { }

    @BeforeEach void setUp(){
        builder = new PacManUiBuilder();
        keyMappingsFieldHelper.setAccessible(true);
    }

    @Test()
    @DisplayName("TS000 create builder instant")
    void createBuilderInstant(){
        assertThat(builder).isInstanceOf(PacManUiBuilder.class);
    }

    @DisplayName("TS001 add key with action")
    @Test void addKey() throws IllegalAccessException {
        int key = KeyEvent.VK_UP;
        Action actionDummy = ()->{};

        PacManUiBuilder ui = builder.addKey(key,actionDummy);
        var keyMapping = (Map<Integer, Action>) keyMappingsFieldHelper.get(ui);

        assertTrue(keyMapping.containsKey(key));
        assertThat(keyMapping.get(key)).isEqualTo(actionDummy);
    }

    @DisplayName("TS002 add key with pressAction and releaseAction")
    @Test void addKeyWithStopAction() throws IllegalAccessException {
        int key = KeyEvent.VK_UP;
        int keyReleased = KeyEvent.VK_UP + KeyEvent.KEY_RELEASED;
        Action actionDummy = ()->{};
        Action stopActionDummy = ()->{};

        PacManUiBuilder ui = builder.addKey(key,actionDummy,stopActionDummy);
        var keyMapping = (Map<Integer, Action>) keyMappingsFieldHelper.get(ui);

        assertTrue(keyMapping.containsKey(key));
        assertTrue(keyMapping.containsKey(keyReleased));
        assertThat(keyMapping.get(key)).isEqualTo(actionDummy);
        assertThat(keyMapping.get(keyReleased)).isEqualTo(stopActionDummy);
    }

}
