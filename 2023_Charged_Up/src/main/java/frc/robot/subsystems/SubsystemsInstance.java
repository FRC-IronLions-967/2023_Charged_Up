package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class SubsystemsInstance {
    public DriveSubsystem driveSubsystem;
    public LeadScrewSubsystem leadScrewSubsystem;
    public PnuematicSubsystem pnuematicSubsystem;
    public ShooterSubsystem shooterSubsystem;

    private static SubsystemsInstance inst;

    private SubsystemsInstance() {
        driveSubsystem = new DriveSubsystem();
        leadScrewSubsystem = new LeadScrewSubsystem();
        pnuematicSubsystem = new PnuematicSubsystem();
        shooterSubsystem = new ShooterSubsystem();

        CommandScheduler.getInstance().registerSubsystem(driveSubsystem);
        CommandScheduler.getInstance().registerSubsystem(leadScrewSubsystem);
        CommandScheduler.getInstance().registerSubsystem(pnuematicSubsystem);
        CommandScheduler.getInstance().registerSubsystem(shooterSubsystem);
        

    }
    public static SubsystemsInstance getInstance () {
        if(inst == null) inst = new SubsystemsInstance();

        return inst;

    }
}