package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;

public class MoveClawCommand extends CommandBase {
  
//hola
  private SubsystemsInstance inst;
  private boolean direction;

  public MoveClawCommand (boolean direction) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.direction = direction;

    inst = SubsystemsInstance.getInstance();
    // addRequirements(inst.runSparkCommand);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inst.pnuematicSubsystem.toggleClaw(direction);

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