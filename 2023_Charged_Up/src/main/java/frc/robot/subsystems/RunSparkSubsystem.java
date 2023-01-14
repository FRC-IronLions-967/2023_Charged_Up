package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RunSparkSubsystem extends SubsystemBase {
    
    private CANSparkMax practiceMotor;
    private double speed = 0.25;

    public RunSparkSubsystem() {
        practiceMotor = new CANSparkMax(5, MotorType.kBrushless);

        practiceMotor.setInverted(true);

    }

    public void runMotor() {
        practiceMotor.set(speed);


    }

    public void stopMotor() {
        practiceMotor.set(0.0);
        
    }


    @Override
    public void periodic() {

    }
}
