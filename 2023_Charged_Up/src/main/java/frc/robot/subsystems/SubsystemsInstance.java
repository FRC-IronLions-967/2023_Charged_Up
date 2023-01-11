package frc.robot.subsystems;

public class SubsystemsInstance {
    public DriveSubsystem driveSubsystem;

    private static SubsystemsInstance inst;

    private SubsystemsInstance() {
        driveSubsystem = new DriveSubsystem();


    }
    public static SubsystemsInstance getInstance () {
        if(inst == null) inst = new SubsystemsInstance();

        return inst;

    }
}