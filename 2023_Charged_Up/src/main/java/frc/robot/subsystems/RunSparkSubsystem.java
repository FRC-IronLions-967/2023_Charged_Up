package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RunSparkSubsystem extends SubsystemBase {
    
    private CANSparkMax practiceMotor;

    public RunSparkSubsystem() {
        practiceMotor = new CANSparkMax(5, MotorType.kBrushless);

        practiceMotor.setInverted(true);

    }

    public void runMotor(double speed) {
        practiceMotor.set(speed);
    }



    @Override
    public void periodic() {

    }
}
