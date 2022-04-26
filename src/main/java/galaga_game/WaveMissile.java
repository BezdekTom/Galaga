package galaga_game;


import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class WaveMissile extends DrawableSimulableEntity{
    private  double horizontalPosition;
    private  double verticalPosition;
    private int wavesNumber = 0;
    private Image waveImage = Constants.WAVES[wavesNumber];

    WaveMissile(Point2D centerPosition){
        wasAlive = true;
        alive  = true;
        verticalPosition = centerPosition.getY();
        horizontalPosition = centerPosition.getX()- Constants.WAVE_WIDTH/2;

    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(waveImage,horizontalPosition,verticalPosition,Constants.WAVE_WIDTH,(Constants.WAVE_WIDTH/waveImage.getWidth())*waveImage.getHeight());
    }

    @Override
    public void simulate(double timeStep){
        if(wavesNumber< 30){
            wavesNumber++;
            int index = (int) Math.floor((double) wavesNumber/10);
            waveImage = Constants.WAVES[index];
        }
    }

    @Override
    public BoundingBox getBoundingBox(){
        return  new BoundingBox(horizontalPosition, verticalPosition, Constants.WAVE_WIDTH,(Constants.WAVE_WIDTH/waveImage.getWidth())*waveImage.getHeight());
    }

    @Override
    public void  hit(DrawableSimulable another) {
    }
}
