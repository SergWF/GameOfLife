package my.outfittery.life.logic.handling;

import my.outfittery.life.logic.entity.GameMap;

import java.util.List;
import java.util.UUID;

public interface StateStorage {
    GameMap save(GameMap state);
    List<GameMap> history(UUID gameId);
    GameMap getLast(UUID gameId);

    GameMap updateLast(GameMap gameMap);
}
