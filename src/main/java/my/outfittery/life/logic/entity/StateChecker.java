package my.outfittery.life.logic.entity;

public interface StateChecker {
    GameState check(GameMap gameMap);
    String getMessage();
    GameState getFaultState();
}
