package my.outfittery.life.logic.entity.checks;


import my.outfittery.life.logic.handling.StateStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CyclingStateCheckerTest {

    CyclingStateChecker checker;


    @Before
    public void setUp() throws Exception {
        StateStorage storage = Mockito.mock(StateStorage.class);
        checker = new CyclingStateChecker(storage);
    }

    @Test
    public void testCheckIsSamePositive(){
        Assert.assertTrue(checker.isSame(map1, map2));
    }

    @Test
    public void testCheckIsSameNegative(){
        Assert.assertTrue(checker.isSame(map1, map2));
    }

    @Test
    public void testCheckCycling(){
        Assert.assertTrue(checker.check(map));
    }

    @Test
    public void testCheckCycling(){
        Assert.assertTrue(checker.check(map));
    }
}