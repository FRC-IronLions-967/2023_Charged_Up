package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PnuematicSubsystem extends SubsystemBase {

    private Compressor pcmCompressor;
    private DoubleSolenoid clawSol;
    private DoubleSolenoid armSol_1;
    //private DoubleSolenoid armSol_2;

    

    public PnuematicSubsystem() {
        try{
            pcmCompressor  = new Compressor(1, PneumaticsModuleType.CTREPCM);

            clawSol = new DoubleSolenoid(1, PneumaticsModuleType.CTREPCM, 2, 3);
            armSol_1 = new DoubleSolenoid(1, PneumaticsModuleType.CTREPCM, 0, 1);
            //armSol_2 = new DoubleSolenoid(1, PneumaticsModuleType.CTREPCM, 1, 6);
        
        } catch(Exception e) {
            System.out.println("Unable to created DoubleSolenoid");
        }
    }

    public void toggleClaw(boolean direction){
        if(direction){
            clawSol.set(kForward);
        } else {
            clawSol.set(kReverse);
        }
    }

    public void toggleArm(boolean direction){
        if(direction){
           armSol_1.set(kForward);
           //armSol_2.set(kForward);
        } else {
            armSol_1.set(kReverse);
            //armSol_2.set(kReverse);
        }
    }
   
    //hello!

    @Override
    public void periodic() {
        //System.out.println(doubleSol.get());
        //System.out.println(doubleSol.isRevSolenoidDisabled());
    }
}