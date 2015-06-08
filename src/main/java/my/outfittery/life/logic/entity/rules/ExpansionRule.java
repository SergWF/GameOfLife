package my.outfittery.life.logic.entity.rules;

import my.outfittery.life.logic.entity.GameMap;

public class ExpansionRule extends AbstractRule {
    int expansionRequirement;

    public ExpansionRule(int expansionRequirement) {
        this.expansionRequirement = expansionRequirement;
    }

    @Override
    public boolean validForCell(GameMap gameMap, int x, int y) {
        return !gameMap.isAlive(x, y);
    }

    @Override
    protected boolean checkRule(GameMap gameMap, int x, int y) {
        return gameMap.isAlive(x, y) || expansionRequirement <= gameMap.getNeighboursAmount(x, y);
    }
}
