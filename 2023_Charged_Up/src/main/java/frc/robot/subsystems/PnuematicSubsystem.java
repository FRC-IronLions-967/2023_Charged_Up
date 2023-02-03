package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PnuematicSubsystem extends SubsystemBase {

    private Compressor pcmCompressor;
    // private boolean enabled;
    // private boolean pressureSwitch;
    // private double current;
    private DoubleSolenoid doublePCM;

    

    public Value kOff;
    public Value kForward;
    public Value kReverse;



    public PnuematicSubsystem() {
        pcmCompressor  = new Compressor(0, PneumaticsModuleType.CTREPCM);

        doublePCM = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        
        pcmCompressor.disable();
        // DoublePCM.set(kOff);
        // DoublePCM.set(kForward);
        // DoublePCM.set(kReverse);
    }

    public void toggleSolenoid(Integer toggleSolenoid){
        if(toggleSolenoid == 0){
            doublePCM.set(kForward);
        } else if(toggleSolenoid == 1){
            doublePCM.set(kReverse);
        }else if(toggleSolenoid == 2){
            doublePCM.set(kOff);
        }
        doublePCM.toggle();
    }

    public void toggleCompressor(Integer toggleCompressor){
        if(toggleCompressor == 1){
            pcmCompressor.isEnabled();
        }else if(toggleCompressor == 0){
            pcmCompressor.disable();
        }
    }
    

    @Override
    public void periodic() {
    }
}