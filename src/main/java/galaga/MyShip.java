package galaga;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MyShip extends Ship{
    private  final double maxSpeed;
    private  int direction = 0;
    private double horizontalPosition;
    private double verticalPosition;
    private  boolean fored = false;
    private  double height;
    private  final double leftBorder;
    private  final double rightBorder;
    private  double pictureWidth;

    public  MyShip(double aHeight, double aVerticalPosition, double absolutSpeed, double aLeftBorder, double aRightBorder){
        maxSpeed = absolutSpeed;
        verticalPosition = aVerticalPosition;
        height = aHeight;
        leftBorder = aLeftBorder;
        rightBorder = aRightBorder;
        pictureWidth = (height/ Constants.HUMANS_SHIP.getHeight())*Constants.HUMANS_SHIP.getWidth();
        horizontalPosition = (rightBorder - leftBorder - pictureWidth)/2;
    }

    public MyShip(double aHeight, double aVerticalPosition, double absolutSpeed, double aLeftBorder, double aRightBorder, SimulableListener aSimulableListener){
        this(aHeight,aVerticalPosition,absolutSpeed,aLeftBorder,aRightBorder);
        simulableListener = aSimulableListener;
    }

    public  MyShip(double aHeight, double aHorizontalPosition, double aVerticalPosition, double absolutSpeed, double aLeftBorder, double aRightBorder){
        maxSpeed = absolutSpeed;
        horizontalPosition = aHorizontalPosition;
        verticalPosition = aVerticalPosition;
        height = aHeight;
        leftBorder = aLeftBorder;
        rightBorder = aRightBorder;
        pictureWidth = (height/ Constants.HUMANS_SHIP.getHeight())*Constants.HUMANS_SHIP.getWidth();
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        Image ship = Constants.HUMANS_SHIP;
        gc.drawImage(ship,horizontalPosition,verticalPosition,(height/ ship.getHeight())*ship.getWidth(),height);
    }

    @Override
    public void  simulate(double timeStep){
        if(direction != 0){
            if(horizontalPosition+direction*maxSpeed*timeStep < leftBorder){
                horizontalPosition = leftBorder;
            }
            else if(horizontalPosition + direction*maxSpeed*timeStep + pictureWidth > rightBorder){
                horizontalPosition = rightBorder- pictureWidth;
            }
            else{
                horizontalPosition += direction*maxSpeed*timeStep;
            }
        }
    }

    @Override
    public BoundingBox getBoundingBox(){
        return  (new BoundingBox(this.horizontalPosition, this.verticalPosition, this.pictureWidth, this.height));
    }

    @Override
    public void  hit(DrawableSimulable another){
        if(intersect(another) && (another instanceof EnemyShip)){
            simulableListener.destruct(this);
        }
    }

    public  void moveLeft(){
        direction = -1;
    }

    public  void moveRight(){
        direction = 1;
    }

    public  void stop(){
        direction = 0;
    }

    public  double getHorizontalPosition(){
        return  horizontalPosition + pictureWidth/2;
    }
}
