package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoSettleLeadScrew;
import frc.robot.commands.ClawWaitCommand;
import frc.robot.commands.LeadScrewInitializeCommand;
import frc.robot.commands.MoveArmToPositionCommand;
import frc.robot.commands.MoveClawCommand;
import frc.robot.commands.RunAutoDriveCommand;
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
                if (inst.leadScrewSubsystem.getState() == LeadScrewStates.AUTO){
                    state = AutoStateMachine.LEAD_SCREW_OUT;
                }
                
                break;
            case LEAD_SCREW_OUT:
                CommandScheduler.getInstance().schedule(new MoveArmToPositionCommand(false, 11));
                state=AutoStateMachine.FINISH_ARM;
                break;
            case FINISH_ARM:
                if (inst.leadScrewSubsystem.isLeadScrewFinished()) {
                    CommandScheduler.getInstance().schedule(new MoveArmToPositionCommand(true, 14.3));
                    state = AutoStateMachine.SETTLE_ROBOT;
                }
            break;
            case SETTLE_ROBOT:
                if (inst.leadScrewSubsystem.isLeadScrewFinished()) {
                    CommandScheduler.getInstance().schedule(new AutoSettleLeadScrew(2));
                    state = AutoStateMachine.PLACE_GAME_PIECE;
                }
            break;
            case PLACE_GAME_PIECE:
                if (inst.leadScrewSubsystem.getAutoSettled()) {
                    CommandScheduler.getInstance().schedule(new MoveClawCommand(true));
                    CommandScheduler.getInstance().schedule(new ClawWaitCommand(2, false));
                    state = AutoStateMachine.DRIVE;
                }
                break;
            case DRIVE:
            if(!inst.pnuematicSubsystem.getClawPosition()) {
                CommandScheduler.getInstance().schedule(new RunAutoDriveCommand(1.1, -0.5, -0.5));
                state = AutoStateMachine.RETRACT_ARM;
            }
                break;
            case RETRACT_ARM:
            if(inst.driveSubsystem.go == 0.0) {
                CommandScheduler.getInstance().schedule(new MoveArmToPositionCommand(false, 3.0));
            }
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
//5. 
