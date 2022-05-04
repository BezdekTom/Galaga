package galaga;

import java.util.concurrent.CompletableFuture;

public interface GameControllerListener {
	void switchState(GameStates state, CompletableFuture<Void> scoreFuture);
}
