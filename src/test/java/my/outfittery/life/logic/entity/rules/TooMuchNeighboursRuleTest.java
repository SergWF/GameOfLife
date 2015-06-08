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
public class TooMuchNeighboursRuleTest {

    private static final int MAX_LIMIT = 3;
    TooMuchNeighboursRule rule = new TooMuchNeighboursRule(MAX_LIMIT);

    GameMap map;

    private String testName;
    private boolean expectedResult;
    private int neighboursAmount;
    private boolean isAlive;

    public TooMuchNeighboursRuleTest(String testName, boolean expectedResult, int neighboursAmount, boolean isAlive) {
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
                {"Alive and Less Than Max", true, MAX_LIMIT -1, true}
                , {"Alive and Same WIth Max", true, MAX_LIMIT, true}
                , {"Alive and More Than Max", false, MAX_LIMIT + 1, true}
                , {"Dead and Less Than Max", false, MAX_LIMIT -1, false}
                , {"Dead and Same WIth Max", false, MAX_LIMIT, false}
                , {"Dead and More Than Max", false, MAX_LIMIT + 1, false}
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