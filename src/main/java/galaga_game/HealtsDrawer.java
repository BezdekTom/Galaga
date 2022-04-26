package galaga_game;

import javafx.scene.canvas.GraphicsContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HealtsDrawer {
    private final double gameWidth;
    private final double imageHeight = Constants.MARGIN;
    private final double imageWidth = Constants.HUMANS_SHIP.getWidth()*(imageHeight/Constants.HUMANS_SHIP.getHeight());


    public void draw(GraphicsContext gc, int healths){
        gc.save();
        for(int i = 0; i < healths; i++){
            gc.drawImage(Constants.HUMANS_SHIP, gameWidth - Constants.MARGIN - imageWidth - i*(imageWidth + 5), Constants.MARGIN*0.5, imageHeight,imageWidth);
        }
        gc.restore();
    }
}
