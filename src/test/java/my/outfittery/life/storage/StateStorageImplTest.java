package my.outfittery.life.storage;

import my.outfittery.life.logic.entity.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StateStorageImplTest {

    StateStorageImpl stateStorage;


    @Before
    public void setUp() throws Exception {
        stateStorage = new StateStorageImpl();
    }

    @Test
    public void testSaveNew() throws Exception {
        //GIVEN: new GameMap
        GameMap gameMap = new GameMap(3,4);
        //WHEN: save to Storage
        gameMap = stateStorage.save(gameMap);
        //THEN: should has Id value and be present in storage
        Assert.assertNotNull(gameMap.getId());
        Assert.assertEquals(gameMap, stateStorage.history(gameMap.getId()).get(0));

    }

    @Test
    public void testSaveExisting() throws Exception {
        //GIVEN: already saved GameMap
        GameMap gameMap = new GameMap(3,4);
        gameMap = stateStorage.save(gameMap);
        Assert.assertEquals(1, stateStorage.history(gameMap.getId()).size());
        //WHEN: save to Storage
        gameMap = stateStorage.save(gameMap);
        //THEN: Amount of saved maps should be increased
        Assert.assertEquals(2, stateStorage.history(gameMap.getId()).size());
    }


}