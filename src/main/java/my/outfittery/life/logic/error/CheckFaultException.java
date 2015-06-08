package my.outfittery.life.logic.error;

import my.outfittery.life.logic.entity.StateChecker;

public class CheckFaultException extends GameOfLifeException {
    private StateChecker checker;
    public CheckFaultException(StateChecker checker) {
        this.checker = checker;
    }
    public StateChecker getChecker(){
        return checker;
    }
}
