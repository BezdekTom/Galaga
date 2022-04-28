package galaga;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class MyMissile extends  DrawableSimulableEntity{
    private  double horizontalPosition;
    private  double verticalPosition;
    private  final double speed;
    private  final double topBorder;
    private  final double height;
    private  double width;
    private  Image image = Constants.HUMANS_MISSILE;

    MyMissile(double aHorizontalPosition, double aVerticalPosition,double aHeight, double aSpeed, double aTopBorder){
        speed = aSpeed;
        topBorder = aTopBorder;
        height = aHeight;
        verticalPosition = aVerticalPosition - 3* (height/4) ;
        width = (height/ image.getHeight())*image.getWidth();
        horizontalPosition = aHorizontalPosition - (height/ image.getHeight())*image.getWidth() / 2;
    }

    MyMissile(double aHorizontalPosition, double aVerticalPosition,double aHeight, double aSpeed, double aTopBorder, SimulableListener aSimulableListener){
        this(aHorizontalPosition,aVerticalPosition,aHeight,aSpeed,aTopBorder);
        simulableListener = aSimulableListener;
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(image,horizontalPosition,verticalPosition,width,height);
    }

    @Override
    public void simulate(double timeStep){
        wasAlive = alive;
        if(verticalPosition - speed*timeStep > 20 && alive)
        {
            verticalPosition -= timeStep*speed;
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
        if(intersect(another) && (another instanceof EnemyShip) && ((EnemyShip) another).wasAlive){
            alive = false;
        }
    }
}
