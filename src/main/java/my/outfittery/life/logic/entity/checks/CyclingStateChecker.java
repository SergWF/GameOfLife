package my.outfittery.life.logic.entity.checks;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.entity.GameState;
import my.outfittery.life.logic.entity.StateChecker;
import my.outfittery.life.logic.handling.StateStorage;

import java.util.Arrays;
import java.util.List;

public class CyclingStateChecker implements StateChecker {

    private StateStorage storage;
    public static final String MESSAGE = "Game is finished because of cycling";;


    public CyclingStateChecker(StateStorage storage) {
        this.storage = storage;
    }

    protected boolean isSame(GameMap map1, GameMap map2){
        return Arrays.deepEquals(map1.getMap(), map2.getMap());
    }

    protected int findSame(GameMap gameMap, List<GameMap> history){
        if(null == history || history.isEmpty()){
            return -1;
        }
        int i = 0;
        for(GameMap histMap: history){
            if(isSame(gameMap, histMap)){
                return ++i;
            }
        }
        return -1;
    }

    @Override
    public GameState check(GameMap gameMap) {
        return (0<findSame(gameMap, storage.history(gameMap.getId())))
                ?GameState.GAMEOVER
                : GameState.PLAYING;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public GameState getFaultState() {
        return GameState.GAMEOVER;
    }

}
