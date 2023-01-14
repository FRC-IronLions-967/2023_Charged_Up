package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class RunSparkCommand extends CommandBase {
    
  private SubsystemsInstance inst;

  public RunSparkCommand() {
    // Use addRequirements() here to declare subsystem dependencies.

    inst = SubsystemsInstance.getInstance();
    addRequirements(inst.runSparkSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inst.runSparkSubsystem.runMotor();

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    inst.runSparkSubsystem.stopMotor();
    return true;
  }
}