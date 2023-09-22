package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IO;


public class ShooterSubsystem extends SubsystemBase {
    private IO io;
    private CANSparkMax shooter;
    private SparkMaxPIDController shooterController;
    public ShooterSubsystem () {
        shooter= new CANSparkMax(10, MotorType.kBrushless);
        io = IO.getInstance();
        
        shooterController = shooter.getPIDController();
        shooterController.setP(3e-4);  //needs tuning
        shooterController.setI(0);
        shooterController.setD(0);
        shooterController.setFF(0.00005);
    }

    public void goMotorMotor(Boolean IsMotorRun) {
        if (IsMotorRun) {
            shooterController.setReference(0, ControlType.kVelocity);
        }
        else {
            shooterController.setReference(2000 , ControlType.kVelocity);
        }
    }
    
    
    public void periodic() {  

    
        double x = io.getDriverController().getRightTrigger();
        if (x >= 0.1) {
            //shooter.set(x);
        // if (x >= 1) {
        //    shooter.set(1);
        // }    
            shooterController.setReference(x, ControlType.kVelocity);
        }
      

        }

    public void simulationInit() {
        REVPhysicsSim.getInstance().addSparkMax(shooter, DCMotor.getNEO(1));
    }
    }




