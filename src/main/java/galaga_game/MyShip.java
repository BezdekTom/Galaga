package galaga_game;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;


class MyShip extends Ship{
    private  final double maxSpeed;
    private  int direction = 0;
    private double horizontalPosition;
    private double verticalPosition;
    private  double height;
    private  final double leftBorder = Constants.MARGIN;
    private  final double rightBorder;
    private  double pictureWidth;

    MyShip(double aHeight, double aVerticalPosition, double absolutSpeed, double gameWidth){
        maxSpeed = absolutSpeed;
        verticalPosition = aVerticalPosition;
        height = aHeight;
        rightBorder = gameWidth - Constants.MARGIN;
        pictureWidth = (height/ Constants.HUMANS_SHIP.getHeight())*Constants.HUMANS_SHIP.getWidth();
        horizontalPosition = (rightBorder - leftBorder - pictureWidth)/2;
    }

    MyShip(double aHeight, double aVerticalPosition, double absolutSpeed, double gameWidth, ShipListener aShipListener){
        this(aHeight,aVerticalPosition,absolutSpeed,gameWidth);
        shipListener = aShipListener;
    }

    MyShip(double aHeight, double aVerticalPosition, double absolutSpeed, double gameWidth, SimulableListener aSimulableListener){
        this(aHeight,aVerticalPosition,absolutSpeed,gameWidth);
        simulableListener = aSimulableListener;
    }

    MyShip(double aHeight, double aHorizontalPosition, double aVerticalPosition, double absolutSpeed, double gameWidth){
        maxSpeed = absolutSpeed;
        horizontalPosition = aHorizontalPosition;
        verticalPosition = aVerticalPosition;
        height = aHeight;
        rightBorder = gameWidth - Constants.MARGIN;
        pictureWidth = (height/ Constants.HUMANS_SHIP.getHeight())*Constants.HUMANS_SHIP.getWidth();
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        Image ship = Constants.HUMANS_SHIP;
        gc.drawImage(ship,horizontalPosition,verticalPosition,(height/ ship.getHeight())*ship.getWidth(),height);
    }

    @Override
    public void  simulate(double timeStep){
        if(!alive){
            simulableListener.destruct(this);
            alive = true;
        }

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
        if(intersect(another) && (another instanceof EnemyShip || another instanceof EnemyMissile) && ((DrawableSimulableEntity) another).wasAlive){
            alive = false;
        }
        else if(intersect(another) && another instanceof WaveMissile){
            for(int i = 0;i<3;i++){
                simulableListener.destruct(this);
            }
        }
    }

    void moveLeft(){
        direction -= 1;
    }

    void moveRight(){

        direction += 1;
    }

    void stop(){
        direction = 0;
    }

    double getHorizontalPosition(){
        return  horizontalPosition + pictureWidth/2;
    }

    @Override
    public  void fire(){
        shipListener.fire(new Point2D(horizontalPosition+ pictureWidth/2, verticalPosition + height/2), -1);
    }

    Point2D getPosition(){
        return new Point2D(horizontalPosition, verticalPosition);
    }
}
