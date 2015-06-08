package my.outfittery.life.logic.handling;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.entity.GameRule;
import my.outfittery.life.logic.entity.GameState;
import my.outfittery.life.logic.entity.StateChecker;
import my.outfittery.life.logic.error.EmptyRulesList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private static final Logger logger = LoggerFactory.getLogger(GameHandler.class);
    private List<GameRule> rules = new ArrayList<>();
    private List<StateChecker> checkers = new ArrayList<>();

    public GameMap initMap(int xSize, int ySize){
        GameMap gameMap = new GameMap(xSize, ySize);
        gameMap.setGameState(GameState.NEW);
        gameMap.setMessage("Welcome!");
        return gameMap;
    }

    public GameMap placeLife(GameMap gameMap, int x, int y){
        gameMap.placeLife(x, y, true);
        return gameMap;
    }


    public GameMap makeTurn(GameMap currentMap){
        logger.info("make a new tour for game [{}]",currentMap.getId());
        GameMap gameMap = useRules(currentMap);
        StateChecker fault = checkFaultState(gameMap);
        gameMap.setGameState((null != fault)?fault.getFaultState():GameState.PLAYING);
        gameMap.setMessage((null != fault)?fault.getMessage():"Do Next Turn");
        return gameMap;
    }


    protected GameMap useRules(GameMap gameMap){
        if(rules.isEmpty()){
            logger.error("empty rules list");
            throw new EmptyRulesList();
        }

        GameMap newMap = gameMap.makeCopyForNextStep();
        logger.debug("use rules");
        for(int x = 0; x < gameMap.getxSize(); x++) {
            for (int y = 0; y < gameMap.getySize(); y++) {
                newMap.placeLife(x, y, checkCellByRules(gameMap, x, y));
            }
        }
        logger.debug("rules implemented");
        return newMap;
    }

    protected boolean checkCellByRules(GameMap state, int x, int y){
        boolean result = true;
        for (GameRule rule : rules) {
            if(rule.validForCell(state, x, y) && !rule.checkAlive(state, x, y)){
                return false;
            }
        }
        return result;
    }

    protected StateChecker checkFaultState(GameMap gameMap){
        for(StateChecker checker: checkers){
            GameState check = checker.check(gameMap);
            if(!check.canContinue()){
                return checker;
            }
        }
        return null;
    }

    public void addRule(GameRule gameRule) {
        rules.add(gameRule);
    }

    public void addChecker(StateChecker checker) {
        checkers.add(checker);
    }

    public String printMap(GameMap map){
        StringBuilder sb = new StringBuilder("GAME ");
        sb.append(map.getId());
        sb.append("\n").append("step ").append(map.getStep()).append("\n");
        for(int y = 0; y < map.getySize(); y++){
            for(int x = 0; x < map.getxSize(); x++){
                sb.append(map.isAlive(x, y)?"x":".");
            }
            sb.append("\n");
        }
        sb.append("state: ").append(map.getGameState());
        sb.append("\n").append("message: ").append(map.getMessage());
        return sb.toString();
    }
}
