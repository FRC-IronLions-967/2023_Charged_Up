package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PnuematicSubsystem extends SubsystemBase {

    private Compressor pcmCompressor;
    private DoubleSolenoid doubleSol;

    

    public PnuematicSubsystem() {
        pcmCompressor  = new Compressor(1, PneumaticsModuleType.CTREPCM);

        doubleSol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
    }

    public void toggleSolenoid(Integer toggleSolenoid){
        if(toggleSolenoid == 0){
            doubleSol.set(kForward);
        } else if(toggleSolenoid == 1){
            doubleSol.set(kReverse);
        }else if(toggleSolenoid == 2){
            doubleSol.set(kOff);
        }
        doubleSol.toggle();
    }
   

    @Override
    public void periodic() {
   
    }
}