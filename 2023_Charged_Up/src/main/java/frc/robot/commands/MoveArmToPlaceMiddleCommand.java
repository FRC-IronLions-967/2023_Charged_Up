package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class MoveArmToPlaceMiddleCommand extends CommandBase {
  

  private SubsystemsInstance inst;

  public MoveArmToPlaceMiddleCommand () {
    // Use addRequirements() here to declare subsystem dependencies.

    inst = SubsystemsInstance.getInstance();
    // addRequirements(inst.runSparkCommand);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inst.pnuematicSubsystem.toggleArm(true); //arm shoulder out
    inst.leadScrewSubsystem.setLeadScrewPosition(10); //arm elbow set with lead screw extended 14 inches
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}