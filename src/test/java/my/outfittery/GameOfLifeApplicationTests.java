package my.outfittery;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.entity.GameState;
import my.outfittery.life.logic.entity.checks.CyclingStateChecker;
import my.outfittery.life.logic.entity.checks.MinumumLiveAmountChecker;
import my.outfittery.life.logic.handling.GameHandler;
import my.outfittery.life.logic.handling.StateStorage;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GameOfLifeApplication.class)
@WebAppConfiguration
public class GameOfLifeApplicationTests {


    @Autowired
    ApplicationContext context;

    @Autowired
    GameHandler handler;
    @Autowired
    StateStorage stateStorage;


	@Test
	public void contextLoads() {
    }

    @Test
    public void testMakeTurn(){
        GameMap map = handler.initMap(5,5);
        handler.placeLife(map, 2, 1);
        handler.placeLife(map, 3, 2);
        handler.placeLife(map, 2, 3);
        printMap(map);
        Assert.assertTrue(map.isAlive(2, 1));
        Assert.assertTrue(map.isAlive(3, 2));
        Assert.assertTrue(map.isAlive(2, 3));
        Assert.assertThat(map, Matchers.allOf(
                Matchers.hasProperty("gameState", Matchers.equalTo(GameState.NEW)),
                Matchers.hasProperty("step", Matchers.equalTo(0L)),
                Matchers.hasProperty("message", Matchers.equalTo("Welcome!"))
        ));
        handler.printMap(map);
        GameMap map1 = handler.makeTurn(map);
        handler.printMap(map1);
        Assert.assertThat(map1, Matchers.allOf(
                Matchers.hasProperty("gameState", Matchers.equalTo(GameState.PLAYING)),
                Matchers.hasProperty("step", Matchers.equalTo(1L)),
                Matchers.hasProperty("message", Matchers.equalTo("Do Next Turn"))
        ));

        GameMap map2 = handler.makeTurn(map1);
        handler.printMap(map2);
        Assert.assertThat(map2, Matchers.allOf(
                Matchers.hasProperty("gameState", Matchers.equalTo(GameState.GAMEOVER)),
                Matchers.hasProperty("step", Matchers.equalTo(2L)),
                Matchers.hasProperty("message", Matchers.equalTo(MinumumLiveAmountChecker.MESSAGE))
        ));
    }

    @Test
    public void testGameCycle(){
        GameMap map = handler.initMap(5,5);
        handler.placeLife(map, 2, 1);
        handler.placeLife(map, 1, 2);
        handler.placeLife(map, 3, 2);
        handler.placeLife(map, 2, 3);
        stateStorage.save(map);
        System.out.println(handler.printMap(map));
        Assert.assertTrue(map.isAlive(2, 1));
        Assert.assertTrue(map.isAlive(3, 2));
        Assert.assertTrue(map.isAlive(2, 3));
        int maxTurnCount = 10;
        while(!GameState.GAMEOVER.equals(map.getGameState())){
            map = handler.makeTurn(map);
            stateStorage.save(map);
            if(map.getStep() > maxTurnCount){
                break;
            }
        }
        Assert.assertThat(map, Matchers.allOf(
                Matchers.hasProperty("gameState", Matchers.equalTo(GameState.GAMEOVER)),
                Matchers.hasProperty("step", Matchers.equalTo(7L)),
                Matchers.hasProperty("message", Matchers.equalTo(CyclingStateChecker.MESSAGE))
        ));
    }

    protected void printMap(GameMap map){
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < map.getySize(); y++){
            for(int x = 0; x < map.getxSize(); x++){
                sb.append(map.isAlive(x, y)?"x":".");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
