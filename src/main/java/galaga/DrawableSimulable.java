package galaga;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

public interface DrawableSimulable {
    void  hit(DrawableSimulable another);
    void draw(GraphicsContext gc);
    void  simulate(double timeStep);
    BoundingBox getBoundingBox();
    boolean  intersect(DrawableSimulable another);
}
