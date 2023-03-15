package frc.robot.commands;

import edu.wpi.first.net.WPINetJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SubsystemsInstance;

public class RunAutoDriveCommand extends WaitCommand {
  
  private SubsystemsInstance inst;
  private boolean direction;
  private double leftSpeed;
  private double rightSpeed;

  public RunAutoDriveCommand (double seconds, double leftSpeed, double rightSpeed ) {
    super(seconds);
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


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    inst.driveSubsystem.move(0, 0);
    inst.driveSubsystem.driveBackFinished = true;
  }
}