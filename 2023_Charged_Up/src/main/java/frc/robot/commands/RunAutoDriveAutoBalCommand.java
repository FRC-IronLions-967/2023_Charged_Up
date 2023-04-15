package frc.robot.commands;

import edu.wpi.first.net.WPINetJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class RunAutoDriveAutoBalCommand extends CommandBase {
  
  private SubsystemsInstance inst;
  private double leftSpeed;
  private double rightSpeed;

  public RunAutoDriveAutoBalCommand (double leftSpeed, double rightSpeed ){
    // Use addRequirements() here to declare subsystem dependencies.
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;

    inst = SubsystemsInstance.getInstance();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inst.driveSubsystem.move(rightSpeed, leftSpeed);
  }

  @Override
  public boolean isFinished() {
    return ((inst.driveSubsystem.checkAngle() > 0.2) || inst.driveSubsystem.driveTimeout);
  } 

  @Override
  public void end(boolean interrupted){
    super.end(interrupted);

    if( inst.driveSubsystem.driveTimeout){
      inst.driveSubsystem.move(0, 0);
    }
  }

  // Called once the command ends or is interrupted.
}