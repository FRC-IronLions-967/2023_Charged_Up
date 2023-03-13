package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class RunAutobalancingCommand extends CommandBase {
  
  private SubsystemsInstance inst;
  private boolean autoBal;
  //delete this comment asap

  public RunAutobalancingCommand(boolean autoBal) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.autoBal = autoBal;
    inst = SubsystemsInstance.getInstance();
    // addRequirements(inst.runSparkSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(autoBal){
    inst.driveSubsystem.operatorControl();
    System.out.println("Autobalancing command ran");
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
