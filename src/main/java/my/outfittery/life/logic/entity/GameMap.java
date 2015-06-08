package my.outfittery.life.logic.entity;

import java.util.UUID;

public class GameMap {
    private UUID id;
    boolean [][] map;
    private int xSize;
    private int ySize;
    private GameState gameState;
    private String message;
    private long step = 0;

    private boolean getCell(int x, int y){
        return map[y][x];
    }

    private void setCell(int x, int y, boolean val){
        map[y][x] = val;
    }

    public UUID getId() {
        return id;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getMessage() {
        return message;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected int circular(int val, int dimSize){
        int res = val % dimSize;
        return res< 0 ? (dimSize + res) : res;
    }

    protected int getXCircular(int x){
       return circular(x, xSize);
    }

    protected int getYCircular(int y){
        return circular(y, ySize);
    }

    public GameMap(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.id = UUID.randomUUID();
        map = new boolean[ySize][xSize];
    }

    protected GameMap(int xSize, int ySize, boolean[][] map) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.id = UUID.randomUUID();
        this.map = map;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public Boolean isAlive(int x, int y){
        return getCell(getXCircular(x), getYCircular(y));
    }


    public void placeLife(int x, int y, boolean isAlive){
        setCell(getXCircular(x), getYCircular(y), isAlive);
    }

    public int getNeighboursAmount(int x, int y){
        int nCount = 0;
        nCount += isAlive(x-1, y-1) ? 1:0;
        nCount += isAlive(x-1, y) ? 1:0;
        nCount += isAlive(x-1, y+1) ? 1:0;
        nCount += isAlive(x, y-1) ? 1:0;

        nCount += isAlive(x, y+1) ? 1:0;
        nCount += isAlive(x+1, y-1) ? 1:0;
        nCount += isAlive(x+1, y) ? 1:0;
        nCount += isAlive(x+1, y+1) ? 1:0;

        return nCount;
    }


    public boolean[][] getMap() {
        return map;
    }

    public GameMap makeCopyForNextStep(){
        GameMap gameMap = new GameMap(xSize, ySize);
        gameMap.id = this.id;
        gameMap.step = this.step + 1;
        return  gameMap;
    }
}
