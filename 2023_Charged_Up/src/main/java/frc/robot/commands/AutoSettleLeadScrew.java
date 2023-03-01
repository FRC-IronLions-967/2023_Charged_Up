package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SubsystemsInstance;

public class AutoSettleLeadScrew extends WaitCommand {
  
  private SubsystemsInstance inst;

  public AutoSettleLeadScrew (double seconds) {
    super(seconds);
    // Use addRequirements() here to declare subsystem dependencies.

    inst = SubsystemsInstance.getInstance();

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
    inst.leadScrewSubsystem.setAutoSettled(true);
  }
}