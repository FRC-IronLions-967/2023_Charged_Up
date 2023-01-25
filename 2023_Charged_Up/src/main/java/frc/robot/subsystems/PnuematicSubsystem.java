package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PnuematicSubsystem extends SubsystemBase {

    private Compressor pcmCompressor;
    private Compressor phCompressor;
    private boolean enabled;
    private boolean pressureSwitch;
    private double current;
    
    public PnuematicSubsystem() {
        pcmCompressor  = new Compressor(0, PneumaticsModuleType.CTREPCM);
        phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);

        pcmCompressor.enableDigital();
        pcmCompressor.disable();

        enabled = pcmCompressor.isEnabled();
        pressureSwitch = pcmCompressor.getPressureSwitchValue();
        current = pcmCompressor.getCurrent();

    }

    @Override
    public void periodic() {
    }
}