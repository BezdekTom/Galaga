package galaga;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MyMissile extends  DrawableSimulableEntity{
    private  double horizontalPosition;
    private  double verticalPosition;
    private  final double speed;
    private  final double topBorder;
    private  final double height;
    private  double width;
    private  Image image = Constants.MY_MISSILE;

    public  MyMissile(double aHorizontalPosition, double aVerticalPosition,double aHeight, double aSpeed, double aTopBorder){
        verticalPosition = aVerticalPosition;
        speed = aSpeed;
        topBorder = aTopBorder;
        height = aHeight;
        width = (height/ image.getHeight())*image.getWidth();
        horizontalPosition = aHorizontalPosition - (height/ image.getHeight())*image.getWidth() / 2;
    }

    public  MyMissile(double aHorizontalPosition, double aVerticalPosition,double aHeight, double aSpeed, double aTopBorder, SimulableListener aSimulableListener){
        this(aHorizontalPosition,aVerticalPosition,aHeight,aSpeed,aTopBorder);
        simulableListener = aSimulableListener;
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(image,horizontalPosition,verticalPosition,width,height);
    }

    @Override
    public void simulate(double timeStep){
        if(verticalPosition - speed*timeStep > 0 && alive)
        {
            verticalPosition -= timeStep*speed;
        }
        else {
            simulableListener.destruct(this);
        }
    }

    @Override
    public BoundingBox getBoundingBox(){
        return  new BoundingBox(horizontalPosition, verticalPosition, (height/ Constants.MY_MISSILE.getHeight())*Constants.MY_MISSILE.getWidth(), height);
    }

    @Override
    public void  hit(DrawableSimulable another){
        if(intersect(another) && (another instanceof EnemyShip)){
            alive = false;
        }
    }
}
