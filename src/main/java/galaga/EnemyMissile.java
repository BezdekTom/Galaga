package galaga;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EnemyMissile extends  DrawableSimulableEntity{
    private  double horizontalPosition;
    private  double verticalPosition;
    private  final double speed;
    private  final double bottomBorder;
    private  final double height;
    private  double width;
    private  Image image;

    public  EnemyMissile(int type, double aHorizontalPosition, double aVerticalPosition,double aHeight, double aSpeed, double aBottomBorder){
        image = Constants.ENEMY_MISSILES[type];
        verticalPosition = aVerticalPosition;
        speed = aSpeed;
        bottomBorder = aBottomBorder;
        height = aHeight;
        width = (height/ image.getHeight())*image.getWidth();
        horizontalPosition = aHorizontalPosition - (height/ image.getHeight())*image.getWidth() / 2;
    }

    public  EnemyMissile(int type, double aHorizontalPosition, double aVerticalPosition,double aHeight, double aSpeed, double aTopBorder, SimulableListener aSimulableListener){
        this(type,aHorizontalPosition,aVerticalPosition,aHeight,aSpeed,aTopBorder);
        simulableListener = aSimulableListener;
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(image,horizontalPosition,verticalPosition,width,height);
    }

    @Override
    public void simulate(double timeStep){
        wasAlive = alive;

        if(verticalPosition + Constants.SPEED_KOEF*speed*timeStep < bottomBorder && alive)
        {
            verticalPosition += Constants.SPEED_KOEF*timeStep*speed;
        }
        else {
            simulableListener.destruct(this);
        }
    }

    @Override
    public BoundingBox getBoundingBox(){
        return  new BoundingBox(horizontalPosition, verticalPosition, (height/ Constants.HUMANS_MISSILE.getHeight())*Constants.HUMANS_MISSILE.getWidth(), height);
    }

    @Override
    public void  hit(DrawableSimulable another){
        if(intersect(another) && (another instanceof MyShip) && ((MyShip) another).wasAlive){
            alive = false;
        }
    }
}
