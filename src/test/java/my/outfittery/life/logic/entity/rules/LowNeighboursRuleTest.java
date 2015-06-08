package my.outfittery.life.logic.entity.rules;

import my.outfittery.life.logic.entity.GameMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LowNeighboursRuleTest {
    private static final int MIN_LIMIT = 2;
    LowNeighboursRule rule = new LowNeighboursRule(MIN_LIMIT);

    GameMap map;

    private String testName;
    private boolean expectedResult;
    private int neighboursAmount;
    private boolean isAlive;

    public LowNeighboursRuleTest(String testName, boolean expectedResult, int neighboursAmount, boolean isAlive) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.neighboursAmount = neighboursAmount;
        this.isAlive = isAlive;
    }


    @Before
    public void setUp() throws Exception {
        map = Mockito.mock(GameMap.class);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"Alive and Less Than Min", false, MIN_LIMIT -1, true}
                , {"Alive and Same With Min", true, MIN_LIMIT, true}
                , {"Alive and More Than Min", true, MIN_LIMIT + 1, true}
                , {"Dead and Less Than Min", false, MIN_LIMIT -1, false}
                , {"Dead and Same With Min", false, MIN_LIMIT, false}
                , {"Dead and More Than Min", false, MIN_LIMIT + 1, false}
        };
        return Arrays.asList(data);
    }

    @Test
    public void test(){
        //GIVEN: Cell state
        Mockito.doReturn(isAlive).when(map).isAlive(Mockito.anyInt(), Mockito.anyInt());
        //AND:   has neighbours amount
        Mockito.doReturn(neighboursAmount).when(map).getNeighboursAmount(Mockito.anyInt(), Mockito.anyInt());
        //WHEN: check rule
        //THEN:
        Assert.assertEquals(testName, expectedResult, rule.checkAlive(map, 3, 3));
    }
}