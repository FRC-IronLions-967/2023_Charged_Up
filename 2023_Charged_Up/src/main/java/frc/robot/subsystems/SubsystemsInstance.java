package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class SubsystemsInstance {
    public DriveSubsystem driveSubsystem;
    public RunSparkSubsystem runSparkSubsystem;

    private static SubsystemsInstance inst;

    private SubsystemsInstance() {
        driveSubsystem = new DriveSubsystem();
        runSparkSubsystem = new RunSparkSubsystem();

        CommandScheduler.getInstance().registerSubsystem(driveSubsystem);
        CommandScheduler.getInstance().registerSubsystem(runSparkSubsystem);
        

    }
    public static SubsystemsInstance getInstance () {
        if(inst == null) inst = new SubsystemsInstance();

        return inst;

    }
}