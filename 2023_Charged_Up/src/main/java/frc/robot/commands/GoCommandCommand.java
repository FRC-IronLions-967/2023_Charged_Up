package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemsInstance;


public class GoCommandCommand extends CommandBase{
    private boolean MotorIsRun;
    private SubsystemsInstance inst; 
    public GoCommandCommand(boolean IsMotorRun) {
        // Use addRequirements() here to declare subsystem dependencies.
    
        inst = SubsystemsInstance.getInstance();
        MotorIsRun = IsMotorRun;
        // addRequirements(inst.runSparkCommand);
    
      }
    
      // Called when the command is initially scheduled.
      @Override
      public void initialize() {
      }
    
      // Called every time the scheduler runs while the command is scheduled.
      @Override
      public void execute() {
        inst.shooterSubsystem.goMotorMotor(MotorIsRun);
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

