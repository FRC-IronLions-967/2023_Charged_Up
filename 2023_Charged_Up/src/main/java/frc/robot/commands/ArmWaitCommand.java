package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SubsystemsInstance;

public class ArmWaitCommand extends WaitCommand {
  
  private SubsystemsInstance inst;
  private boolean direction;

  public ArmWaitCommand (double seconds) {
    super(seconds);
    // Use addRequirements() here to declare subsystem dependencies.

    inst = SubsystemsInstance.getInstance();

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    inst.pnuematicSubsystem.autoShoulderRetracted = true; 
  }
}