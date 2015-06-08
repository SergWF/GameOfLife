package my.outfittery.life.logic.handling;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.entity.GameState;
import my.outfittery.life.logic.entity.checks.CyclingStateChecker;
import my.outfittery.life.logic.entity.checks.MinumumLiveAmountChecker;
import my.outfittery.life.logic.entity.rules.TooMuchNeighboursRule;
import my.outfittery.life.logic.entity.rules.ExpansionRule;
import my.outfittery.life.logic.entity.rules.LowNeighboursRule;
import my.outfittery.life.storage.StateStorageImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameHandlerTest {

    GameHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new GameHandler();
        handler.addRule(new ExpansionRule(3));
        handler.addRule(new TooMuchNeighboursRule(3));
        handler.addRule(new LowNeighboursRule(2));
        handler.addChecker(new MinumumLiveAmountChecker(2));
        handler.addChecker(new CyclingStateChecker(new StateStorageImpl()));
    }

    @Test
    public void testInitMap(){
        //GIVEN: new game
        //WHEN: Game initialized
        GameMap gameMap = handler.initMap(3, 4);
        //THEN Properties should be in the beginning state
        Assert.assertThat(gameMap,
                Matchers.allOf(
                        Matchers.hasProperty("xSize", Matchers.equalTo(3)),
                        Matchers.hasProperty("ySize", Matchers.equalTo(4)),
                        Matchers.hasProperty("gameState", Matchers.equalTo(GameState.NEW)),
                        Matchers.hasProperty("message", Matchers.equalTo("Welcome!"))
                )
        );
    }

    @Test
    public void testPlaceLife(){
        //GIVEN: Game initialized
        GameMap gameMap = handler.initMap(3, 4);
        //WHEN: place Life in a call
        gameMap = handler.placeLife(gameMap, 2,1);
        //THEN: Life should be found in this cell
        Assert.assertTrue(gameMap.isAlive(2, 1));
    }

    @Test
    public void testCheckCellByRules(){
        GameMap map = handler.initMap(5,5);
        handler.placeLife(map, 2, 1);
        handler.placeLife(map, 3, 2);
        handler.placeLife(map, 2, 3);
        Assert.assertTrue(handler.checkCellByRules(map, 2, 2));
        Assert.assertTrue(handler.checkCellByRules(map, 3, 2));

        Assert.assertFalse(handler.checkCellByRules(map, 2, 1));
        Assert.assertFalse(handler.checkCellByRules(map, 2, 3));

    }

    @Test
    public void testNextStep(){
        //GIVEN: Game initialized
        GameMap gameMap = handler.initMap(5, 7);
        //AND: Life was placed:
        gameMap = handler.placeLife(gameMap, 2,0);
        gameMap = handler.placeLife(gameMap, 2,1);
        gameMap = handler.placeLife(gameMap, 2,2);
        //WHEN: do next step
        GameMap newGameMap = handler.makeTurn(gameMap);
        //THEN: Should be generated new one map with same ID
        Assert.assertNotEquals(gameMap, newGameMap);
        Assert.assertEquals(gameMap.getId(), newGameMap.getId());
    }

}