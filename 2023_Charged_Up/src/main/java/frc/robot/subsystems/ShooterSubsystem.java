package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IO;

public class ShooterSubsystem extends SubsystemBase {
    private IO io;
    private CANSparkMax shooter;
    public ShooterSubsystem () {
        shooter= new CANSparkMax(5, MotorType.kBrushless);
        io = IO.getInstance(); 
    }
    
    public void periodic() {  

    
        double x = io.getManipulatorController().getRightTrigger();
        if (x >= 0.1) {
            shooter.set(x);
        // if (x >= 1) {
        //    shooter.set(1);
        // }    
        }
      

        }
    }




