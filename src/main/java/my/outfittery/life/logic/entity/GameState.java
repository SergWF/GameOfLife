package my.outfittery.life.logic.entity;

public enum GameState {
    NEW(true),PLAYING(true),GAMEOVER(false),FAULT(false);
    private boolean canContinue;

    GameState(boolean canContinue) {
        this.canContinue = canContinue;
    }

    public boolean canContinue() {
        return canContinue;
    }
}
