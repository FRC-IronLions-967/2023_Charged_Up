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
    this.direction = direction;
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;

    inst = SubsystemsInstance.getInstance();

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inst.driveSubsystem.move(rightSpeed, leftSpeed);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    inst.driveSubsystem.move(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}