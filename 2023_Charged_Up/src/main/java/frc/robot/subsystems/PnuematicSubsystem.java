package frc.robot.subsystems;

import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PnuematicSubsystem extends SubsystemBase {

    private Compressor pcmCompressor;
    private Compressor phCompressor;
    private boolean enabled;
    private boolean pressureSwitch;
    private double current;
    private DoubleSolenoid DoublePCM;
    private DoubleSolenoid DoublePH;
    

    public Value kOff;
    public Value kForward;
    public Value kReverse;



    public PnuematicSubsystem() {
        pcmCompressor  = new Compressor(0, PneumaticsModuleType.CTREPCM);
        phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);

        pcmCompressor.enableDigital();
        pcmCompressor.disable();

        enabled = pcmCompressor.isEnabled();
        pressureSwitch = pcmCompressor.getPressureSwitchValue();
        current = pcmCompressor.getCurrent();

        DoublePCM = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
        DoublePH = new DoubleSolenoid(9, PneumaticsModuleType.REVPH, 4, 5);


        DoublePCM.set(kOff);
        DoublePCM.set(kForward);
        DoublePCM.set(kReverse);
    }

    @Override
    public void periodic() {
    }
}