package galaga;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MyMissile extends  DrawableSimulableEntity{
    private  double horizontalPosition;
    private  double verticalPosition;
    private  final double speed;
    private  final double topBorder;
    private  final double height;

    public  MyMissile(double aHorizontalPosition, double aVerticalPosition,double aHeight, double aSpeed, double aTopBorder){
        verticalPosition = aVerticalPosition;
        speed = aSpeed;
        topBorder = aTopBorder;
        height = aHeight;
        horizontalPosition = aHorizontalPosition - (height/ Constants.MY_MISSILE.getHeight())*Constants.MY_MISSILE.getWidth() / 2;
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(Constants.MY_MISSILE,horizontalPosition,verticalPosition,(height/ Constants.MY_MISSILE.getHeight())*Constants.MY_MISSILE.getWidth(),height);
    }

    @Override
    public boolean simulate(double timeStep){
        if(verticalPosition - speed*timeStep > 0)
        {
            verticalPosition -= timeStep*speed;
            return true;
        }
        return  false;
    }

}
