package galaga_game;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import lombok.Setter;

public abstract class DrawableSimulableEntity implements  DrawableSimulable{

    protected SimulableListener simulableListener;
    @Setter
    protected  int numberOfHits = 0;
    protected boolean alive = true;
    protected boolean wasAlive = true;
    protected double explosionTimer = 3;

    protected DrawableSimulableEntity(){
        simulableListener = new SimulableListenerEmpty();
    }

    @Override
    public  void draw(GraphicsContext gc){
        gc.save();
        drawInternal(gc);
        gc.restore();
    }

    protected void drawInternal(GraphicsContext gc){

    }

    public void setSimulableListener(SimulableListener aSimulableListener){
        simulableListener = aSimulableListener;
    }

    @Override
    public boolean  intersect(DrawableSimulable another){
        if(getBoundingBox().intersects(another.getBoundingBox())){
            return true;
        }
        return  false;
    }
}
