package galaga;

import javafx.scene.canvas.GraphicsContext;

public class HealtsDrawer {
    private final double rightSide;
    private final double imageHeight = Constants.MARGIN;
    private final double imageWidth = Constants.HUMANS_SHIP.getWidth()*(imageHeight/Constants.HUMANS_SHIP.getHeight());

    public HealtsDrawer(double gameWidth){
        rightSide = gameWidth;
    }

    public void draw(GraphicsContext gc, int healths){
        gc.save();
        for(int i = 0; i < healths; i++){
            gc.drawImage(Constants.HUMANS_SHIP, rightSide - Constants.MARGIN - imageWidth - i*(imageWidth + 5), Constants.MARGIN, imageHeight,imageWidth);
        }
        gc.restore();
    }
}
