package my.outfittery.life.logic.entity.checks;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.entity.GameState;
import my.outfittery.life.logic.entity.StateChecker;

public class MinumumLiveAmountChecker implements StateChecker {
    private int liveAmount;

    public MinumumLiveAmountChecker(int liveAmount) {
        this.liveAmount = liveAmount;
    }

    @Override
    public GameState check(GameMap gameMap) {
        return (calculateAlive(gameMap) >= liveAmount)? GameState.PLAYING : getFaultState();
    }

    protected int calculateAlive(GameMap gameMap){
        int liveAmount = 0;
        for(int x = 0; x < gameMap.getxSize(); x++){
            for(int y = 0; y < gameMap.getySize(); y++){
                liveAmount += gameMap.isAlive(x, y)?1:0;
            }
        }
        return liveAmount;
    }

    @Override
    public String getMessage() {
        return "Should be at least two live cell";
    }

    @Override
    public GameState getFaultState() {
        return GameState.GAMEOVER;
    }
}
