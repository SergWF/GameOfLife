package my.outfittery.life.logic.entity;

public interface GameRule {
    boolean validForCell(GameMap gameMap, int x, int y);
    boolean checkAlive(GameMap gameMap, int x, int y);
}
