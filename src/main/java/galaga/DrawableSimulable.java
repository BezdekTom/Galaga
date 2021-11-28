package galaga;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

public interface DrawableSimulable {
    public void  hit(DrawableSimulable another);
    public void draw(GraphicsContext gc);
    public void  simulate(double timeStep);
    public BoundingBox getBoundingBox();
    public boolean  intersect(DrawableSimulable another);
}
