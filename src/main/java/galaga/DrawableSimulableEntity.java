package galaga;

import javafx.scene.canvas.GraphicsContext;

public abstract class DrawableSimulableEntity implements  DrawableSimulable{

    @Override
    public  void draw(GraphicsContext gc){
        gc.save();
        drawInternal(gc);
        gc.restore();
    }

    public void drawInternal(GraphicsContext gc){

    }
}
