package my.outfittery.life.logic.entity.rules;

import my.outfittery.life.logic.entity.GameMap;

public class TooMuchNeighboursRule extends AbstractRule {
    private int maximumLimit;

    public TooMuchNeighboursRule(int maximumLimit) {
        this.maximumLimit = maximumLimit;
    }

    @Override
    public boolean validForCell(GameMap gameMap, int x, int y) {
        return gameMap.isAlive(x, y);
    }

    @Override
    protected boolean checkRule(GameMap gameMap, int x, int y) {
        return gameMap.isAlive(x, y) && maximumLimit >= gameMap.getNeighboursAmount(x, y);
    }
}
