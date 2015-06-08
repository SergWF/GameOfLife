package my.outfittery.life.logic.entity.rules;

import my.outfittery.life.logic.entity.GameMap;

public class LowNeighboursRule extends AbstractRule {
    private int minimumLimit;

    public LowNeighboursRule(int minimumLimit) {
        this.minimumLimit = minimumLimit;
    }

    @Override
    public boolean validForCell(GameMap gameMap, int x, int y) {
        return gameMap.isAlive(x, y);
    }

    @Override
    protected boolean checkRule(GameMap gameMap, int x, int y) {
        return gameMap.isAlive(x, y) && minimumLimit <= gameMap.getNeighboursAmount(x, y);
    }
}
