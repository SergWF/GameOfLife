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
public class ExpansionRuleTest {
    private static final int EXPANSION_VALUE = 3;
    ExpansionRule rule = new ExpansionRule(EXPANSION_VALUE);

    GameMap map;

    private String testName;
    private boolean expectedResult;
    private int neighboursAmount;
    private boolean isAlive;

    public ExpansionRuleTest(String testName, boolean expectedResult, int neighboursAmount, boolean isAlive) {
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
                {"Alive and Less Than Need", true, EXPANSION_VALUE -1, true}
                , {"Alive and Same With Need", true, EXPANSION_VALUE, true}
                , {"Alive and More Than Need", true, EXPANSION_VALUE + 1, true}
                , {"Dead and Less Than Need", false, EXPANSION_VALUE -1, false}
                , {"Dead and Same With Need", true, EXPANSION_VALUE, false}
                , {"Dead and More Than Need", true, EXPANSION_VALUE + 1, false}
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