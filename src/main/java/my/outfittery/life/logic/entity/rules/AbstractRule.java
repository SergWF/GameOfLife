package my.outfittery.life.logic.entity.rules;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.entity.GameRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRule implements GameRule {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRule.class);

    protected abstract boolean checkRule(GameMap gameMap, int x, int y);

    @Override
    public boolean checkAlive(GameMap gameMap, int x, int y) {
        boolean res = checkRule(gameMap, x, y);
        if(/*logger.isDebugEnabled() &&*/ res != gameMap.isAlive(x, y)){
            logger.info("{}x{} now is {} because of {}", x, y, res?"alive":"dead", this.getClass().getSimpleName());
        }
        return res;
    }

}
