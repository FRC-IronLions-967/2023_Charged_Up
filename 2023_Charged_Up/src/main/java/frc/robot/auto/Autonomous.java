package frc.robot.auto;

public class Autonomous implements AutonomousInterface {

    private static AutoStateMachine state = AutoStateMachine.IDLE;

    public Autonomous(){

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void periodic() {
        switch(state) {
            case IDLE:
                
                break;
            case INITIALIZING:
                
                break;
            case DRIVE:

            break;
            case PLACE_GAME_PIECE:
            
            break;
        }
    }
}
