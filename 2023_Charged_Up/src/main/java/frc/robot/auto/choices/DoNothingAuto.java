package frc.robot.auto.choices;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.auto.AutoStateMachine;
import frc.robot.auto.AutonomousInterface;
import frc.robot.commands.AutoSettleLeadScrew;
import frc.robot.commands.ClawWaitCommand;
import frc.robot.commands.LeadScrewInitializeCommand;
import frc.robot.commands.MoveArmToPositionCommand;
import frc.robot.commands.MoveClawCommand;
import frc.robot.commands.RunAutoDriveCommand;
import frc.robot.subsystems.LeadScrewStates;
import frc.robot.subsystems.LeadScrewSubsystem;
import frc.robot.subsystems.SubsystemsInstance;

public class DoNothingAuto implements AutonomousInterface {

    private static AutoStateMachine state = AutoStateMachine.IDLE;
    private boolean autoInit;
    private SubsystemsInstance inst; 
    
  

    public DoNothingAuto(){
        inst = SubsystemsInstance.getInstance();
    }

    public void UpdateState(AutoStateMachine newState){
        state = newState;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        autoInit = true;
        state = AutoStateMachine.IDLE;
    }

    @Override
    public void periodic() {
        switch(state) {
            case IDLE:
                if(autoInit){
                    state = AutoStateMachine.INITIALIZING;
                    CommandScheduler.getInstance().schedule(new LeadScrewInitializeCommand());
                    CommandScheduler.getInstance().schedule(new MoveClawCommand(false));
                }
                break;
            case INITIALIZING:
                break;
            case FINISH_ARM:
            break;
            case SETTLE_ROBOT:
            break;
            case PLACE_GAME_PIECE:
                break;
            case DRIVE:
                break;
            case RETRACT_ARM:
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
//5. Spin!
