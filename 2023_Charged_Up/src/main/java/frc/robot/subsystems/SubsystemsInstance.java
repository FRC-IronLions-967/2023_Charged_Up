package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class SubsystemsInstance {
    public DriveSubsystem driveSubsystem;
    public RunSparkSubsystem runSparkSubsystem;
    public PnuematicSubsystem pnuematicSubsystem;
    private static SubsystemsInstance inst;

    private SubsystemsInstance() {
        driveSubsystem = new DriveSubsystem();
        runSparkSubsystem = new RunSparkSubsystem();
        pnuematicSubsystem = new PnuematicSubsystem();

        CommandScheduler.getInstance().registerSubsystem(driveSubsystem);
        CommandScheduler.getInstance().registerSubsystem(runSparkSubsystem);
        CommandScheduler.getInstance().registerSubsystem(pnuematicSubsystem);
        

    }
    public static SubsystemsInstance getInstance () {
        if(inst == null) inst = new SubsystemsInstance();

        return inst;

    }
}