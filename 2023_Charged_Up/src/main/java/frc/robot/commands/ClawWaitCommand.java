package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SubsystemsInstance;

public class ClawWaitCommand extends WaitCommand {
  
  private SubsystemsInstance inst;
  private boolean direction;

  public ClawWaitCommand (double seconds, boolean direction) {
    super(seconds);
    // Use addRequirements() here to declare subsystem dependencies.
    this.direction = direction;

    inst = SubsystemsInstance.getInstance();

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    inst.pnuematicSubsystem.toggleClaw(direction); 
  }
}