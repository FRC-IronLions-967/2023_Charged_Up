package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.MoveArmToPositionCommand;
import frc.robot.subsystems.LeadScrewStates;
import frc.robot.subsystems.LeadScrewSubsystem;
import frc.robot.subsystems.SubsystemsInstance;

public class Autonomous implements AutonomousInterface {

    private static AutoStateMachine state = AutoStateMachine.IDLE;
    private boolean autoInit;
    private SubsystemsInstance inst;

    public Autonomous(){
        inst = SubsystemsInstance.getInstance();
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        autoInit = true;
        state = AutoStateMachine.INITIALIZING;
    }

    @Override
    public void periodic() {
        switch(state) {
            case IDLE:
                if(autoInit){
                    state = AutoStateMachine.INITIALIZING;
                }
                break;
            case INITIALIZING:
                if (inst.leadScrewSubsystem.getState() == LeadScrewStates.AUTO){
                    state = AutoStateMachine.LEAD_SCREW_OUT;
                }
                
                break;
            case LEAD_SCREW_OUT:
                CommandScheduler.getInstance().schedule(new MoveArmToPositionCommand(false, 8));
                state=AutoStateMachine.FINISH_ARM;
                break;
            case FINISH_ARM:
                if (inst.leadScrewSubsystem.isLeadScrewFinished()) {
                    CommandScheduler.getInstance().schedule(new MoveArmToPositionCommand(true, 14));
                    state = AutoStateMachine.PLACE_GAME_PIECE;
                }
            break;
            case PLACE_GAME_PIECE:
                break;
            case DRIVE:
                break;
            default:
                break;
        }
    }
}
//Auto procedure 
//0. wait for leadScrew to initialize
//1. leadScrew 2/3 up 
//2. arm pneumatics out, rest of leadScrew 
//3. open claw
//4. Drive Back, arm pneumatics in, leadScrew to storage position 
