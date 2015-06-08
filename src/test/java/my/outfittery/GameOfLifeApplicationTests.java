package my.outfittery;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.handling.GameHandler;
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


	@Test
	public void contextLoads() {
    }

    @Test
    public void testGame(){
        GameHandler handler = getHandler();
        GameMap map = handler.initMap(5,5);
        handler.placeLife(map, 2, 1);
        handler.placeLife(map, 3, 2);
        handler.placeLife(map, 2, 3);
        printMap(map);
        Assert.assertTrue(map.isAlive(2, 1));
        Assert.assertTrue(map.isAlive(3, 2));
        Assert.assertTrue(map.isAlive(2, 3));
        GameMap map1 = handler.makeTurn(map);
        GameMap map2 = handler.makeTurn(map1);
    }

    @Test
    public void testGameCycle(){
        GameHandler handler = getHandler();
        GameMap map = handler.initMap(5,5);
        handler.placeLife(map, 2, 1);
        handler.placeLife(map, 1, 2);
        handler.placeLife(map, 3, 2);
        handler.placeLife(map, 2, 3);
        handler.printMap(map);
        Assert.assertTrue(map.isAlive(2, 1));
        Assert.assertTrue(map.isAlive(3, 2));
        Assert.assertTrue(map.isAlive(2, 3));
        for(int i = 0; i < 10; i++){
            map = handler.makeTurn(map);
        }
    }

    private GameHandler getHandler(){
        return context.getBean(GameHandler.class);
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
