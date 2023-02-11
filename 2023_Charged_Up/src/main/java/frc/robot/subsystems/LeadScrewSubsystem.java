package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LeadScrewSubsystem extends SubsystemBase {
    
    private CANSparkMax leadScrew;

    public LeadScrewSubsystem() {
        leadScrew = new CANSparkMax(5, MotorType.kBrushless);

        leadScrew.setInverted(true);

    }

    public void runMotor(double speed) {
        leadScrew.set(speed);
    }



    @Override
    public void periodic() {

    }
}
