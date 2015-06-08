package my.outfittery.life.storage;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.handling.StateStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StateStorageImpl implements StateStorage {
    private ConcurrentMap<UUID, List<GameMap>> store = new ConcurrentHashMap<>();
    @Override
    public GameMap  save(GameMap gameMap) {
        store.putIfAbsent(gameMap.getId(), new ArrayList<GameMap>());
        List<GameMap> list = history(gameMap.getId());
        list.add(gameMap);
        return gameMap;
    }


    @Override
    public List<GameMap> history(UUID gameId) {
        return store.get(gameId);
    }

    @Override
    public GameMap getLast(UUID gameId) {
        List<GameMap> history = history(gameId);
        return history.isEmpty()?null:history.get(history.size() -1);
    }

    @Override
    public GameMap updateLast(GameMap gameMap) {
        List<GameMap> history = history(gameMap.getId());
        history.remove(history.size()-1);
        history.add(gameMap);
        return gameMap;
    }

}
