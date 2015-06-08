package my.outfittery.life.logic.entity.checks;


import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.entity.GameState;
import my.outfittery.life.logic.handling.StateStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.UUID;

public class CyclingStateCheckerTest {

    CyclingStateChecker checker;

    private class TestGameMap extends GameMap {

        protected TestGameMap(int xSize, int ySize, boolean[][] map) {
            super(xSize, ySize, map);
        }
    }

    GameMap map;
    GameMap map_unic;
    GameMap map_rotated;
    GameMap map_same1;
    GameMap map_same2;

    boolean[][] arr = new boolean[][]{
            { false, true, false, true, false },
            { false, false, false, false, false },
            { false, false, false, false, false },
            { false, false, false, false, false }
    };
    boolean[][] arr_unic = new boolean[][]{
            { true, true, false, true, false },
            { true, false, false, false, false },
            { true, false, false, false, false },
            { false, false, false, false, false }
    };

    boolean[][] arr_rotated = new boolean[][]{
            { false, false,  false, false},
            { true, false, false,  false},
            { false, false, false,  false},
            { true, false, false,  false},
            { false, false,  false, false}
    };

    boolean[][] arr1 = new boolean[][]{
            { false, true,  false, true,  false },
            { false, false, true,  true,  false },
            { false, false, true,  false, false },
            { false, true,  false, true,  false }
    };
    boolean[][] arr2 = new boolean[][]{
            { false, true,  false, true,  false },
            { false, false, true,  true,  false },
            { false, false, true,  false, false },
            { false, true,  false, true,  false }
    };


    @Before
    public void setUp() throws Exception {
        StateStorage storage = Mockito.mock(StateStorage.class);
        checker = Mockito.spy(new CyclingStateChecker(storage));
        map = new TestGameMap(5,4,arr);
        map_unic = new TestGameMap(5,4,arr_unic);
        map_rotated = new TestGameMap(5,4,arr_rotated);
        map_same1 = new TestGameMap(5,4,arr1);
        map_same2 = new TestGameMap(5,4,arr2);
        Mockito.doReturn(Arrays.asList(map,map_rotated, map_same1)).when(storage).history(Mockito.any(UUID.class));
    }

    @Test
    public void testCheckIsSamePositive(){
        Assert.assertTrue(checker.isSame(map_same1, map_same2));
    }

    @Test
    public void testCheckIsSameNegative(){
        Assert.assertFalse(checker.isSame(map, map_unic));
    }

    @Test
    public void testCheckIsSameNegativeRotated(){
        Assert.assertFalse(checker.isSame(map, map_rotated));
    }

    @Test
    public void testCheckCyclingPositive(){
        Assert.assertEquals(GameState.GAMEOVER, checker.check(map_same2));
    }

    @Test
    public void testCheckCyclingNegative(){
        Assert.assertNotEquals(GameState.PLAYING, checker.check(map_unic));
    }
}