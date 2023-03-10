package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class MoveArmToPositionCommand extends CommandBase {

  private SubsystemsInstance inst;

  private boolean shoulderPosition;
  private double elbowPosition;

  /**
   * Move the entire arm system to a specified position
   * @param shoulderPosition pneumatic position
   * @param elbowPosition lead screw position
   */
  public MoveArmToPositionCommand (boolean shoulderPosition, double elbowPosition) {
    // Use addRequirements() here to declare subsystem dependencies.

    inst = SubsystemsInstance.getInstance();
    addRequirements(inst.leadScrewSubsystem);
    this.shoulderPosition = shoulderPosition;
    this.elbowPosition = elbowPosition;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inst.pnuematicSubsystem.toggleArm(shoulderPosition); //arm shoulder in
    inst.leadScrewSubsystem.setLeadScrewPosition(elbowPosition); //arm elbow set with lead screw extended 8 inches, value is a guess and needs testing
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      inst.leadScrewSubsystem.stopLeadScrew();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}