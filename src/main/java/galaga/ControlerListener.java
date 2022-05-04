package galaga;

import java.util.concurrent.CompletableFuture;

public interface ControlerListener {
    void switchState(GameStates state);
    //void switchState(GameStates state, CompletableFuture<String> scoreFuture);
}
