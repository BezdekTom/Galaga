package galaga;

import javafx.geometry.Point2D;

public class EnemyShipWaveAttac extends EnemyShip{
    private EnemyShipOnPlace enemyShipOnPlace;
    private WaveMissile waveMissile;
    private WaveAttacListener waveAttacListener;
    private final Point2D startPosition;
    private final Point2D finalPosition;
    private Point2D speed;
    private double shootingTimer = 5;


    public EnemyShipWaveAttac(EnemyShipOnPlace aEnemyShipOnPlace,double aHeight, int aType, Point2D aPosition,Point2D aFinalPosition, SimulableListener aSimulableListener , EnemyShipListener aEnemyShipListener, WaveAttacListener aWaveAttacListener){
        super(aHeight, aType, aPosition, aSimulableListener,aEnemyShipListener);
        waveAttacListener = aWaveAttacListener;
        enemyShipOnPlace = aEnemyShipOnPlace;
        startPosition = position;
        finalPosition = aFinalPosition;
        speed = finalPosition.add(startPosition.multiply(-1)).multiply(0.5);
    }

    @Override
    public void simulate(double timeStep) {
        wasAlive = alive;

        if (alive){
            if(position.getY() <= finalPosition.getY() && position.getY() >= startPosition.getY()){
                position = position.add(speed.multiply(timeStep));
            }
            else if(shootingTimer >= 0){
                fire();
                shootingTimer -= timeStep;
            }
            else if(position.getY() < startPosition.getY()){
                merge();
            }
            else{
                simulableListener.destruct(waveMissile);
                waveMissile = null;
                speed = speed.multiply(-1);
                position = position.add(speed.multiply(timeStep));
            }
        }
        else if(explosionTimer > 0)
        {
            explosionTimer -= timeStep;
            if(waveMissile != null){
                simulableListener.destruct(waveMissile);
                waveMissile = null;
            }
        }
        else
        {
            simulableListener.destruct(this);
            if(waveMissile != null){
                simulableListener.destruct(waveMissile);
                waveMissile = null;
            }
            simulableListener.destruct(enemyShipOnPlace);
        }
    }

    @Override
    public  void fire(){
        if(waveMissile == null){
            waveMissile = new WaveMissile(new Point2D(position.getX() + width*0.5, position.getY()+height));
            waveAttacListener.fire(waveMissile);
        }
    }

    private void merge(){
        if(waveMissile != null){
            simulableListener.destruct(waveMissile);
            waveMissile = null;
        }
        enemyShipOnPlace.merge(alive, wasAlive);
        simulableListener.destruct(this);
    }

}
