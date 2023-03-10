package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class DriveBrakeCommand extends CommandBase {
  
  private boolean idleMode;
  private SubsystemsInstance inst;

  public DriveBrakeCommand(Boolean idleMode) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.idleMode = idleMode;
    inst = SubsystemsInstance.getInstance();

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(idleMode){
      inst.driveSubsystem.brakeMotors();
    } else if(idleMode == false){
      inst.driveSubsystem.coastMotors();
    }

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