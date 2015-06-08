package my.outfittery.life.web;

import my.outfittery.life.logic.entity.GameMap;
import my.outfittery.life.logic.handling.GameHandler;
import my.outfittery.life.logic.handling.StateStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameHandler gameHandler;
    @Autowired
    StateStorage stateStorage;

    @RequestMapping("/new")
    public @ResponseBody GameMap newGame(@RequestParam int x, @RequestParam int y){
        GameMap gameMap = gameHandler.initMap(x, y);
        stateStorage.save(gameMap);
        return gameMap;
    }

    @RequestMapping(value = "{gameId}/place")
    public @ResponseBody GameMap placeLife(@PathVariable("gameId") UUID gameId, @RequestParam int x, @RequestParam int y){
        GameMap gameMap = gameHandler.placeLife(stateStorage.getLast(gameId), x, y);
        return stateStorage.updateLast(gameMap);
    }

    @RequestMapping("{gameId}/next")
    public @ResponseBody GameMap doNextTurn(@PathVariable("gameId") UUID gameId){
        GameMap gameMap = gameHandler.makeTurn(stateStorage.getLast(gameId));
        stateStorage.save(gameMap);
        return gameMap;
    }


//    @ExceptionHandler(Throwable.class)
//    public ResponseEntity<Throwable> errorHandling(Throwable e){
//        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
