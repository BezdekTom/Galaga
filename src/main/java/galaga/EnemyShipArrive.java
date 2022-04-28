package galaga;

import javafx.geometry.Point2D;

class EnemyShipArrive extends EnemyShip{
    private final double diameter;
    private final Point2D centrOfCircle;
    private final int direction;
    private final int nextShipDirection;
    private final int numberFromSide;
    private ArriveShipListener arriveShipListener;
    private double time = 0;

    EnemyShipArrive(double aHeight, int aType,int aNumberFromSide, Point2D aPosition,double gameWidth, int aDirection,int aNextShipDirection, SimulableListener aSimulableListener, EnemyShipListener aEnemyShipListener, ArriveShipListener aArriveShipListener){
        super(aHeight, aType, aPosition, aSimulableListener, aEnemyShipListener);
        nextShipDirection = aNextShipDirection;
        arriveShipListener = aArriveShipListener;
        numberFromSide = aNumberFromSide;
        if(aDirection == -1){
            diameter = ((gameWidth - width)/2 -position.getX())/2;
            centrOfCircle = new Point2D(gameWidth*0.5-diameter - width*0.5, aPosition.getY());

        }
        else if(aDirection == 1){
            diameter = (0.5*gameWidth - (gameWidth- position.getX()))*0.5;
            centrOfCircle = new Point2D(0.5*gameWidth + diameter-0.5*width, aPosition.getY());
        }
        else{
            diameter = 0.25*gameWidth;
            centrOfCircle = new Point2D(aPosition.getX()+diameter, aPosition.getY());
        }

        direction = aDirection;
    }

    EnemyShipArrive(int aType,int aNumberFromSide, Point2D aPosition,double gameWidth, int aDirection,int aNextShipDirection, SimulableListener aSimulableListener, EnemyShipListener aEnemyShipListener, ArriveShipListener aArriveShipListener){
        this(Constants.DEFAULT_SHIP_HEIGHT, aType, aNumberFromSide, aPosition, gameWidth, aDirection, aNextShipDirection, aSimulableListener, aEnemyShipListener, aArriveShipListener);
    }


    @Override
    public void simulate(double timeStep){
        wasAlive = alive;

        if(alive) {
            if(time < Math.PI){
                position = centrOfCircle.add(direction * diameter * Math.cos(time), diameter*Math.sin(time));
                time += timeStep*Constants.SPEED_KOEF;
            }
            else{
                arriveShipListener.ChangeShip(position, nextShipDirection,direction, type,numberOfHits,numberFromSide, height);
                simulableListener.destruct(this);
            }
        }
        else if(explosionTimer > 0)
        {
            explosionTimer -= timeStep;
        }
        else
        {
            simulableListener.destruct(this);

        }
    }

    @Override
    public  void fire(){
    }
}
