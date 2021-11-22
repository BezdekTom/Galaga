package galaga;

import javafx.scene.canvas.GraphicsContext;

public interface DrawableSimulable {
    public  void draw(GraphicsContext gc);
    public boolean simulate(double timeStep);
}
