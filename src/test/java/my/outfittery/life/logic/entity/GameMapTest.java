package my.outfittery.life.logic.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameMapTest {
    private int xSize = 5;
    private int ySize = 4;
/*
     01234

0    01010
1    00110
2    00100
3    01010
*/
    GameMap state;
    boolean[][] map;

    @Before
    public void setUp() throws Exception {
        map = new boolean[][]{
                { false, true,  false, true,  false },
                { false, false, true,  true,  false },
                { false, false, true,  false, false },
                { false, true,  false, true,  false }
        };
        state = new GameMap(xSize, ySize, map);
    }


    @Test
    public void testIsAlivePositive(){
        //GIVEN: cell with a life (1, 3)
        //WHEN: check this cell
        //THEN: Life found
        Assert.assertTrue(state.isAlive(1,3));
    }

    @Test
    public void testIsAliveNegative(){
        //GIVEN: cell without a life
        //WHEN: check this cell
        //THEN: Life not found
        Assert.assertFalse(state.isAlive(2,3));
    }



    @Test
    public void testPlaceLife(){
        //GIVEN: cell without a life
        Assert.assertFalse(state.isAlive(2, 0));
        //WHEN: Add  a life to this cell
        state.placeLife(2,0, true);
        //THEN: Life is present
        Assert.assertTrue(state.isAlive(2,0));
    }

    @Test
    public void testkillLife(){
        //GIVEN: Cell 3x1 Has a Life
        Assert.assertTrue(state.isAlive(2, 2));
        //WHEN: kill a life in this cell
        state.placeLife(2, 2, false);
        //THEN: No life found in this cell
        Assert.assertFalse(state.isAlive(2, 2));
    }

    @Test
    public void testCircularPositive(){
        int dimSize = 5;
        int val = 0;
        for(int i = 0; i < 1000; i++){
            if(val >= dimSize){
                val = 0;
            }
            Assert.assertEquals("check " + i, val++, state.circular(i, dimSize));
        }
    }

    @Test
    public void testCircularNegative(){
        int dimSize = 5;
        int val = 0;
        for(int i = 0; i > -1000; i--){
            if(val < 0){
                val = dimSize -1;
            }
            Assert.assertEquals("check " + i, val--, state.circular(i, dimSize));
        }
    }

    @Test
    public void testGetAlive00(){
        map = new boolean[][]{
                { true, false,  false, false,  false },
                { false, false,  false, false,  false }
        };
        state = new GameMap(xSize, ySize, map);
        Assert.assertTrue(state.isAlive(0, 0));
    }

    @Test
    public void getAliveMaxMax(){
        map = new boolean[][]{
                { false, false,  false, false,  false },
                { false, false,  false, false,  true }
        };
        state = new GameMap(xSize, ySize, map);

        Assert.assertTrue(state.isAlive(4, 1));
    }

    @Test
    public void getAlive0Max(){
        map = new boolean[][]{
                { false, false,  false, false,  false },
                { true, false,  false, false,  false }
        };
        state = new GameMap(xSize, ySize, map);

        Assert.assertTrue(state.isAlive(0, 1));
    }
    @Test
    public void getAliveMax0(){
        map = new boolean[][]{
                { false, false,  false, false,  true },
                { false, false,  false, false,  false }
        };
        state = new GameMap(xSize, ySize, map);

        Assert.assertTrue(state.isAlive(4, 0));
    }

    @Test
    public void testXCoordinateCycling() {
        Assert.assertEquals(0, state.getXCircular(0));
        Assert.assertEquals(1, state.getXCircular(1));
        Assert.assertEquals(xSize -1, state.getXCircular(xSize -1));
        Assert.assertEquals(0, state.getXCircular(xSize));
        Assert.assertEquals(0, state.getXCircular(10));
        Assert.assertEquals(1, state.getXCircular(6));
        Assert.assertEquals(3, state.getXCircular(33));

    }
    @Test
    public void testXCoordinateCyclingNegative(){
        Assert.assertEquals(xSize -1, state.getXCircular(-1));
        Assert.assertEquals(2, state.getXCircular(-8));
        Assert.assertEquals(3, state.getXCircular(-7));
        Assert.assertEquals(4, state.getXCircular(-16));
    }
    @Test
    public void testYCoordinateCycling() {
        Assert.assertEquals(0, state.getYCircular(0));
        Assert.assertEquals(1, state.getYCircular(1));
        Assert.assertEquals(ySize-1, state.getYCircular(ySize -1));
        Assert.assertEquals(0, state.getYCircular(ySize));
        Assert.assertEquals(1, state.getYCircular(ySize + 1));
        Assert.assertEquals(2, state.getYCircular(ySize + 2));
    }
    @Test
    public void testYCoordinateCyclingNegative(){
        Assert.assertEquals(ySize - 1, state.getYCircular(-1));
        Assert.assertEquals(2, state.getYCircular(-2));
        Assert.assertEquals(1, state.getYCircular(-3));
        Assert.assertEquals(0, state.getYCircular(-4));
        Assert.assertEquals(3, state.getYCircular(-5));
        Assert.assertEquals(2, state.getYCircular(-6));
        Assert.assertEquals(1, state.getYCircular(-7));
        Assert.assertEquals(0, state.getYCircular(-8));
        Assert.assertEquals(3, state.getYCircular(-9));
        Assert.assertEquals(3, state.getYCircular(-21));
    }

    @Test
    public void testGetNeighboursAmount(){
        Assert.assertEquals(4, state.getNeighboursAmount(2, 1));
        Assert.assertEquals(2, state.getNeighboursAmount(1, 0));
        Assert.assertEquals(6, state.getNeighboursAmount(2, 0));
        Assert.assertEquals(4, state.getNeighboursAmount(2, 1));
        Assert.assertEquals(3, state.getNeighboursAmount(3, 1));
        Assert.assertEquals(5, state.getNeighboursAmount(2, 3));
        Assert.assertEquals(1, state.getNeighboursAmount(0, 1));
    }


}