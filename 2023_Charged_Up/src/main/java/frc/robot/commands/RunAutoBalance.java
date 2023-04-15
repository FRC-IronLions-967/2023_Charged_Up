package frc.robot.commands;

import edu.wpi.first.net.WPINetJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class RunAutoBalance extends CommandBase {
  
  private SubsystemsInstance inst;

  public RunAutoBalance (){
    // Use addRequirements() here to declare subsystem dependencies.

    inst = SubsystemsInstance.getInstance();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inst.driveSubsystem.autoBal();
  }

  @Override
  public boolean isFinished() {
    return Math.abs(inst.driveSubsystem.checkAngle()) <= 0.025;
  } 

  @Override
  public void end(boolean interrupted){
    super.end(interrupted);
    inst.driveSubsystem.move(0, 0);
  }

  // Called once the command ends or is interrupted.
}